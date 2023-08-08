package dev.zanckor.dimensionalinventory.modules.playerdata;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;

public class InventoryProvider implements ICapabilityProvider, INBTSerializable<CompoundNBT> {
    @CapabilityInject(InventoryCapability.class)
    public static final Capability<InventoryCapability> INVENTORY_CAPABILITY = null;
    private InventoryCapability inventoryHandler = null;
    private final LazyOptional<InventoryCapability> optional = LazyOptional.of(this::createData);

    private InventoryCapability createData() {
        if (inventoryHandler == null) {
            inventoryHandler = new InventoryCapability();
        }

        return inventoryHandler;
    }



    public static InventoryCapability getPlayer(PlayerEntity player) {
        final InventoryCapability capability = player.getCapability(INVENTORY_CAPABILITY, null).orElse(null);

        return capability;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == INVENTORY_CAPABILITY) {
            createData();
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        return createData().serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT compoundTag) {
        createData().deserializeNBT(compoundTag);
    }
}
