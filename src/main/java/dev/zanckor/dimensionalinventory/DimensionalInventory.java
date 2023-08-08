package dev.zanckor.dimensionalinventory;

import dev.zanckor.dimensionalinventory.init.DimensionConfig;
import dev.zanckor.dimensionalinventory.modules.playerdata.InventoryCapability;
import dev.zanckor.dimensionalinventory.modules.playerdata.InventoryStorage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static dev.zanckor.dimensionalinventory.DimensionalInventory.MOD_ID;

@Mod(MOD_ID)
public class DimensionalInventory {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "dimensionalinventory";

    public DimensionalInventory() {
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        LOGGER.debug("Registering config files " + MOD_ID);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, DimensionConfig.SPEC, "dimensional-inventory.toml");
    }

    @SubscribeEvent
    public void onCommonSetup(FMLCommonSetupEvent e) {
        CapabilityManager.INSTANCE.register(InventoryCapability.class, new InventoryStorage(), InventoryCapability::new);
    }
}
