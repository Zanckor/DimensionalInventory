package dev.zanckor.dimensionalinventory.modules.playerdata;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.ItemStackHandler;

import java.util.concurrent.ConcurrentHashMap;

public class InventoryCapability implements INBTSerializable<CompoundNBT> {
    private ConcurrentHashMap<String, ItemStackHandler> inventoryHash = new ConcurrentHashMap<>();

    public ItemStackHandler getInventory(String name) {
        return inventoryHash.get(name);
    }

    public void setInventory(String name, ItemStackHandler inventory) {
        inventoryHash.put(name, inventory);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();

        //For each inventoryHash's key, save the data synced with the dimension name.
        inventoryHash.forEach((name, itemStackHandler) -> {
            if (inventoryHash.containsKey(name)) {
                //Save the inventory
                nbt.put("inventory_" + name, itemStackHandler.serializeNBT());
            }
        });

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        nbt.keySet().forEach(name -> {
            if (name.contains("inventory_")) {
                //Obtain the handler from CompoundNBT
                ItemStackHandler itemStackHandler = new ItemStackHandler();
                itemStackHandler.deserializeNBT(nbt.getCompound(name));

                //Deserialize based on name and handler deserialized
                inventoryHash.put(name.substring(10), itemStackHandler);
            }
        });
    }

    public void copyForRespawn(InventoryCapability oldStore) {
        inventoryHash = oldStore.inventoryHash;
    }
}
