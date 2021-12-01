package com.supermartijn642.durabilitytooltip;

import net.minecraftforge.fml.common.Mod;

/**
 * Created 7/7/2020 by SuperMartijn642
 */
@Mod(modid = DurabilityTooltip.MODID, name = DurabilityTooltip.NAME, version = DurabilityTooltip.VERSION, dependencies = DurabilityTooltip.DEPENDENCIES, clientSideOnly = true)
public class DurabilityTooltip {

    public static final String MODID = "durabilitytooltip";
    public static final String NAME = "Durability Tooltip";
    public static final String VERSION = "1.0.1";
    public static final String DEPENDENCIES = "required-after:supermartijn642configlib@[1.0.9,)";

    public DurabilityTooltip(){
        DurabilityTooltipConfig.init();
    }
}
