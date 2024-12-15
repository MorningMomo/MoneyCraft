package com.morningmomo.moneycraft.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.morningmomo.moneycraft.MoneyCraft;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BoilerScreen extends HandledScreen<BoilerScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(MoneyCraft.MOD_ID, "textures/gui/boiler1_gui.png");

    public BoilerScreen(BoilerScreenHandler handler, PlayerInventory inventory, Text title){
        super(handler, inventory, title);
    }

    @Override
    protected void init(){
        super.init();
        titleY = 1000;
        playerInventoryTitleY = 1000;
    }

    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
        renderProgressArrow(context, x, y);
        renderBurning(context, x, y);
    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        if (handler.isCrafting()){
            context.drawTexture(TEXTURE, x + 55, y + 18, 176, 14, handler.getScaledProgress(), 51);
        }
    }
    private void renderBurning(DrawContext context, int x, int y) {
        int a = 14 - handler.getScaledBurnTime();
        if (handler.isBurning()) {
            context.drawTexture(TEXTURE, x + 101, y + 43 + a, 176, a, 16, handler.getScaledBurnTime());
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}