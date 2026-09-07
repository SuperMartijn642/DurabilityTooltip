package com.supermartijn642.durabilitytooltip;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created 7/1/2021 by SuperMartijn642
 */
@Mod.EventBusSubscriber(Side.CLIENT)
public class DurabilityTooltipClient {

    private static String lastBlackListConfig = "";
    private static Set<String> blackListedMods = new HashSet<>();

    public static boolean isBlackListed(Item item){
        String owningMod = ForgeRegistries.ITEMS.getKey(item).getResourceDomain();

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
        ITooltipFlag flag = e.getFlags();
        List<String> lines = e.getToolTip();
        if((!DurabilityTooltipConfig.onlyVanillaTools.get() || ForgeRegistries.ITEMS.getKey(stack.getItem()).getResourceDomain().equals("minecraft"))
            && !isBlackListed(stack.getItem())
            && (DurabilityTooltipConfig.showWhenFull.get() || stack.isItemDamaged())
            && stack.isItemStackDamageable() && (!flag.isAdvanced() || !stack.isItemDamaged())){
            int maxDurability = stack.getMaxDamage();
            int durability = maxDurability - stack.getItemDamage();
            if(DurabilityTooltipConfig.showWhenBroken.get() || durability > 0)
                DurabilityTooltipConfig.tooltipStyle.get().appendTooltip(lines, durability, maxDurability);
        }
    }
}
