package com.supermartijn642.durabilitytooltip;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created 7/1/2021 by SuperMartijn642
 */
@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
public class DurabilityTooltipClient {

    private static String lastBlackListConfig = "";
    private static Set<String> blackListedMods = new HashSet<>();

    public static boolean isBlackListed(Item item){
        String owningMod = BuiltInRegistries.ITEM.getKey(item).getNamespace();

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
        ItemStack stack = e.getItemStack();
        TooltipFlag flag = e.getFlags();
        List<Component> lines = e.getToolTip();
        if((!DurabilityTooltipConfig.onlyVanillaTools.get() || BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace().equals("minecraft"))
            && !isBlackListed(stack.getItem())
            && (DurabilityTooltipConfig.showWhenFull.get() || stack.isDamaged())
            && stack.isDamageableItem() && (!flag.isAdvanced() || !stack.isDamaged())){
            int maxDurability = stack.getMaxDamage();
            int durability = maxDurability - stack.getDamageValue();
            if(DurabilityTooltipConfig.showWhenBroken.get() || durability > 0)
                DurabilityTooltipConfig.tooltipStyle.get().appendTooltip(lines, durability, maxDurability);
        }
    }
}
