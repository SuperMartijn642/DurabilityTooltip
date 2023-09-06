package com.supermartijn642.durabilitytooltip;

import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created 7/1/2021 by SuperMartijn642
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DurabilityTooltipClient {

    private static String lastBlackListConfig = "";
    private static Set<String> blackListedMods = new HashSet<>();

    public static boolean isBlackListed(Item item){
        String owningMod = ForgeRegistries.ITEMS.getKey(item).getNamespace();

        // Update the list of blacklisted mods
        if(!DurabilityTooltipConfig.blackListedMods.get().equals(lastBlackListConfig)){
            blackListedMods.clear();
            Arrays.stream(DurabilityTooltipConfig.blackListedMods.get().split(","))
                .map(String::trim)
                .filter(s -> {
                    boolean valid = s.matches("[a-z0-9_.-]+");
                    if(!valid)
                        DurabilityTooltip.LOGGER.error("Invalid modid '" + s + "' in config value 'blackListedMods'!");
                    return valid;
                })
                .forEach(blackListedMods::add);
            lastBlackListConfig = DurabilityTooltipConfig.blackListedMods.get();
        }

        return blackListedMods.contains(owningMod);
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onItemTooltip(ItemTooltipEvent e){
        if((!DurabilityTooltipConfig.onlyVanillaTools.get() || ForgeRegistries.ITEMS.getKey(e.getItemStack().getItem()).getNamespace().equals("minecraft"))
            && !isBlackListed(e.getItemStack().getItem())
            && (DurabilityTooltipConfig.showWhenFull.get() || e.getItemStack().isDamaged())
            && e.getItemStack().isDamageableItem() && (!e.getFlags().isAdvanced() || !e.getItemStack().isDamaged())){
            int maxDurability = e.getItemStack().getMaxDamage();
            int durability = maxDurability - e.getItemStack().getDamageValue();
            DurabilityTooltipConfig.tooltipStyle.get().appendTooltip(e.getToolTip(), durability, maxDurability);
        }
    }
}
