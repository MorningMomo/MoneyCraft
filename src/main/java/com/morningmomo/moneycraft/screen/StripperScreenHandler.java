package com.morningmomo.moneycraft.screen;

import com.morningmomo.moneycraft.entities.blockentities.StripperBlockEntity;
import com.morningmomo.moneycraft.init.ModScreenHandlers;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class StripperScreenHandler extends ScreenHandler {
   private final Inventory inventory;
   private final PropertyDelegate propertyDelegate;
   public final StripperBlockEntity blockEntity;
   public StripperScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf){
       this(syncId, inventory, inventory.player.getWorld().getBlockEntity(buf.readBlockPos()),
               new ArrayPropertyDelegate(2));
   }

   public StripperScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity, PropertyDelegate arrayPropertyDelegate){
       super(ModScreenHandlers.STRIPPER_SCREEN_HANDLER, syncId);
       checkSize((Inventory) blockEntity, 2);
       this.inventory = (Inventory) blockEntity;
       inventory.onOpen(playerInventory.player);
       this.propertyDelegate = arrayPropertyDelegate;
       this.blockEntity = (StripperBlockEntity) blockEntity;

       this.addSlot(new Slot(inventory, 0, 80, 11));
       this.addSlot(new Slot(inventory, 1, 80, 59));

       addPlayerInventory(playerInventory);
       addPlayerHotbar(playerInventory);

       addProperties(arrayPropertyDelegate);
   }

    private void addPlayerInventory(PlayerInventory playerInventory) {
       for (int i = 0; i < 3; ++i){
           for (int j = 0; j < 9; ++j){
               this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
           }
       }
    }

    private  void addPlayerHotbar(PlayerInventory playerInventory){
       for (int i = 0; i < 9; ++i){
           this.addSlot(new Slot(playerInventory, i, 8 + i * 18,142));
       }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int inventorySlot) {
       ItemStack newStack = ItemStack.EMPTY;
       Slot slot = this.slots.get(inventorySlot);

       if (slot != null && slot.hasStack()){
           ItemStack originalStack = slot.getStack();
           newStack = originalStack.copy();
           
           if (inventorySlot < this.inventory.size()){
               if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)){
                   return ItemStack.EMPTY;
               }
           }else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)){
               return ItemStack.EMPTY;
           }

           if (originalStack.isEmpty()){
               slot.setStack(ItemStack.EMPTY);
           }else{
               slot.markDirty();
           }
       }
       return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player){
       return this.inventory.canPlayerUse(player);
    }

    public boolean isCrafting(){
       return propertyDelegate.get(0) > 0;
    }

    public int getScaledProgress(){
       int progress = this.propertyDelegate.get(0);
       int maxProgress = this.propertyDelegate.get(1);
       int progressArrowSize = 26;

       return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

}
