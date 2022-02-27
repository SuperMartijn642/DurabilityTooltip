package com.supermartijn642.durabilitytooltip;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

/**
 * Created 26/02/2022 by SuperMartijn642
 */
public enum TooltipStyle {

    BAR, NUMBERS, TEXT;

    public void appendTooltip(List<ITextComponent> tooltips, int durability, int maxDurability){
        boolean showTooltipHint = DurabilityTooltipConfig.showTooltipHint.get();
        TextFormatting baseColor = DurabilityTooltipConfig.baseTooltipColor.get();
        TooltipColorStyle colorStyle = DurabilityTooltipConfig.tooltipColorStyle.get();
        TextFormatting reactiveColor = colorStyle.getColorForDurability(baseColor, durability, maxDurability);

        switch(this){
            case BAR:
                if(showTooltipHint)
                    tooltips.add(new TranslationTextComponent("durabilitytooltip.info.bar.durability_hint").withStyle(baseColor));
                int fullCharacters = Math.round(10f * durability / maxDurability);
                StringTextComponent innerBar = new StringTextComponent("");
                for(int character = 0; character < 10; character++)
                    innerBar.append(new TranslationTextComponent(character < fullCharacters ? "durabilitytooltip.info.bar.full_symbol" : "durabilitytooltip.info.bar.empty_symbol").withStyle(reactiveColor));
                ITextComponent bar = new TranslationTextComponent("durabilitytooltip.info.bar.bar_line", innerBar).withStyle(baseColor);
                tooltips.add(bar);
                break;

            case NUMBERS:
                ITextComponent durabilityComponent = new StringTextComponent(Integer.toString(durability)).withStyle(reactiveColor);
                ITextComponent maxDurabilityComponent = new StringTextComponent(Integer.toString(maxDurability)).withStyle(colorStyle == TooltipColorStyle.VARYING ? baseColor : reactiveColor);
                ITextComponent numbers;
                if(durability == maxDurability)
                    numbers = new TranslationTextComponent("durabilitytooltip.info.numbers.full_durability", maxDurabilityComponent).withStyle(baseColor);
                else
                    numbers = new TranslationTextComponent("durabilitytooltip.info.numbers.damaged", durabilityComponent, maxDurabilityComponent).withStyle(baseColor);
                if(showTooltipHint)
                    numbers = new TranslationTextComponent("durabilitytooltip.info.numbers.durability_hint", numbers).withStyle(baseColor);
                tooltips.add(numbers);
                break;

            case TEXT:
                String translationKey = durability == maxDurability ? "durabilitytooltip.info.text.full_durability"
                    : durability >= 0.4f * maxDurability ? "durabilitytooltip.info.text.damaged"
                    : durability >= 0.1f * maxDurability ? "durabilitytooltip.info.text.severely_damaged"
                    : "durabilitytooltip.info.text.nearly_broken";
                ITextComponent tooltip = new TranslationTextComponent(translationKey).withStyle(reactiveColor);
                if(showTooltipHint)
                    tooltip = new TranslationTextComponent("durabilitytooltip.info.text.durability_hint", tooltip).withStyle(baseColor);
                tooltips.add(tooltip);
                break;
        }
    }
}
