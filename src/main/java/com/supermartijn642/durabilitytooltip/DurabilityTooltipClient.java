package com.supermartijn642.durabilitytooltip;

import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created 7/1/2021 by SuperMartijn642
 */
@Mod.EventBusSubscriber(Side.CLIENT)
public class DurabilityTooltipClient {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onItemTooltip(ItemTooltipEvent e){
        if((!DurabilityTooltipConfig.onlyVanillaTools.get() || e.getItemStack().getItem().getRegistryName().getResourceDomain().equals("minecraft")) && e.getItemStack().isItemStackDamageable() && (!e.getFlags().isAdvanced() || !e.getItemStack().isItemDamaged())){
            int maxDurability = e.getItemStack().getMaxDamage();
            int durability = maxDurability - e.getItemStack().getItemDamage();
            DurabilityTooltipConfig.tooltipStyle.get().appendTooltip(e.getToolTip(), durability, maxDurability);
        }
    }
}
