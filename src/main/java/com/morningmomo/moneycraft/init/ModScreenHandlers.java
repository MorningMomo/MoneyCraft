package com.morningmomo.moneycraft.init;

import com.morningmomo.moneycraft.MoneyCraft;
import com.morningmomo.moneycraft.screens.BoilerScreenHandler;
import com.morningmomo.moneycraft.screens.FoodConverterScreenHandler;
import com.morningmomo.moneycraft.screens.StripperScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

import java.util.ArrayList;

public class ModScreenHandlers extends ModInit{
    public static final ArrayList<ScreenHandlerType<? extends ScreenHandler>> SCREEN_HANDLER_LIST = new ArrayList<>();

    public static final ScreenHandlerType<StripperScreenHandler> STRIPPER_SCREEN_HANDLER = registerNormalScreenHandler("stripper_screen_handler", StripperScreenHandler::new);
    public static final ScreenHandlerType<FoodConverterScreenHandler> FOOD_CONVERTER_SCREEN_HANDLER = registerNormalScreenHandler("food_converter_screen_handler", FoodConverterScreenHandler::new);
    public static final ScreenHandlerType<BoilerScreenHandler> BOILER_SCREEN_HANDLER_SCREEN_HANDLER = registerNormalScreenHandler("boiler_screen_handler", BoilerScreenHandler::new);

    public static <T extends ScreenHandler> ScreenHandlerType<T> registerNormalScreenHandler(String name, ExtendedScreenHandlerType.ExtendedFactory<T> factory) {
        ScreenHandlerType<T> screenHandlerType = new ExtendedScreenHandlerType<>(factory);
        SCREEN_HANDLER_LIST.add(screenHandlerType);
        return registerScreenHandler(name, screenHandlerType);
    }

    public static void register(){
        MoneyCraft.LOGGER.info("Registering Mod Screen Handlers for " + MoneyCraft.MOD_ID);
    }
    
}
