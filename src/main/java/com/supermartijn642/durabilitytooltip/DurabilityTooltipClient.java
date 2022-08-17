package com.supermartijn642.durabilitytooltip;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Created 7/1/2021 by SuperMartijn642
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DurabilityTooltipClient {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onItemTooltip(ItemTooltipEvent e){
        if((!DurabilityTooltipConfig.onlyVanillaTools.get() || ForgeRegistries.ITEMS.getKey(e.getItemStack().getItem()).getNamespace().equals("minecraft")) && e.getItemStack().isDamageableItem() && (!e.getFlags().isAdvanced() || !e.getItemStack().isDamaged())){
            int maxDurability = e.getItemStack().getMaxDamage();
            int durability = maxDurability - e.getItemStack().getDamageValue();
            DurabilityTooltipConfig.tooltipStyle.get().appendTooltip(e.getToolTip(), durability, maxDurability);
        }
    }
}
