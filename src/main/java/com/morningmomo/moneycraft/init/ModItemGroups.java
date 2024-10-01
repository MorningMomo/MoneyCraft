package com.morningmomo.moneycraft.init;

import com.morningmomo.moneycraft.MoneyCraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

import java.util.ArrayList;

public class ModItemGroups extends ModInit{
    public static final ArrayList<ItemGroup> ITEM_GROUP_LIST = new ArrayList<>();
    public static final ItemGroup MONEYCRAFT_ITEM_GROUP = registerNormalItemGroup("moneycraft", ModItems.COIN_FOOD);

    public static ItemGroup registerNormalItemGroup(String name, Item itemStack) {
        ITEM_GROUP_LIST.add(registerItemGroup(name, itemStack));
        return ITEM_GROUP_LIST.get(ITEM_GROUP_LIST.size() - 1);
    }
    public static void register() {
        MoneyCraft.LOGGER.info("Registering Mod ItemGroups for " + MoneyCraft.MOD_ID);
    }
}
