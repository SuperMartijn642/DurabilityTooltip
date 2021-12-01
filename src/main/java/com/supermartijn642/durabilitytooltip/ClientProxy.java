package com.supermartijn642.durabilitytooltip;

import net.minecraft.util.text.*;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created 7/1/2021 by SuperMartijn642
 */
@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent e){
        if((!DurabilityTooltipConfig.onlyVanillaTools.get() || e.getItemStack().getItem().getRegistryName().getResourceDomain().equals("minecraft")) && e.getItemStack().isItemStackDamageable() && !e.getFlags().isAdvanced()){
            ITextComponent durability = new TextComponentString(Integer.toString(e.getItemStack().getMaxDamage())).setStyle(new Style().setColor(TextFormatting.GOLD));
            if(e.getItemStack().isItemDamaged()){
                ITextComponent remainingDurability = new TextComponentString(Integer.toString(e.getItemStack().getMaxDamage() - e.getItemStack().getItemDamage())).setStyle(new Style().setColor(TextFormatting.GOLD));
                e.getToolTip().add(new TextComponentTranslation("durabilitytooltip.info.damaged", remainingDurability, durability).setStyle(new Style().setColor(TextFormatting.GRAY)).getFormattedText());
            }else
                e.getToolTip().add(new TextComponentTranslation("durabilitytooltip.info.durability", durability).setStyle(new Style().setColor(TextFormatting.GRAY)).getFormattedText());
        }
    }
}
