package com.supermartijn642.durabilitytooltip;

import net.minecraft.ChatFormatting;

/**
 * Created 27/02/2022 by SuperMartijn642
 */
public enum TooltipColorStyle {

    BASE, GOLD, VARYING;

    public ChatFormatting getColorForDurability(ChatFormatting baseColor, int durability, int maxDurability){
        switch(this){
            case BASE:
                return baseColor;
            case GOLD:
                return ChatFormatting.GOLD;
            case VARYING:
                return durability >= 0.4f * maxDurability ? ChatFormatting.GREEN : durability >= 0.1f * maxDurability ? ChatFormatting.GOLD : ChatFormatting.RED;
        }
        return null;
    }
}
