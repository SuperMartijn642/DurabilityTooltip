package com.supermartijn642.durabilitytooltip;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.List;

/**
 * Created 26/02/2022 by SuperMartijn642
 */
public enum TooltipStyle {

    BAR, NUMBERS, TEXT;

    public void appendTooltip(List<Component> tooltips, int durability, int maxDurability){
        boolean showTooltipHint = DurabilityTooltipConfig.showTooltipHint.get();
        ChatFormatting baseColor = DurabilityTooltipConfig.baseTooltipColor.get();
        TooltipColorStyle colorStyle = DurabilityTooltipConfig.tooltipColorStyle.get();
        ChatFormatting reactiveColor = colorStyle.getColorForDurability(baseColor, durability, maxDurability);

        switch(this){
            case BAR:
                if(showTooltipHint)
                    tooltips.add(new TranslatableComponent("durabilitytooltip.info.bar.durability_hint").withStyle(baseColor));
                int fullCharacters = Math.round(10f * durability / maxDurability);
                TextComponent innerBar = new TextComponent("");
                for(int character = 0; character < 10; character++)
                    innerBar.append(new TranslatableComponent(character < fullCharacters ? "durabilitytooltip.info.bar.full_symbol" : "durabilitytooltip.info.bar.empty_symbol").withStyle(reactiveColor));
                Component bar = new TranslatableComponent("durabilitytooltip.info.bar.bar_line", innerBar).withStyle(baseColor);
                tooltips.add(bar);
                break;

            case NUMBERS:
                Component durabilityComponent = new TextComponent(Integer.toString(durability)).withStyle(reactiveColor);
                Component maxDurabilityComponent = new TextComponent(Integer.toString(maxDurability)).withStyle(colorStyle == TooltipColorStyle.VARYING ? baseColor : reactiveColor);
                Component numbers;
                if(durability == maxDurability)
                    numbers = new TranslatableComponent("durabilitytooltip.info.numbers.full_durability", maxDurabilityComponent).withStyle(baseColor);
                else
                    numbers = new TranslatableComponent("durabilitytooltip.info.numbers.damaged", durabilityComponent, maxDurabilityComponent).withStyle(baseColor);
                if(showTooltipHint)
                    numbers = new TranslatableComponent("durabilitytooltip.info.numbers.durability_hint", numbers).withStyle(baseColor);
                tooltips.add(numbers);
                break;

            case TEXT:
                String translationKey = durability == maxDurability ? "durabilitytooltip.info.text.full_durability"
                    : durability >= 0.4f * maxDurability ? "durabilitytooltip.info.text.damaged"
                    : durability >= 0.1f * maxDurability ? "durabilitytooltip.info.text.severely_damaged"
                    : "durabilitytooltip.info.text.nearly_broken";
                Component tooltip = new TranslatableComponent(translationKey).withStyle(reactiveColor);
                if(showTooltipHint)
                    tooltip = new TranslatableComponent("durabilitytooltip.info.text.durability_hint", tooltip).withStyle(baseColor);
                tooltips.add(tooltip);
                break;
        }
    }
}
