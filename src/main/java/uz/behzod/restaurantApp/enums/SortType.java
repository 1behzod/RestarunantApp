package uz.behzod.restaurantApp.enums;

import io.micrometer.common.util.StringUtils;

import java.io.Serializable;

public enum SortType implements Serializable {
    asc("asc"),
    desc("desc");

    private final String name;

    SortType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static SortType get(String name) {
        if (StringUtils.isNotEmpty(name)) {
            for (SortType sortType : values()) {
                if (name.equals(sortType.getName())) {
                    return sortType;
                }
            }
            return desc;
        }
        return desc;
    }

}
