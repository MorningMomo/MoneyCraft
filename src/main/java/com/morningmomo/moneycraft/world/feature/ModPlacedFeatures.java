package com.morningmomo.moneycraft.world.feature;

import com.morningmomo.moneycraft.MoneyCraft;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

import java.util.List;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> WILD_FLAX_PLACED_KEY = registerKey("wild_flax_placed");
    public static final RegistryKey<PlacedFeature> WILD_COTTON_PLACED_KEY = registerKey("wild_cotton_placed");

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(MoneyCraft.MOD_ID,name));
    }

    public static void bootstrap(Registerable<PlacedFeature> context) {
        var registerEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

       register(context, WILD_FLAX_PLACED_KEY, registerEntryLookup.getOrThrow(ModConfiguredFeatures.WILD_FLAX_KEY),
                RarityFilterPlacementModifier.of(10),
                SquarePlacementModifier.of(),
                PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
                BiomePlacementModifier.of());
       register(context, WILD_COTTON_PLACED_KEY, registerEntryLookup.getOrThrow(ModConfiguredFeatures.WILD_COTTON_KEY),
               RarityFilterPlacementModifier.of(10),
               SquarePlacementModifier.of(),
               PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
               BiomePlacementModifier.of());
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration, PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }


}
