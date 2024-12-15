package com.morningmomo.moneycraft.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.morningmomo.moneycraft.init.ModBlocks;
import com.morningmomo.moneycraft.init.ModRecipes;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class BoilerRecipe implements Recipe<SimpleInventory> {
    private final Identifier id;
    private final ItemStack output;
    private final DefaultedList<Ingredient> inputs;
    private final Ingredient fuel;

    public BoilerRecipe(Identifier id, DefaultedList<Ingredient> inputs, ItemStack output, Ingredient fuel) {
        this.id = id;
        this.inputs = inputs;
        this.output = output;
        this.fuel = fuel;
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        int pos = 0;
        for (int i = 0; i <=2; i++){
            if (this.inputs.get(i).test(inventory.getStack(i))) {
                pos++;
            }
        }
        return pos == 3 && this.fuel.test(inventory.getStack(3));
    }

    @Override
    public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        return output;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return output;
    }

    public ItemStack createIcon() {
        return ModBlocks.BOILER.asItem().getDefaultStack();
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.ofSize(this.inputs.size());
        list.addAll(inputs);
        return list;
    }

    public Ingredient getFuel() {
        return this.fuel;
    }

    public DefaultedList<Ingredient> getInputs() {
        DefaultedList<Ingredient> list = this.getIngredients();
        list.add(this.getFuel());
        return list;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.BOILER_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.BOILER_TYPE;
    }

    public static class Type implements RecipeType<BoilerRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
    }

    public static final class Serializer implements RecipeSerializer<BoilerRecipe> {
        public Serializer() {}

        public static final Serializer INSTANCE = new Serializer();

        @Override
        public BoilerRecipe read(Identifier id, JsonObject json) {
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json,"output"));
            JsonArray ingredients = JsonHelper.getArray(json,"ingredients");
            JsonArray fuelInput = JsonHelper.getArray(json, "fuel");

            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(3,Ingredient.EMPTY);
            Ingredient fuel = Ingredient.fromJson(fuelInput);

            for(int i=0;i<inputs.size();i++){
                inputs.set(i,Ingredient.fromJson(ingredients.get(i)));
            }

            return new BoilerRecipe(id, inputs, output,fuel);
        }

        @Override
        public BoilerRecipe read(Identifier id, PacketByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(),Ingredient.EMPTY);

            inputs.replaceAll(ignored -> Ingredient.fromPacket(buf));
            Ingredient fuel = Ingredient.fromPacket(buf);

            ItemStack output = buf.readItemStack();
            return new BoilerRecipe(id, inputs, output,fuel);
        }

        @Override
        public void write(PacketByteBuf buf, BoilerRecipe recipe) {
            buf.writeInt(recipe.getInputs().size());
            for(Ingredient ingredient : recipe.getInputs()){
                ingredient.write(buf);
            }
            buf.writeItemStack(recipe.output);
        }
    }
}
