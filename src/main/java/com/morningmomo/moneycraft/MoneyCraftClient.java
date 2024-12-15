package com.morningmomo.moneycraft;

import com.morningmomo.moneycraft.init.ModBlocks;
import com.morningmomo.moneycraft.init.ModFluids;
import com.morningmomo.moneycraft.init.ModScreenHandlers;
import com.morningmomo.moneycraft.screens.BoilerScreen;
import com.morningmomo.moneycraft.screens.FoodConverterScreen;
import com.morningmomo.moneycraft.screens.StripperScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MoneyCraftClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(ModScreenHandlers.STRIPPER_SCREEN_HANDLER, StripperScreen::new);
        ScreenRegistry.register(ModScreenHandlers.FOOD_CONVERTER_SCREEN_HANDLER, FoodConverterScreen::new);
        ScreenRegistry.register(ModScreenHandlers.BOILER_SCREEN_HANDLER_SCREEN_HANDLER, BoilerScreen::new);
        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.STILL_PAPER_PULP, ModFluids.FLOWING_PAPER_PULP,
                new SimpleFluidRenderHandler(
                        new Identifier("moneycraft:block/still_paper_pulp"),
                        new Identifier("moneycraft:block/flowing_paper_pulp")
        ));
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FLAX_CROP_BLOCK, RenderLayer.getCutout()) ;
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WILD_FLAX, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModFluids.STILL_PAPER_PULP, ModFluids.FLOWING_PAPER_PULP);
    }
}
