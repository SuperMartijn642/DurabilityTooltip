package com.supermartijn642.durabilitytooltip;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

/**
 * Created 7/1/2021 by SuperMartijn642
 */
public class DurabilityTooltipClient implements ClientModInitializer {

    @Override
    public void onInitializeClient(){
        ItemTooltipCallback.EVENT.register(DurabilityTooltipClient::onItemTooltip);
    }

    public static void onItemTooltip(ItemStack stack, TooltipFlag flag, List<Component> lines){
        if((!DurabilityTooltipConfig.onlyVanillaTools.get() || BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace().equals("minecraft")) && stack.isDamageableItem() && (!flag.isAdvanced() || !stack.isDamaged())){
            int maxDurability = stack.getMaxDamage();
            int durability = maxDurability - stack.getDamageValue();
            DurabilityTooltipConfig.tooltipStyle.get().appendTooltip(lines, durability, maxDurability);
        }
    }
}
