package dev.zanckor.dimensionalinventory.init;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.List;

public class DimensionConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.ConfigValue<List<String>> DIMENSION_LIST;

    static {
        BUILDER.push("Dimensional Inventory configuration");

        DIMENSION_LIST = BUILDER.comment("A list of dimensions that will have a different inventory.")
                .comment("Needs to be: modid:dimension_name. Group is defined setting the group index before the first _")
                .define("Dimension Group", Arrays.asList("0_minecraft:overworld", "0_minecraft:the_nether", "1_minecraft:the_end"));

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
