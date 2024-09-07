package com.lorez.nuke_bay.forge.ModData;

import com.lorez.nuke_bay.forge.files.client.bullet.TinyNukeRenderer;
import immersive_aircraft.WeaponRendererRegistry;
import immersive_aircraft.client.render.entity.weaponRenderer.SimpleWeaponRenderer;
import immersive_aircraft.cobalt.registration.Registration;

public class NukeBayDataClient {
    public static void init() {
        Registration.register(NukeBayData.TINY_NUKE.get(), TinyNukeRenderer::new);
        WeaponRendererRegistry.register(NukeBayData.locate("nuke_bay"), new SimpleWeaponRenderer("nuke_bay"));
    }
}
