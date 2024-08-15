package com.morningmomo.moneycraft.entities.blockentities;

import com.morningmomo.moneycraft.init.ModBlockEntities;
import com.morningmomo.moneycraft.init.ModItems;
import com.morningmomo.moneycraft.screen.StripperScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
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

public class StripperBlockEntity extends BlockEntity implements ImplementedInventory, ExtendedScreenHandlerFactory {
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
        return inventory;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf){
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName(){
        return Text.literal("Stripper");
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
        nbt.putInt("progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt){
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("progress");
    }

    public void tick(World world1, BlockPos pos, BlockState state1){
        if (world1.isClient){
            return;
        }
        if (isOutputSlotAvailable()){
            if (this.hasRecipe()){
                this.increaseCraftProgress();
                markDirty(world1, pos ,state1);

                if (hasCraftingFinished()){
                    this.craftItem();
                    this.resetProgress();
                }
            }else{
                this.resetProgress();
            }
        }else{
            this.resetProgress();
            markDirty(world1, pos, state1);
        }
    }

    private boolean isOutputSlotAvailable(){
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getCount() < this.getStack(OUTPUT_SLOT).getMaxCount();
    }

    private boolean hasRecipe(){
        ItemStack result = new ItemStack(ModItems.FLAX_FIBER);
        boolean hasInput = getStack(INPUT_SLOT).getItem() == ModItems.FLAX;

        return hasInput && canInsertAmountIntoOutputSlot(result) && canInsertItemIntoOutputSlot(result.getItem());
    }
    
    private boolean canInsertAmountIntoOutputSlot(ItemStack result){
        return this.getStack(OUTPUT_SLOT).getCount() + result.getCount() <= this.getStack(OUTPUT_SLOT).getMaxCount();
    }

    private boolean canInsertItemIntoOutputSlot(Item item){
        return this.getStack(OUTPUT_SLOT).getItem() == item || this.getStack(OUTPUT_SLOT).isEmpty();
    }

    private void increaseCraftProgress(){
        progress++;
    }

    private boolean hasCraftingFinished(){
        return progress >= maxProgress;
    }

    private void craftItem(){
        this.removeStack(INPUT_SLOT, 1);
        ItemStack result = new ItemStack(ModItems.FLAX_FIBER);

        this.setStack(OUTPUT_SLOT, new ItemStack(result.getItem(), getStack(OUTPUT_SLOT).getCount() + result.getCount()));
    }

    private void resetProgress(){
        this.progress = 0;
    }

}