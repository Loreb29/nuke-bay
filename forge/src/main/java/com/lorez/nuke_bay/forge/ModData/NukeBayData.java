package com.lorez.nuke_bay.forge.ModData;

import com.lorez.nuke_bay.forge.files.entity.bullet.TinyNuke;
import com.lorez.nuke_bay.forge.files.entity.weapon.NukeBay;
import com.mojang.logging.LogUtils;
import immersive_aircraft.Main;
import immersive_aircraft.WeaponRegistry;
import immersive_aircraft.WeaponRendererRegistry;
import immersive_aircraft.client.render.entity.weaponRenderer.SimpleWeaponRenderer;
import immersive_aircraft.cobalt.registration.Registration;
import immersive_aircraft.Items;
import immersive_aircraft.entity.bullet.BulletEntity;
import immersive_aircraft.entity.misc.WeaponMount;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import immersive_aircraft.item.WeaponItem;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static immersive_aircraft.Items.baseProps;

public class NukeBayData {
    public static final String MOD_ID = "nuke_bay";
    public static Supplier<Item> NUKE_BAY_ITEM;
    public static Supplier<EntityType<TinyNuke>> TINY_NUKE;

    public static final Map<ResourceLocation, WeaponRegistry.WeaponConstructor> REGISTRY = new HashMap<>();
    public static void init() {
        WeaponRegistry.register(NukeBayData.locate("nuke_bay"), NukeBay::new);
        NUKE_BAY_ITEM=register("nuke_bay", () -> new WeaponItem(baseProps().stacksTo(1), WeaponMount.Type.DROP));//Adds the item
     TINY_NUKE = registeer("tiny_nuke", EntityType.Builder
                .of(TinyNuke::new, MobCategory.MISC)
                .sized(0.98f, 0.98f)
                .clientTrackingRange(20)
                .updateInterval(1)
                .fireImmune());
    }

    static Supplier<Item> register(String name, Supplier<Item> item) {
        Supplier<Item> register = Registration.register(BuiltInRegistries.ITEM, NukeBayData.locate(name), item);
        Items.items.add(register);
        return register;
    }

    static <T extends Entity> Supplier<EntityType<T>> registeer(String name, EntityType.Builder<T> builder) {
        ResourceLocation id = new ResourceLocation(NukeBayData.MOD_ID, name);
        return Registration.register(BuiltInRegistries.ENTITY_TYPE, id, () -> builder.build(id.toString()));
    }
    public static ResourceLocation locate(String name) {
        return new ResourceLocation(NukeBayData.MOD_ID, name);
    }

}