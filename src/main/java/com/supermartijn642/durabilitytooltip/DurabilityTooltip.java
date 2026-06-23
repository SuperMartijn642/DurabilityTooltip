package com.supermartijn642.durabilitytooltip;

import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created 7/7/2020 by SuperMartijn642
 */
@Mod("durabilitytooltip")
public class DurabilityTooltip {

    public static Logger LOGGER = LoggerFactory.getLogger("durabilitytooltip");

    public DurabilityTooltip(FMLJavaModLoadingContext context){
        context.registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> IExtensionPoint.DisplayTest.IGNORESERVERONLY, (a, b) -> b));
        if(ModList.isLoaded("supermartijn642configlib"))
            DurabilityTooltipConfig.init();
    }
}
