package com.lorez.nuke_bay.forge.files.entity.bullet;
import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.client.particle.ACParticleRegistry;
import com.github.alexmodguy.alexscaves.server.block.blockentity.NuclearSirenBlockEntity;
import com.github.alexmodguy.alexscaves.server.block.poi.ACPOIRegistry;
import com.github.alexmodguy.alexscaves.server.entity.ACEntityRegistry;
import com.github.alexmodguy.alexscaves.server.entity.item.NuclearBombEntity;
import com.github.alexmodguy.alexscaves.server.entity.item.NuclearExplosionEntity;
import com.google.common.base.Predicates;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

import java.util.stream.Stream;

public class TinyNuke extends NuclearBombEntity{
    public TinyNuke(EntityType<? extends NuclearBombEntity> entityType, Level level){
        super(entityType, level);
    }

    @Override
    public void tick() {
        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.04, 0.0));
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().scale(0.98));
        if (this.onGround()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.7, -0.7, 0.7));
        }

        if ((this.tickCount + this.getId()) % 10 == 0) {
            Level var2 = this.level();
            if (var2 instanceof ServerLevel) {
                ServerLevel serverLevel = (ServerLevel)var2;
                this.getNearbyySirens(serverLevel, 256).forEach(this::activateeSiren);
            }
        }

        int i = this.getTime() + 1;
        if (i > 450) {
            this.discard();
            if (!this.level().isClientSide) {
                this.boom();
            }
        } else {
            this.setTime(i);
            this.updateInWaterStateAndDoFluidPushing();
            if (this.level().isClientSide && 300 - i > 10 && this.random.nextFloat() < 0.3F && this.onGround()) {
                Vec3 center = this.getEyePosition();
                this.level().addParticle((ParticleOptions) ACParticleRegistry.PROTON.get(), center.x, center.y, center.z, center.x, center.y, center.z);
            }
        }

    }
    @Override
    public void resetFallDistance() {
        if (this.fallDistance > 40.0F) {
            this.discard();
            if (!this.level().isClientSide) {
                this.boom();
            }
        }

    }
    public Stream<BlockPos> getNearbyySirens(ServerLevel world, int range) {
        PoiManager pointofinterestmanager = world.getPoiManager();
        return pointofinterestmanager.findAll((poiTypeHolder) -> {
            return poiTypeHolder.is(ACPOIRegistry.NUCLEAR_SIREN.getKey());
        }, Predicates.alwaysTrue(), this.blockPosition(), range, PoiManager.Occupancy.ANY);
    }

    public void activateeSiren(BlockPos pos) {
        BlockEntity var3 = this.level().getBlockEntity(pos);
        if (var3 instanceof NuclearSirenBlockEntity nuclearSirenBlock) {
            nuclearSirenBlock.setNearestNuclearBomb(this);
        }

    }
    public void boom(){
        NuclearExplosionEntity explosion = (NuclearExplosionEntity)((EntityType) ACEntityRegistry.NUCLEAR_EXPLOSION.get()).create(this.level());
        explosion.copyPosition(this);
        explosion.setSize(((Double) AlexsCaves.COMMON_CONFIG.nukeExplosionSizeModifier.get()).floatValue());
        this.level().addFreshEntity(explosion);
    }
}
