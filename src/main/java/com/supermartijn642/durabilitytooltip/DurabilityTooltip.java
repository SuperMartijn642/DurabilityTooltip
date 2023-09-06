package com.supermartijn642.durabilitytooltip;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

/**
 * Created 7/7/2020 by SuperMartijn642
 */
public class DurabilityTooltip implements ModInitializer {

    @Override
    public void onInitialize(){
        if(FabricLoader.getInstance().isModLoaded("supermartijn642configlib"))
            DurabilityTooltipConfig.init();
    }
}
