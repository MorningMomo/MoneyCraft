package com.morningmomo.moneycraft.init;

import com.morningmomo.moneycraft.MoneyCraft;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Objects;

public class ModItemGroups {
    public static final ItemGroup MONEYCRAFT_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(MoneyCraft.MOD_ID, "moneycraft"),
            FabricItemGroup.builder().displayName(Text.translatable("itemGroup.moneycraft.moneycraft")).icon(()-> new ItemStack(ModItems.COIN_FOOD))
                    .entries((displayContext, entries) ->
                    {
                        for (Item item : ModItems.ITEM_LIST){
                            entries.add(item);
                        }
                        for (BlockItem blockItem : ModItems.BLOCK_ITEM_LIST){
                            entries.add(blockItem);
                        }
                    })
                    .build());
    public static void registerModItemGroups() {
        MoneyCraft.LOGGER.info("Registering Mod ItemGroups for " + MoneyCraft.MOD_ID + ":");
        MoneyCraft.LOGGER.info(Objects.requireNonNull(Registries.ITEM_GROUP.getId(MONEYCRAFT_ITEM_GROUP)).toString());
    }
}
