package com.supermartijn642.durabilitytooltip;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
        ItemStack stack = e.getItemStack();
        ITooltipFlag flag = e.getFlags();
        List<ITextComponent> lines = e.getToolTip();
        if((!DurabilityTooltipConfig.onlyVanillaTools.get() || ForgeRegistries.ITEMS.getKey(stack.getItem()).getNamespace().equals("minecraft"))
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
