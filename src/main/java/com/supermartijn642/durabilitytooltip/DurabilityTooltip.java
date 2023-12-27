package com.supermartijn642.durabilitytooltip;

import net.neoforged.fml.IExtensionPoint;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.NetworkConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created 7/7/2020 by SuperMartijn642
 */
@Mod("durabilitytooltip")
public class DurabilityTooltip {

    public static Logger LOGGER = LoggerFactory.getLogger("durabilitytooltip");

    public DurabilityTooltip(){
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (a, b) -> b));
        if(ModList.get().isLoaded("supermartijn642configlib"))
            DurabilityTooltipConfig.init();
    }
}
