package com.lorez.nuke_bay.neoforge;

import com.lorez.nuke_bay.neoforge.ModData.NukeBayData;
import com.lorez.nuke_bay.neoforge.ModData.NukeBayDataClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

@SuppressWarnings("unused")
@Mod(value=NukeBayData.MOD_ID, dist =Dist.CLIENT)
@EventBusSubscriber(modid = NukeBayData.MOD_ID, value = Dist.CLIENT)
public class ForgeNukeBayClient {
    @SubscribeEvent
    public static void setup(EntityRenderersEvent.RegisterRenderers event) {
        NukeBayDataClient.init();
    }
}
