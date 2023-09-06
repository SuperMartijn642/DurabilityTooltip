package com.supermartijn642.durabilitytooltip;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created 7/7/2020 by SuperMartijn642
 */
@Mod(modid = DurabilityTooltip.MODID, name = DurabilityTooltip.NAME, version = DurabilityTooltip.VERSION, dependencies = DurabilityTooltip.DEPENDENCIES, clientSideOnly = true)
public class DurabilityTooltip {

    public static final String MODID = "durabilitytooltip";
    public static final String NAME = "Durability Tooltip";
    public static final String VERSION = "1.1.4";
    public static final String DEPENDENCIES = "required-after:supermartijn642configlib@[1.1.6,)";
    public static Logger LOGGER = LogManager.getLogger("durabilitytooltip");

    public DurabilityTooltip(){
        if(Loader.isModLoaded("supermartijn642configlib"))
            DurabilityTooltipConfig.init();
    }
}
