package com.lorez.nuke_bay.forge;

import com.lorez.nuke_bay.forge.ModData.NukeBayData;
import com.lorez.nuke_bay.forge.ModData.NukeBayDataClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = NukeBayData.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeNukeBayClient {
    @SubscribeEvent
    public static void setup(EntityRenderersEvent.RegisterRenderers event) {
        NukeBayDataClient.init();
    }
}
