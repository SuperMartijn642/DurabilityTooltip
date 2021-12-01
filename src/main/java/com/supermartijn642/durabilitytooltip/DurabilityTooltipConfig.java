package com.supermartijn642.durabilitytooltip;

import com.supermartijn642.configlib.ModConfig;
import com.supermartijn642.configlib.ModConfigBuilder;

import java.util.function.Supplier;

/**
 * Created 01/12/2021 by SuperMartijn642
 */
public class DurabilityTooltipConfig {

    public static final Supplier<Boolean> onlyVanillaTools;

    static {
        ModConfigBuilder builder = new ModConfigBuilder("durabilitytooltip", ModConfig.Type.CLIENT);

        builder.push("Client");
        onlyVanillaTools = builder.dontSync().comment("Should the durability tooltip only be shown on vanilla tools?").define("onlyVanillaTools", false);
        builder.pop();

        builder.build();
    }

    public static void init(){
        // Place this here so the class gets loaded
    }

}
