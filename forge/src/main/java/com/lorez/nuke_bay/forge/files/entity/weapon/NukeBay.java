package com.lorez.nuke_bay.forge.files.entity.weapon;

import com.github.alexmodguy.alexscaves.server.entity.ACEntityRegistry;
import com.github.alexmodguy.alexscaves.server.entity.item.NuclearBombEntity;
import com.lorez.nuke_bay.forge.ModData.NukeBayData;
import com.lorez.nuke_bay.forge.files.entity.bullet.TinyNuke;
import com.mojang.logging.LogUtils;
import immersive_aircraft.Entities;
import immersive_aircraft.cobalt.network.NetworkHandler;
import immersive_aircraft.config.Config;
import immersive_aircraft.entity.VehicleEntity;
import immersive_aircraft.entity.misc.WeaponMount;
import immersive_aircraft.entity.weapons.BombBay;
import immersive_aircraft.entity.weapons.BulletWeapon;
import immersive_aircraft.network.c2s.FireMessage;
import immersive_aircraft.network.s2c.FireResponse;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.slf4j.Logger;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class NukeBay extends BulletWeapon {
    private static final float MAX_COOLDOWN = 1.0F;
    private float cooldown = 0.0F;
    private Random randomn= new Random();

    public NukeBay(VehicleEntity entity, ItemStack stack, WeaponMount mount, int slot) {
        super(entity, stack, mount, slot);
    }
    protected float getBarrelLength() {
        return 0.25F;
    }

    protected Vector4f getBarrelOffset() {
        return new Vector4f(0.0F, -0.8F, 0.0F, 1.0F);
    }

    public float getVelocity() {
        return 0.0F;
    }

    protected Entity getBullet(Entity shooter, Vector4f position, Vector3f direction) {
        TinyNuke bullet = new TinyNuke(NukeBayData.TINY_NUKE.get(), shooter.level());
        bullet.setPos((double)position.x(), (double)position.y(), (double)position.z());
        direction.mul(this.getVelocity());
        bullet.setDeltaMovement((double)direction.x(), (double)direction.y(), (double)direction.z());

        return bullet;
    }

    public void tick() {
        this.cooldown -= 0.05F;

    }
    @Override
    public void fire(Vector3f direction) {
        if (this.spentAmmo(nukeBayAmmunition, 20)) {
            super.fire(direction);

        }
    }
    public Map<String, Integer> nukeBayAmmunition = Map.of("alexscaves:nuclear_bomb", 100);
    public void clientFire(int index) {
        if (this.cooldown <= 0.0F) {
            this.cooldown = 100.0F;
            NetworkHandler.sendToServer(new FireMessage(this.getSlot(), index, this.getDirection()));
        }

    }

    public Vector3f getDirection() {
        Vector3f direction = new Vector3f(0.0F, 1.0F, 0.0F);
        direction.mul(new Matrix3f(this.getMount().transform()));
        direction.mul(this.getEntity().getVehicleNormalTransform());
        return direction;
    }
}
