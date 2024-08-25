package com.morningmomo.moneycraft;

import com.morningmomo.moneycraft.init.ModBlocks;
import com.morningmomo.moneycraft.init.ModScreenHandlers;
import com.morningmomo.moneycraft.screen.StripperScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class MoneyCraftClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(ModScreenHandlers.STRIPPER_SCREEN_HANDLER, StripperScreen::new);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FLAX_CROP_BLOCK, RenderLayer.getCutout()) ;
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WILD_FLAX, RenderLayer.getCutout());
    }
}
