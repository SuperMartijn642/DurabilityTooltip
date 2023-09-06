package com.supermartijn642.durabilitytooltip;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created 7/1/2021 by SuperMartijn642
 */
public class DurabilityTooltipClient implements ClientModInitializer {

    @Override
    public void onInitializeClient(){
        ItemTooltipCallback.EVENT.register(DurabilityTooltipClient::onItemTooltip);
    }

    private static String lastBlackListConfig = "";
    private static Set<String> blackListedMods = new HashSet<>();

    public static boolean isBlackListed(Item item){
        String owningMod = Registry.ITEM.getKey(item).getNamespace();

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

    public static void onItemTooltip(ItemStack stack, TooltipFlag flag, List<Component> lines){
        if((!DurabilityTooltipConfig.onlyVanillaTools.get() || Registry.ITEM.getKey(stack.getItem()).getNamespace().equals("minecraft"))
            && !isBlackListed(stack.getItem())
            && (DurabilityTooltipConfig.showWhenFull.get() || stack.isDamaged())
            && stack.isDamageableItem() && (!flag.isAdvanced() || !stack.isDamaged())){
            int maxDurability = stack.getMaxDamage();
            int durability = maxDurability - stack.getDamageValue();
            DurabilityTooltipConfig.tooltipStyle.get().appendTooltip(lines, durability, maxDurability);
        }
    }
}
