package dev.zanckor.dimensionalinventory.init;

import net.minecraftforge.common.ForgeConfigSpec;

public class DimensionConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.ConfigValue<String> DIMENSION_LIST;

    static {
        BUILDER.comment("A list of dimensions that will have a different inventory.").push("Dimensional Inventory Configuration");

        DIMENSION_LIST = BUILDER.comment("Needs to be: modid:dimension_name. Group is defined setting the group index after the equal =")
                .define("Dimension Group", "minecraft:overworld=0, minecraft:the_nether=0, minecraft:the_end=1");

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
