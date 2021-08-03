package com.supermartijn642.durabilitytooltip;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
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
            ITextComponent durability = new StringTextComponent(Integer.toString(e.getItemStack().getMaxDamage())).withStyle(TextFormatting.GOLD);
            if(e.getItemStack().isDamaged()){
                ITextComponent remainingDurability = new StringTextComponent(Integer.toString(e.getItemStack().getMaxDamage() - e.getItemStack().getDamageValue())).withStyle(TextFormatting.GOLD);
                e.getToolTip().add(new TranslationTextComponent("durabilitytooltip.info.damaged", remainingDurability, durability).withStyle(TextFormatting.GRAY));
            }else
                e.getToolTip().add(new TranslationTextComponent("durabilitytooltip.info.durability", durability).withStyle(TextFormatting.GRAY));
        }
    }
}
