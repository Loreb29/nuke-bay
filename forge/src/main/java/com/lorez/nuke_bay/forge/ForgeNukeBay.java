package com.lorez.nuke_bay.forge;

import com.lorez.nuke_bay.forge.ModData.NukeBayData;
import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;
import org.slf4j.Logger;
// The value here should match an entry in the META-INF/mods.toml file
@Mod(NukeBayData.MOD_ID)
@Mod.EventBusSubscriber(modid = NukeBayData.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeNukeBay
{
    private static boolean registered = false;
    @SubscribeEvent
    public static void onRegistryEvent(RegisterEvent event) {
        if (!registered) {
            registered = true;
            NukeBayData.init();
        }
    }
}
