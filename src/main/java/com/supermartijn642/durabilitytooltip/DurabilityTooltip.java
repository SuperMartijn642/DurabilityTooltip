package com.supermartijn642.durabilitytooltip;

import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

/**
 * Created 7/7/2020 by SuperMartijn642
 */
@Mod("durabilitytooltip")
public class DurabilityTooltip {

    public DurabilityTooltip(){
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> "IGNORESERVERONLY", (a, b) -> b));
        DurabilityTooltipConfig.init();
    }
}
