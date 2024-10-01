package com.morningmomo.moneycraft.init;

import com.morningmomo.moneycraft.MoneyCraft;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.*;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModInit {
    public ModInit() {
    }

    public enum ModEnums {
        //Item

        //BlockItem

        //Block
        BLOCK,
        CROP_BLOCK,
        MACHINE_BLOCK,
        MACHINE_BLOCK_ENTITY,

        //Fluids
        STILL,
        FLOWING
    }

    //Register Identifiers
    public static Identifier setId(String name) {
        return new Identifier(MoneyCraft.MOD_ID, name);
    }


    //Register Items
    public static Item ItemRegistry(String name, int maxCount, Rarity rarity) {
        return Registry.register(Registries.ITEM, setId(name), registerItem(maxCount, rarity));
    }

    public static Item FoodItemRegistry(String name, int maxCount, Rarity rarity, int hunger, float saturation, boolean isAlwaysEdible) {
        return Registry.register(Registries.ITEM, setId(name), registerFoodItem(maxCount, rarity, hunger, saturation, isAlwaysEdible));
    }

    public static BlockItem BlockItemRegistry(String name, int maxCount, Rarity rarity, Block block) {
        return Registry.register(Registries.ITEM, setId(name), registerBlockItem(block, maxCount, rarity));
    }

    public static AliasedBlockItem CropItemRegistry(String name, int maxCount, Rarity rarity, Block block) {
        return Registry.register(Registries.ITEM, setId(name), registerCropBlockItem(block, maxCount, rarity));
    }

    public static BucketItem BucketItemRegistry(String name, Fluid fluid, Rarity rarity) {
        return Registry.register(Registries.ITEM, setId(name), registerBucketItem(fluid, rarity));
    }

    public static Item registerItem(int maxCount, Rarity rarity) {
        return new Item(new FabricItemSettings().maxCount(maxCount).rarity(rarity));
    }

    public static Item registerFoodItem(int maxCount, Rarity rarity, int hunger, float saturation, boolean isAlwaysEdible) {
        if (isAlwaysEdible) {
            return new Item(itemSettings(maxCount, rarity).food(foodSettings(hunger, saturation).alwaysEdible().build()));
        } else {
            return new Item(itemSettings(maxCount, rarity).food(foodSettings(hunger, saturation).build()));
        }
    }

    public static FabricItemSettings itemSettings(int maxCount, Rarity rarity) {
        return new FabricItemSettings().maxCount(maxCount).rarity(rarity);
    }

    public static FoodComponent.Builder foodSettings(int hunger, float saturation) {
        return new FoodComponent.Builder().hunger(hunger).saturationModifier(saturation);
    }


    //Register Block Items
    public static BlockItem registerBlockItem(Block block, int maxCount, Rarity rarity) {
        return new BlockItem(block, itemSettings(maxCount, rarity));
    }

    public static AliasedBlockItem registerCropBlockItem(Block block, int maxCount, Rarity rarity) {
        return new AliasedBlockItem(block, itemSettings(maxCount, rarity));
    }

    public static BucketItem registerBucketItem(Fluid fluid, Rarity rarity) {
        return new BucketItem(fluid, new Item.Settings().recipeRemainder(Items.BUCKET).rarity(rarity));
    }


    //Register Blocks
    public static Block BlockRegistry(String name, float strength, float hardness) {
        return Registry.register(Registries.BLOCK, setId(name), registerBlock(hardness, strength));
    }

    public static Block BlockRegistry(String name, Block block) {
        return Registry.register(Registries.BLOCK, setId(name), block);
    }

    public static Block registerBlock(float hardness, float strength) {
        return new Block(blockSettings(hardness, strength));
    }

    public static FabricBlockSettings blockSettings(float hardness, float strength) {
        return FabricBlockSettings.create().strength(hardness, strength);
    }

    public static AbstractBlock.Settings cropSettings() {
        return AbstractBlock.Settings.copy(Blocks.WHEAT);
    }


    //Register Block Entities
    public static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(String name, BlockEntityType<T> blockEntityType) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, setId(name), blockEntityType);
    }


    //Register Recipes
    public static RecipeType<?> registerRecipes(String name, RecipeType<?> type) {
        return Registry.register(Registries.RECIPE_TYPE, setId(name), type);
    }

    public static RecipeSerializer<?> registerRecipes(String name, RecipeSerializer<?> serializer) {
        return Registry.register(Registries.RECIPE_SERIALIZER, setId(name), serializer);
    }


    //Register Fluids
    public static FlowableFluid registerFluid(String name, FlowableFluid state) {
        return Registry.register(Registries.FLUID, setId(name), state);
    }


    //Register ItemGroups
    public static ItemGroup registerItemGroup(String name, Item itemStack) {
        return Registry.register(Registries.ITEM_GROUP, setId(name),
                FabricItemGroup.builder()
                        .displayName(Text.translatable("itemGroup.moneycraft." + name))
                        .icon(() -> new ItemStack(itemStack))
                        .entries((displayContext, entries) ->
                        {
                            for (Item item : ModItems.ITEM_LIST) {
                                entries.add(item);
                            }
                            for (BlockItem blockItem : ModItems.BLOCK_ITEM_LIST) {
                                entries.add(blockItem);
                            }
                            for (BucketItem bucketItem : ModItems.BUCKET_ITEM_LIST) {
                                entries.add(bucketItem);
                            }
                            for (BlockItem machineItem : ModItems.MACHINE_ITEM_LIST) {
                                entries.add(machineItem);
                            }
                        })
                        .build());
    }


    //Register Screen Handlers
    public static <T extends ScreenHandler> ScreenHandlerType<T> registerScreenHandler(String name, ScreenHandlerType<T> screenHandlerType) {
        return Registry.register(Registries.SCREEN_HANDLER, setId(name), screenHandlerType);
    }


    //Methods
    public static String getBlockName(Block block) {
        return Registries.BLOCK.getId(block).getPath();
    }


    public static void register() {
        MoneyCraft.LOGGER.info("Registering Mod Init for " + MoneyCraft.MOD_ID);
    }
}