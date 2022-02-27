package com.supermartijn642.durabilitytooltip;

import net.minecraft.util.text.*;

import java.util.List;

/**
 * Created 26/02/2022 by SuperMartijn642
 */
public enum TooltipStyle {

    BAR, NUMBERS, TEXT;

    public void appendTooltip(List<String> tooltips, int durability, int maxDurability){
        boolean showTooltipHint = DurabilityTooltipConfig.showTooltipHint.get();
        TextFormatting baseColor = DurabilityTooltipConfig.baseTooltipColor.get();
        TooltipColorStyle colorStyle = DurabilityTooltipConfig.tooltipColorStyle.get();
        TextFormatting reactiveColor = colorStyle.getColorForDurability(baseColor, durability, maxDurability);

        switch(this){
            case BAR:
                if(showTooltipHint)
                    tooltips.add(new TextComponentTranslation("durabilitytooltip.info.bar.durability_hint").setStyle(new Style().setColor(baseColor)).getFormattedText());
                int fullCharacters = Math.round(10f * durability / maxDurability);
                TextComponentString innerBar = new TextComponentString("");
                for(int character = 0; character < 10; character++)
                    innerBar.appendSibling(new TextComponentTranslation(character < fullCharacters ? "durabilitytooltip.info.bar.full_symbol" : "durabilitytooltip.info.bar.empty_symbol").setStyle(new Style().setColor(reactiveColor)));
                ITextComponent bar = new TextComponentTranslation("durabilitytooltip.info.bar.bar_line", innerBar).setStyle(new Style().setColor(baseColor));
                tooltips.add(bar.getFormattedText());
                break;

            case NUMBERS:
                ITextComponent durabilityComponent = new TextComponentString(Integer.toString(durability)).setStyle(new Style().setColor(reactiveColor));
                ITextComponent maxDurabilityComponent = new TextComponentString(Integer.toString(maxDurability)).setStyle(new Style().setColor(colorStyle == TooltipColorStyle.VARYING ? baseColor : reactiveColor));
                ITextComponent numbers;
                if(durability == maxDurability)
                    numbers = new TextComponentTranslation("durabilitytooltip.info.numbers.full_durability", maxDurabilityComponent).setStyle(new Style().setColor(baseColor));
                else
                    numbers = new TextComponentTranslation("durabilitytooltip.info.numbers.damaged", durabilityComponent, maxDurabilityComponent).setStyle(new Style().setColor(baseColor));
                if(showTooltipHint)
                    numbers = new TextComponentTranslation("durabilitytooltip.info.numbers.durability_hint", numbers).setStyle(new Style().setColor(baseColor));
                tooltips.add(numbers.getFormattedText());
                break;

            case TEXT:
                String translationKey = durability == maxDurability ? "durabilitytooltip.info.text.full_durability"
                    : durability >= 0.4f * maxDurability ? "durabilitytooltip.info.text.damaged"
                    : durability >= 0.1f * maxDurability ? "durabilitytooltip.info.text.severely_damaged"
                    : "durabilitytooltip.info.text.nearly_broken";
                ITextComponent tooltip = new TextComponentTranslation(translationKey).setStyle(new Style().setColor(reactiveColor));
                if(showTooltipHint)
                    tooltip = new TextComponentTranslation("durabilitytooltip.info.text.durability_hint", tooltip).setStyle(new Style().setColor(baseColor));
                tooltips.add(tooltip.getFormattedText());
                break;
        }
    }
}
