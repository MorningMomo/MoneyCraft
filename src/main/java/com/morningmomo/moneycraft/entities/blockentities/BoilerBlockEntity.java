package com.morningmomo.moneycraft.entities.blockentities;

import com.morningmomo.moneycraft.init.ModBlockEntities;
import com.morningmomo.moneycraft.init.ModItems;
import com.morningmomo.moneycraft.recipes.BoilerRecipe;
import com.morningmomo.moneycraft.screens.BoilerScreenHandler;
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

public class BoilerBlockEntity extends BlockEntity implements ImplementedInventory, ExtendedScreenHandlerFactory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(5, ItemStack.EMPTY);

    private static final int INPUT_SLOT_0 = 0;
    private static final int INPUT_SLOT_1 = 1;
    private static final int INPUT_SLOT_2 = 2;
    private static final int FUEL_SLOT = 3;
    private static final int OUTPUT_SLOT = 4;

    protected final PropertyDelegate propertyDelegate;

    private int progress = 0;
    private int maxProgress = 60;
    private int burnTime = 0;
    private int maxBurnTime = 60;

    public BoilerBlockEntity(BlockPos pos, BlockState state){
        super(ModBlockEntities.BOILER, pos ,state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> BoilerBlockEntity.this.progress;
                    case 1 -> BoilerBlockEntity.this.maxProgress;
                    case 2 -> BoilerBlockEntity.this.burnTime;
                    case 3 -> BoilerBlockEntity.this.maxBurnTime;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0 -> BoilerBlockEntity.this.progress = value;
                    case 1 -> BoilerBlockEntity.this.maxProgress = value;
                    case 2 -> BoilerBlockEntity.this.burnTime = value;
                    case 3 -> BoilerBlockEntity.this.maxBurnTime = value;
                }
            }

            @Override
            public int size() {
                return 4;
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
        return Text.translatable("block.moneycraft.boiler");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player){
        return new BoilerScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    protected void writeNbt(NbtCompound nbt){
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("boiler.progress", progress);
        nbt.putInt("boiler.burnTime", burnTime);
        nbt.putInt("boiler.maxBurnTime", maxBurnTime);
    }

    @Override
    public void readNbt(NbtCompound nbt){
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("boiler.progress");
        burnTime = nbt.getInt("boiler.burnTime");
        maxBurnTime = nbt.getInt("boiler.maxBurnTime");
        super.readNbt(nbt);
    }

    public void tick(World world1, BlockPos pos, BlockState state1){
        if (world1.isClient){
            return;
        }

        if (isOutputSlotAvailable()){
            if (hasRecipe()){
                if (hasFuelRunOut()) {
                    decreaseCraftingProgress();
                }
                if (canStart()) {
                    if (canFuel()) {
                        increaseCraftingProgress();
                        decreaseFuel();
                        decreaseBurnTime();
                        if (this.burnTime == 0) {
                            setFullBurnTime();
                        }
                        markDirty(world1,pos,state1);
                    }else {
                        decreaseCraftingProgress();
                        decreaseBurnTime();
                    }
                }
                if (hasCraftingFinished()){
                    craftItem();
                    resetProgress();
                    decreaseBurnTime();
                }
            } else if (hasRecipe1()) {
                increaseCraftingProgress();
                decreaseBurnTime();
                if (hasCraftingFinished()) {
                    this.setStack(OUTPUT_SLOT, new ItemStack(ModItems.BANKNOTE_MATERIAL, this.getStack(OUTPUT_SLOT).getCount() + 1));
                    this.removeStack(INPUT_SLOT_0, 1);
                    this.removeStack(INPUT_SLOT_1, 8);
                    this.removeStack(INPUT_SLOT_2, 8);
                    resetProgress();
                }
                markDirty(world1, pos, state1);
            } else {
                decreaseCraftingProgress();
                decreaseBurnTime();
            }
        }else {
            decreaseCraftingProgress();
            decreaseBurnTime();
            markDirty(world1, pos, state1);
        }
    }

    private boolean isOutputSlotAvailable(){
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getCount() < this.getStack(OUTPUT_SLOT).getMaxCount();
    }

    private boolean canStart() {
        int count_0 = this.getStack(INPUT_SLOT_0).getCount();
        int count_1 = this.getStack(INPUT_SLOT_1).getCount();
        int count_2 = this.getStack(INPUT_SLOT_2).getCount();
        return count_0 - 1 >= 0 && count_1 - 8 >= 0 && count_2 - 8 >= 0;
    }

    private boolean canFuel() {
        int count_fuel = this.getStack(FUEL_SLOT).getCount();
        return count_fuel - 1 >= 0 || this.burnTime > 0 ;
    }

    private void decreaseFuel() {
        if (this.burnTime == 60) {
            this.removeStack(FUEL_SLOT, 1);
        }
    }

    private boolean hasRecipe(){
        Optional<BoilerRecipe> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) {
            return false;
        }
        ItemStack recipeOutput = recipe.get().getOutput(null);
        return canInsertAmountIntoOutputSlot(recipeOutput) && canInsertItemIntoOutputSlot(recipeOutput.getItem());
    }

    private boolean hasRecipe1() {
        return this.burnTime > 0
                && (getStack(INPUT_SLOT_0).isOf(ModItems.WOOD_DUST) && getStack(INPUT_SLOT_1).isOf(ModItems.FLAX_FIBER) && getStack(INPUT_SLOT_2).isOf(ModItems.COTTON_FIBER))
                && (getStack(INPUT_SLOT_0).getCount() - 1 >= 0 && getStack(INPUT_SLOT_1).getCount() - 8 >= 0 && getStack(INPUT_SLOT_2).getCount() - 8 >= 0);
    }

    private Optional<BoilerRecipe> getCurrentRecipe() {
        SimpleInventory inv = new SimpleInventory(this.size());
        for (int i = 0; i < inv.size(); i++) {
            inv.setStack(i, this.getStack(i));
        }
        return this.getWorld().getRecipeManager().getFirstMatch(BoilerRecipe.Type.INSTANCE, inv, this.getWorld());
    }
    
    private boolean canInsertAmountIntoOutputSlot(ItemStack result){
        return this.getStack(OUTPUT_SLOT).getCount() + result.getCount() <= this.getStack(OUTPUT_SLOT).getMaxCount();
    }

    private boolean canInsertItemIntoOutputSlot(Item item){
        return this.getStack(OUTPUT_SLOT).getItem() == item || this.getStack(OUTPUT_SLOT).isEmpty();
    }

    private void increaseCraftingProgress(){
        this.progress++;
    }

    private void decreaseCraftingProgress(){
        if (this.progress > 0) {
            this.progress--;
        }else {
            this.resetProgress();
        }
    }

    private void decreaseBurnTime() {
        if (this.burnTime > 0) {
            this.burnTime--;
        }else {
            this.resetBurnTime();
        }
    }

    private boolean hasCraftingFinished(){
        return progress >= maxProgress;
    }

    private boolean hasFuelRunOut() {
        return burnTime == 0 && getStack(FUEL_SLOT).isEmpty();
    }

    private void craftItem(){
        Optional<BoilerRecipe> recipe = getCurrentRecipe();

        ItemStack recipeOutput = recipe.get().getOutput(null);
        this.setStack(OUTPUT_SLOT, new ItemStack(recipeOutput.getItem(),
                this.getStack(OUTPUT_SLOT).getCount() + recipeOutput.getCount()));
        this.removeStack(INPUT_SLOT_0, 1);
        this.removeStack(INPUT_SLOT_1, 8);
        this.removeStack(INPUT_SLOT_2, 8);
    }

    private void resetProgress(){
        this.progress = 0;
    }

    private void resetBurnTime() {
        this.burnTime = 0;
    }

    private void setFullBurnTime() {
        this.burnTime = this.maxBurnTime;
    }
}