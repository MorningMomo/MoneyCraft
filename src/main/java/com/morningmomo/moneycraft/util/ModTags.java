package com.morningmomo.moneycraft.util;

import com.morningmomo.moneycraft.MoneyCraft;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static TagKey<Block> WILD_CROPS;
    public static TagKey<Fluid> FLUIDS;
    public static void registerTags() {
         WILD_CROPS = TagKey.of(RegistryKeys.BLOCK, new Identifier(MoneyCraft.MOD_ID, "wild_crops"));
         FLUIDS = TagKey.of(RegistryKeys.FLUID, new Identifier(MoneyCraft.MOD_ID, "fluids"));
    }
}
