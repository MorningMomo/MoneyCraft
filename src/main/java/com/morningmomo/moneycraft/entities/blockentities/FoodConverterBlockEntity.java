package com.morningmomo.moneycraft.entities.blockentities;

import com.morningmomo.moneycraft.init.ModBlockEntities;
import com.morningmomo.moneycraft.recipes.FoodConverterRecipe;
import com.morningmomo.moneycraft.screens.FoodConverterScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class FoodConverterBlockEntity extends BlockEntity implements ImplementedInventory, ExtendedScreenHandlerFactory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    protected final PropertyDelegate propertyDelegate;

    private int progress = 0;
    private int maxProgress = 36;

    public FoodConverterBlockEntity(BlockPos pos, BlockState state){
        super(ModBlockEntities.FOOD_CONVERTER, pos ,state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> FoodConverterBlockEntity.this.progress;
                    case 1 -> FoodConverterBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0 -> FoodConverterBlockEntity.this.progress = value;
                    case 1 -> FoodConverterBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems(){
        return this.inventory;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf){
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName(){
        return Text.translatable("block.moneycraft.food_converter");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player){
        return new FoodConverterScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    protected void writeNbt(NbtCompound nbt){
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("food_converter.progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt){
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("food_converter.progress");
        super.readNbt(nbt);
    }

    public void tick(World world1, BlockPos pos, BlockState state1){
        if (world1.isClient){
            return;
        }

        if (isOutputSlotAvailable()){
            if (this.hasRecipe()){
                this.increaseCraftingProgress();
                markDirty(world1,pos,state1);

                if (hasCraftingFinished()){
                    this.craftItem();
                    this.resetProgress();
                }
            }else {
                this.decreaseCraftingProgress();
            }
        }else {
            this.decreaseCraftingProgress();
            markDirty(world1, pos, state1);
        }
    }

    private boolean isOutputSlotAvailable(){
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getCount() < this.getStack(OUTPUT_SLOT).getMaxCount();
    }

    private boolean hasRecipe(){
        Optional<FoodConverterRecipe> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) {
            return false;
        }
        ItemStack recipeOutput = recipe.get().getOutput(null);
        return canInsertAmountIntoOutputSlot(recipeOutput) && canInsertItemIntoOutputSlot(recipeOutput.getItem());
    }

    private Optional<FoodConverterRecipe> getCurrentRecipe() {
        SimpleInventory inv = new SimpleInventory(this.size());
        for (int i = 0; i < inv.size(); i++) {
            inv.setStack(i, this.getStack(i));
        }
        return this.getWorld().getRecipeManager().getFirstMatch(FoodConverterRecipe.Type.INSTANCE, inv, this.getWorld());
    }
    
    private boolean canInsertAmountIntoOutputSlot(ItemStack result){
        return this.getStack(OUTPUT_SLOT).getCount() + result.getCount() <= this.getStack(OUTPUT_SLOT).getMaxCount();
    }

    private boolean canInsertItemIntoOutputSlot(Item item){
        return this.getStack(OUTPUT_SLOT).getItem() == item || this.getStack(OUTPUT_SLOT).isEmpty();
    }

    private void increaseCraftingProgress(){
        progress++;
    }

    private void decreaseCraftingProgress(){
        if (progress >= 0) {
            progress--;
        }else {
            this.resetProgress();
        }
    }

    private boolean hasCraftingFinished(){
        return progress >= maxProgress;
    }

    private void craftItem(){
        Optional<FoodConverterRecipe> recipe = getCurrentRecipe();

        ItemStack recipeOutput = recipe.get().getOutput(null);
        this.setStack(OUTPUT_SLOT, new ItemStack(recipeOutput.getItem(),
                this.getStack(OUTPUT_SLOT).getCount() + recipeOutput.getCount()));
        this.removeStack(INPUT_SLOT, 1);
    }

    private void resetProgress(){
        this.progress = 0;
    }
}