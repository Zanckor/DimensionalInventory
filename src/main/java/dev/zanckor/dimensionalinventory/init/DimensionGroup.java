package dev.zanckor.dimensionalinventory.init;

import java.util.HashMap;

public class DimensionGroup {
    public static HashMap<String, Integer> dimensionGroups = null;

    public static HashMap<String, Integer> turnConfigToHashMap() {
        HashMap<String, Integer> groups = new HashMap<>();

        //Split the string into groups
        for (String groupStrings : DimensionConfig.DIMENSION_LIST.get().split(", ")) {
            String[] parts = groupStrings.split("=");
            int groupNumber = Integer.parseInt(parts[1]);
            String dimensionName = parts[0];

            groups.put(dimensionName, groupNumber);
        }

        System.out.println(groups);

        return groups;
    }

    public static int getGroup(String dimensionName) {
        return dimensionGroups.get(dimensionName);
    }
}
