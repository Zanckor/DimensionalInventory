package dev.zanckor.dimensionalinventory.common.util;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class InventoryUtil {

    public static ItemStackHandler getInventoryItems(PlayerEntity player) {
        ItemStackHandler itemHandler = new ItemStackHandler(41);

        //Inventory slots
        for (int slot = 0; slot < player.inventory.getSizeInventory(); slot++) {
            ItemStack itemStack = player.inventory.getStackInSlot(slot);
            itemHandler.setStackInSlot(slot, itemStack.copy());
        }

        //Armor slots
        for (int armorSlot = 0; armorSlot < 4; armorSlot++) {
            ItemStack armorStack = player.inventory.armorInventory.get(armorSlot);
            itemHandler.setStackInSlot(armorSlot + 36, armorStack.copy());
        }

        //Offhand
        ItemStack offhandStack = player.inventory.offHandInventory.get(0);
        itemHandler.setStackInSlot(40, offhandStack.copy());

        return itemHandler;
    }

    public static void replaceInventory(PlayerEntity player, ItemStackHandler newStackHandler){
        if(newStackHandler == null) newStackHandler = new ItemStackHandler(41);

        //Inventory slots
        for (int slot = 0; slot < player.inventory.getSizeInventory(); slot++) {
            ItemStack itemStack = newStackHandler.getStackInSlot(slot);
            player.replaceItemInInventory(slot, itemStack);
        }

        //Armor slots
        for (int armorSlot = 0; armorSlot < 4; armorSlot++) {
            ItemStack armorStack = newStackHandler.getStackInSlot(armorSlot + 36);
            player.inventory.armorInventory.set(armorSlot, armorStack);
        }

        //Offhand
        ItemStack offHandStack = newStackHandler.getStackInSlot(40);
        player.inventory.offHandInventory.set(0, offHandStack);
    }
}
