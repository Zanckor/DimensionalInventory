package dev.zanckor.dimensionalinventory.common.event;

import dev.zanckor.dimensionalinventory.common.util.InventoryUtil;
import dev.zanckor.dimensionalinventory.init.DimensionGroup;
import dev.zanckor.dimensionalinventory.modules.playerdata.InventoryCapability;
import dev.zanckor.dimensionalinventory.modules.playerdata.InventoryProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemStackHandler;

import static dev.zanckor.dimensionalinventory.DimensionalInventory.MOD_ID;
import static dev.zanckor.dimensionalinventory.init.DimensionGroup.getGroup;
import static dev.zanckor.dimensionalinventory.init.DimensionGroup.turnConfigToHashMap;
import static dev.zanckor.dimensionalinventory.modules.playerdata.InventoryProvider.INVENTORY_CAPABILITY;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {
    @SubscribeEvent
    public static void replaceInventoryOnChangeDimension(PlayerEvent.PlayerChangedDimensionEvent e) {
        final PlayerEntity PLAYER = e.getPlayer();
        final String PREV_DIMENSION = e.getFrom().getLocation().toString();
        final String CURRENT_DIMENSION = e.getTo().getLocation().toString();


        final InventoryCapability INVENTORY = InventoryProvider.getPlayer(PLAYER);
        final ItemStackHandler PREV_INVENTORY_HANDLER = InventoryUtil.getInventoryItems(PLAYER);
        final ItemStackHandler NEW_INVENTORY_HANDLER = INVENTORY.getInventory(CURRENT_DIMENSION);

        //Save previous dimension inventory
        INVENTORY.setInventory(PREV_DIMENSION, PREV_INVENTORY_HANDLER);

        //Replace inventory only if the group is different
        if (getGroup(CURRENT_DIMENSION) != getGroup(PREV_DIMENSION)) {

            //Replace the items from previous inventory to current dimension inventory
            InventoryUtil.replaceInventory(PLAYER, NEW_INVENTORY_HANDLER);
        }
    }

    @SubscribeEvent
    public static void replaceInventoryOnDieOnDifferentDimension(PlayerEvent.Clone event) {
        final PlayerEntity ORIGINAL = event.getOriginal();
        final PlayerEntity PLAYER = event.getPlayer();
        final String PREV_DIMENSION = ORIGINAL.getEntityWorld().getDimensionKey().getLocation().toString();
        final String CURRENT_DIMENSION = PLAYER.getEntityWorld().getDimensionKey().getLocation().toString();
        final InventoryCapability INVENTORY_CAPABILITY = InventoryProvider.getPlayer(PLAYER);

        copyCapabilityOnDie(event);

        //Replace inventory only if the group is different
        if (getGroup(CURRENT_DIMENSION) != getGroup(PREV_DIMENSION)) {
            removeInventory(INVENTORY_CAPABILITY, PREV_DIMENSION);
            replaceInventoryOnDie(PLAYER, INVENTORY_CAPABILITY, PREV_DIMENSION, CURRENT_DIMENSION);
        }
    }

    private static void replaceInventoryOnDie(PlayerEntity player, InventoryCapability inventoryCapability, String prevDimension, String currentDimension) {
        //If it has spawned in a different dimension, replace inventory
        if (!prevDimension.equalsIgnoreCase(currentDimension)) {
            final ItemStackHandler NEW_INVENTORY_HANDLER = inventoryCapability.getInventory(currentDimension);

            //Replace the items to current dimension inventory
            InventoryUtil.replaceInventory(player, NEW_INVENTORY_HANDLER);
        }
    }

    static void removeInventory(InventoryCapability inventoryCapability, String prevDimension) {
        //Remove the inventory on all dimensions of the same group
        int groupNumber = getGroup(prevDimension);

        DimensionGroup.dimensionGroups.forEach((dimensionName, group) -> {
            if (group == groupNumber) {
                inventoryCapability.setInventory(prevDimension, new ItemStackHandler(41));
            }
        });
    }

    static void copyCapabilityOnDie(PlayerEvent.Clone e) {
        Entity player = e.getPlayer();

        //Copy capabilities from previous player (Died) and paste on new player
        if (e.isWasDeath()) {
            e.getOriginal().revive();
            e.getOriginal().getCapability(INVENTORY_CAPABILITY).ifPresent(oldStore -> player.getCapability(INVENTORY_CAPABILITY).ifPresent(newStore -> newStore.copyForRespawn(oldStore)));
        }
    }

    @SubscribeEvent
    public static void addCapabilityPlayer(AttachCapabilitiesEvent<Entity> e) {
        InventoryProvider capability = new InventoryProvider();

        e.addCapability(new ResourceLocation(MOD_ID, "inventory_capability"), capability);
    }


    @SubscribeEvent
    public static void setGroups(WorldEvent.Load e) {
        DimensionGroup.dimensionGroups = turnConfigToHashMap();
    }
}
