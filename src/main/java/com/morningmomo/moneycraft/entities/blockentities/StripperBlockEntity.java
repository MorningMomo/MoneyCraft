package com.morningmomo.moneycraft.entities.blockentities;

import com.morningmomo.moneycraft.MoneyCraft;
import com.morningmomo.moneycraft.init.ModBlockEntities;
import com.morningmomo.moneycraft.recipes.StripperRecipe;
import com.morningmomo.moneycraft.screens.StripperScreenHandler;
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
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class StripperBlockEntity extends BlockEntity implements ImplementedInventory, ExtendedScreenHandlerFactory {
    private static BooleanProperty stripperState;
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    protected final PropertyDelegate propertyDelegate;

    private int progress = 0;
    private int maxProgress = 36;

    public StripperBlockEntity(BlockPos pos, BlockState state){
        super(ModBlockEntities.STRIPPER, pos ,state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> StripperBlockEntity.this.progress;
                    case 1 -> StripperBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0 -> StripperBlockEntity.this.progress = value;
                    case 1 -> StripperBlockEntity.this.maxProgress = value;
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
        return Text.translatable("block.moneycraft.stripper");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player){
        return new StripperScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    protected void writeNbt(NbtCompound nbt){
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("stripper.progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt){
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("stripper.progress");
        super.readNbt(nbt);
    }

    public void tick(World world1, BlockPos pos, BlockState state1){
        BlockState previousState = world1.getBlockState(pos);
        if (world1.isClient){
            return;
        }
        
        /*if (world1.getBlockState(pos) != previousState && !inventory.get(0).isEmpty() && isCrafting()) {
            world1.setBlockState(pos, state1.with(stripperState, true));
        }

        if (world1.getBlockState(pos) == previousState && !hasRecipe() && progress != 0) {
            world1.getBlockState(pos);
        }*/


        if (isOutputSlotAvailable()){
            //MoneyCraft.LOGGER.info("0");
            if (this.hasRecipe()){
                //MoneyCraft.LOGGER.info("1");
                this.increaseCraftingProgress();
                markDirty(world1,pos,state1);

                if (hasCraftingFinished()){
                    //MoneyCraft.LOGGER.info("2");
                    this.craftItem();
                    this.resetProgress();
                }
            }else {
                //MoneyCraft.LOGGER.info("3");
                this.resetProgress();
            }
        }else {
            //MoneyCraft.LOGGER.info("4");
            this.resetProgress();
            markDirty(world1, pos, state1);
        }
    }

    private boolean isOutputSlotAvailable(){
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getCount() < this.getStack(OUTPUT_SLOT).getMaxCount();
    }

    private boolean hasRecipe(){
        Optional<StripperRecipe> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) {
            return false;
        }
        ItemStack recipeOutput = recipe.get().getOutput(null);
        return canInsertAmountIntoOutputSlot(recipeOutput) && canInsertItemIntoOutputSlot(recipeOutput.getItem());
    }

    private Optional<StripperRecipe> getCurrentRecipe() {
        SimpleInventory inv = new SimpleInventory(this.size());
        for (int i = 0; i < inv.size(); i++) {
            inv.setStack(i, this.getStack(i));
        }
        return this.getWorld().getRecipeManager().getFirstMatch(StripperRecipe.Type.INSTANCE, inv, this.getWorld());
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
        progress--;
    }

    private boolean hasCraftingFinished(){
        return progress >= maxProgress;
    }

    private void craftItem(){
        Optional<StripperRecipe> recipe = getCurrentRecipe();

        ItemStack recipeOutput = recipe.get().getOutput(null);
        this.setStack(OUTPUT_SLOT, new ItemStack(recipeOutput.getItem(),
                this.getStack(OUTPUT_SLOT).getCount() + recipeOutput.getCount()));
        this.removeStack(INPUT_SLOT, 1);
    }

    private void resetProgress(){
        this.progress = 0;
    }

    private boolean isCrafting() {
        return hasRecipe() && isOutputSlotAvailable();
    }
}
