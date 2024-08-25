package com.morningmomo.moneycraft;

import com.morningmomo.moneycraft.init.*;
import com.morningmomo.moneycraft.util.ModTags;
import com.morningmomo.moneycraft.world.generation.ModWorldGeneration;
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
		ModWorldGeneration.generateModWorldGeneration();
		ModTags.registerTags();
		LOGGER.info("Finish!");
	}
}