package dev.zanckor.dimensionalinventory.init;

import java.util.HashMap;

public class DimensionGroup {
    public static HashMap<String, Integer> dimensionGroups = null;

    public static HashMap<String, Integer> turnConfigToHashMap() {
        HashMap<String, Integer> groups = new HashMap<>();

        DimensionConfig.DIMENSION_LIST.get().forEach(dimensionName -> {
            int groupIndex = dimensionName.indexOf('_');
            int groupNumber = Integer.parseInt(dimensionName.substring(0, groupIndex));

            groups.put(dimensionName.substring(groupIndex + 1), groupNumber);
        });

        return groups;
    }

    public static int getGroup(String dimensionName) {
        return dimensionGroups.get(dimensionName);
    }
}
