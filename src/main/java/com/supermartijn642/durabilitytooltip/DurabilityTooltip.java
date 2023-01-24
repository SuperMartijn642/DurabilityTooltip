package com.supermartijn642.durabilitytooltip;

import net.fabricmc.api.ModInitializer;

/**
 * Created 7/7/2020 by SuperMartijn642
 */
public class DurabilityTooltip implements ModInitializer {

    @Override
    public void onInitialize(){
        DurabilityTooltipConfig.init();
    }
}
