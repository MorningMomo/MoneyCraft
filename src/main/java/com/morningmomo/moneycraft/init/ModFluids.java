package com.morningmomo.moneycraft.init;

import com.morningmomo.moneycraft.MoneyCraft;
import com.morningmomo.moneycraft.fluids.PaperFluid;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class ModFluids {
    public static final ArrayList<FlowableFluid> STILL_FLUID_LIST = new ArrayList<>();
    public static final ArrayList<FlowableFluid> FLOWING_FLUID_LIST = new ArrayList<>();
    public static final FlowableFluid STILL_PAPER_PULP = registerNormalFluid("paper_pulp", new PaperFluid.Still(), 0);
    public static final FlowableFluid FLOWING_PAPER_PULP = registerNormalFluid("flowing_paper_pulp", new PaperFluid.Flowing(),1);

    public static FlowableFluid registerNormalFluid(String name, FlowableFluid state, int i) {
        if (i == 0) {
            STILL_FLUID_LIST.add(Registry.register(Registries.FLUID, new Identifier(MoneyCraft.MOD_ID, name), state));
            return STILL_FLUID_LIST.get(STILL_FLUID_LIST.size()-1);
        }else {
            FLOWING_FLUID_LIST.add(Registry.register(Registries.FLUID, new Identifier(MoneyCraft.MOD_ID, name), state));
            return FLOWING_FLUID_LIST.get(FLOWING_FLUID_LIST.size()-1);
        }
    }

    public static void registerFluids() {
        MoneyCraft.LOGGER.info("Registering ModFluids for " + MoneyCraft.MOD_ID);
    }
}