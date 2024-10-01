package com.morningmomo.moneycraft.init;

import com.morningmomo.moneycraft.MoneyCraft;
import com.morningmomo.moneycraft.fluids.PaperFluid;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class ModFluids extends ModInit{
    public static final ArrayList<FlowableFluid> STILL_FLUID_LIST = new ArrayList<>();
    public static final ArrayList<FlowableFluid> FLOWING_FLUID_LIST = new ArrayList<>();
    public static final FlowableFluid STILL_PAPER_PULP = registerNormalFluid("paper_pulp", new PaperFluid.Still(), ModEnums.STILL);
    public static final FlowableFluid FLOWING_PAPER_PULP = registerNormalFluid("flowing_paper_pulp", new PaperFluid.Flowing(),ModEnums.FLOWING);

    public static FlowableFluid registerNormalFluid(String name, FlowableFluid state, ModEnums x) {
        if (x == ModEnums.STILL) {
            STILL_FLUID_LIST.add(registerFluid(name, state));
            return STILL_FLUID_LIST.get(STILL_FLUID_LIST.size()-1);
        }else {
            FLOWING_FLUID_LIST.add(registerFluid(name, state));
            return FLOWING_FLUID_LIST.get(FLOWING_FLUID_LIST.size()-1);
        }
    }

    public static void register() {
        MoneyCraft.LOGGER.info("Registering ModFluids for " + MoneyCraft.MOD_ID);
    }
}