package com.morningmomo.moneycraft.recipes;

import com.google.gson.JsonObject;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;

public abstract class MachineRecipe implements Recipe<SimpleInventory> {
    /*protected final DefaultedList<Ingredient> inputs;
    protected final ItemStack output;
    protected final RecipeType<?> type;
    protected final RecipeSerializer<?> serializer;
    protected final Identifier id;

    public MachineRecipe(RecipeType<?> type, RecipeSerializer<?> serializer, Identifier id, DefaultedList<Ingredient> inputs, ItemStack output) {
        this.inputs = inputs;
        this.output = output;
        this.type = type;
        this.serializer = serializer;
        this.id = id;
    }

    @Override
    public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        return this.output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return this.output;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();
        defaultedList.add(this.inputs);
        return defaultedList;
    }


    @Override
    public ItemStack createIcon() {
        return Recipe.super.createIcon();
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return this.serializer;
    }

    @Override
    public RecipeType<?> getType() {
        return this.type;
    }

    public static class Serializer<T extends MachineRecipe> implements RecipeSerializer<T> {
        final RecipeFactory<T> recipeFactory;

        public Serializer(RecipeFactory<T> recipeFactory) {
            this.recipeFactory = recipeFactory;
        }

        @Override
        public T read(Identifier id, JsonObject json) {
            String string1 = JsonHelper.getString(json, "group", "");
            Ingredient ingredient;
            if (JsonHelper.hasArray(json, "ingredient")) {
                ingredient = Ingredient.fromJson(JsonHelper.getArray(json, "ingredient"), false);
            } else {
                ingredient = Ingredient.fromJson(JsonHelper.getObject(json, "ingredient"), false);
            }
            String string2 = JsonHelper.getString(json, "result", "");
            int i = JsonHelper.getInt(json, "count");
            ItemStack itemStack = new ItemStack(Registries.ITEM.get(new Identifier(string2)), i);
            return this.recipeFactory.create(id, string1, ingredient, itemStack);
        }

        @Override
        public T read(Identifier id, PacketByteBuf buf) {
            String string = buf.readString();
            Ingredient ingredient = Ingredient.fromPacket(buf);
            ItemStack itemStack = buf.readItemStack();
            return this.recipeFactory.create(id, string, ingredient, itemStack);
        }

        @Override
        public void write(PacketByteBuf buf, T recipe) {
            recipe.input.write(buf);
            buf.writeItemStack(recipe.output);
        }
        public interface RecipeFactory<T extends MachineRecipe> {
            T create(Identifier id, String group, Ingredient input, ItemStack output);
        }
    }*/
}

