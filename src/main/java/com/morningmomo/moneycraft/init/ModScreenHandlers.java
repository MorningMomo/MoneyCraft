package com.morningmomo.moneycraft.init;

import com.morningmomo.moneycraft.MoneyCraft;
import com.morningmomo.moneycraft.screens.StripperScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

import java.util.ArrayList;

public class ModScreenHandlers extends ModInit{
    public static final ArrayList<ScreenHandlerType<? extends ScreenHandler>> SCREEN_HANDLER_LIST = new ArrayList<>();

    public static final ScreenHandlerType<StripperScreenHandler> STRIPPER_SCREEN_HANDLER = registerScreenHandler("stripper_screen_handler", StripperScreenHandler::new);

    public static <T extends ScreenHandler> ScreenHandlerType<T> registerScreenHandler(String name, ExtendedScreenHandlerType.ExtendedFactory<T> factory) {
        ScreenHandlerType<T> screenHandlerType = new ExtendedScreenHandlerType<>(factory);
        SCREEN_HANDLER_LIST.add(screenHandlerType);
        return registerScreenHandler(name, screenHandlerType);
    }

    public static void register(){
        MoneyCraft.LOGGER.info("Registering Mod Screen Handlers for " + MoneyCraft.MOD_ID);
    }
    
}
