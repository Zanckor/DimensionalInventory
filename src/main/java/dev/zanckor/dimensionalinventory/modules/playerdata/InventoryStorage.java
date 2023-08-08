package dev.zanckor.dimensionalinventory.modules.playerdata;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class InventoryStorage implements Capability.IStorage<InventoryCapability> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<InventoryCapability> capability, InventoryCapability instance, Direction side) {
        return instance.serializeNBT();
    }

    @Override
    public void readNBT(Capability<InventoryCapability> capability, InventoryCapability instance, Direction side, INBT nbt) {
        instance.deserializeNBT((CompoundNBT) nbt);
    }
}
