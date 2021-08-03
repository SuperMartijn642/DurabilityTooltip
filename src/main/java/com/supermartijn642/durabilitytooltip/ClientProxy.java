package com.supermartijn642.durabilitytooltip;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Created 7/1/2021 by SuperMartijn642
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientProxy {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent e){
        if(e.getItemStack().getItem().getRegistryName().getNamespace().equals("minecraft") && e.getItemStack().isDamageableItem() && !e.getFlags().isAdvanced()){
            Component durability = new TextComponent(Integer.toString(e.getItemStack().getMaxDamage())).withStyle(ChatFormatting.GOLD);
            if(e.getItemStack().isDamaged()){
                Component remainingDurability = new TextComponent(Integer.toString(e.getItemStack().getMaxDamage() - e.getItemStack().getDamageValue())).withStyle(ChatFormatting.GOLD);
                e.getToolTip().add(new TranslatableComponent("durabilitytooltip.info.damaged", remainingDurability, durability).withStyle(ChatFormatting.GRAY));
            }else
                e.getToolTip().add(new TranslatableComponent("durabilitytooltip.info.durability", durability).withStyle(ChatFormatting.GRAY));
        }
    }
}
