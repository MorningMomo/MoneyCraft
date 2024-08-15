package com.morningmomo.moneycraft.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.morningmomo.moneycraft.MoneyCraft;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class StripperScreen extends HandledScreen<StripperScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(MoneyCraft.MOD_ID, "textures/gui/stripper_gui.png");

    public StripperScreen(StripperScreenHandler handler, PlayerInventory inventory, Text title){
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
    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        if (handler.isCrafting()){
            context.drawTexture(TEXTURE, x + 85, y + 30, 176, 0, 8, handler.getScaledProgress());
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }



}