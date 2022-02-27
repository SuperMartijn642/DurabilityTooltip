package com.supermartijn642.durabilitytooltip;

import net.minecraft.util.text.TextFormatting;

/**
 * Created 27/02/2022 by SuperMartijn642
 */
public enum TooltipColorStyle {

    BASE, GOLD, VARYING;

    public TextFormatting getColorForDurability(TextFormatting baseColor, int durability, int maxDurability){
        switch(this){
            case BASE:
                return baseColor;
            case GOLD:
                return TextFormatting.GOLD;
            case VARYING:
                return durability >= 0.4f * maxDurability ? TextFormatting.GREEN : durability >= 0.1f * maxDurability ? TextFormatting.GOLD : TextFormatting.RED;
        }
        return null;
    }
}
