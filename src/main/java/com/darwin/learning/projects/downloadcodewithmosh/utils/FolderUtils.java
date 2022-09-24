package com.darwin.learning.projects.downloadcodewithmosh.utils;

import com.darwin.learning.projects.downloadcodewithmosh.models.Course;
import com.darwin.learning.projects.downloadcodewithmosh.models.CourseModule;
import lombok.experimental.UtilityClass;

import java.io.File;

@UtilityClass
public class FolderUtils {

    // https://stackoverflow.com/a/3634906
    public static void createAFolder(String path) {
        File theDir = new File(path);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
    }


    public static String appendCourseAndModuleFolders(String basePath, Course course, CourseModule module) {
        return basePath +
                course.getName() +
                DurationUtils.getDurationString(course.getDuration()) +
                module.getName() +
                DurationUtils.getDurationString(module.getDuration());
    }
}
