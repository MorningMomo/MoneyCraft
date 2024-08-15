package com.morningmomo.moneycraft.init;

import com.morningmomo.moneycraft.MoneyCraft;
import com.morningmomo.moneycraft.screen.StripperScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class ModScreenHandlers {
    public static final ArrayList<ScreenHandlerType<? extends ScreenHandler>> SCREEN_HANDLER_LIST = new ArrayList<>();

    public static final ScreenHandlerType<StripperScreenHandler> STRIPPER_SCREEN_HANDLER = registerScreenHandler("stripper_screen_handler", StripperScreenHandler::new);

    public static <T extends ScreenHandler> ScreenHandlerType<T> registerScreenHandler(String name, ExtendedScreenHandlerType.ExtendedFactory<T> factory) {
        ScreenHandlerType<T> screenHandlerType = new ExtendedScreenHandlerType<>(factory);
        SCREEN_HANDLER_LIST.add(screenHandlerType);
        return Registry.register(Registries.SCREEN_HANDLER, new Identifier(MoneyCraft.MOD_ID, name), screenHandlerType);
    }

    public static void registerModScreenHandlers(){
        MoneyCraft.LOGGER.info("Registering Mod Screen Handlers for " + MoneyCraft.MOD_ID);
    }
    
}
