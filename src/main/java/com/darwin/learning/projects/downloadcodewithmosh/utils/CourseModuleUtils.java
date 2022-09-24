package com.darwin.learning.projects.downloadcodewithmosh.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CourseModuleUtils {
    public static String getSanitizedModuleName(String name) {
        if (name.contains("(")) {
            return name.substring(0, name.indexOf('(') - 1);
        } else if (name.contains("Available in days")) {
            return name.substring(0, name.indexOf("Available in days"));
        }
        return name;
    }
}
