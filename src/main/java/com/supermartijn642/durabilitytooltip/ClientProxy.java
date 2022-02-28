package com.supermartijn642.durabilitytooltip;

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
        if((!DurabilityTooltipConfig.onlyVanillaTools.get() || e.getItemStack().getItem().getRegistryName().getNamespace().equals("minecraft")) && e.getItemStack().isDamageableItem() && (!e.getFlags().isAdvanced() || !e.getItemStack().isDamaged())){
            int maxDurability = e.getItemStack().getMaxDamage();
            int durability = maxDurability - e.getItemStack().getDamageValue();
            DurabilityTooltipConfig.tooltipStyle.get().appendTooltip(e.getToolTip(), durability, maxDurability);
        }
    }
}
