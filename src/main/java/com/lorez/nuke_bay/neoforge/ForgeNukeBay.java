package com.lorez.nuke_bay.neoforge;

import com.lorez.nuke_bay.neoforge.ModData.NukeBayData;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;

import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(NukeBayData.MOD_ID)
@EventBusSubscriber(modid = NukeBayData.MOD_ID)
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
