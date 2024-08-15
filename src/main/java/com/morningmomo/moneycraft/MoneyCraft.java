package com.morningmomo.moneycraft;

import com.morningmomo.moneycraft.init.*;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoneyCraft implements ModInitializer {
	public static final String MOD_ID = "moneycraft";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("I'm glad that you're using my mod!");
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerModBlockEntities();
		ModItemGroups.registerModItemGroups();
		ModScreenHandlers.registerModScreenHandlers();
		LOGGER.info("Finish!");
	}
}