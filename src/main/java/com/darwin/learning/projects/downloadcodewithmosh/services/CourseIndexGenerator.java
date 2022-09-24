package com.darwin.learning.projects.downloadcodewithmosh.services;

import com.darwin.learning.projects.downloadcodewithmosh.models.Course;
import com.darwin.learning.projects.downloadcodewithmosh.models.CourseModule;
import com.darwin.learning.projects.downloadcodewithmosh.models.Lecture;
import com.darwin.learning.projects.downloadcodewithmosh.utils.DurationUtils;
import com.darwin.learning.projects.downloadcodewithmosh.utils.FolderUtils;

public class CourseIndexGenerator {
    private static final String INDEX_FOLDER_PATH = "/Users/darwin/Desktop/Index/";


    public static void generateIndex(Course course) {
        for (CourseModule module : course.getModules()) {
            String directoryWithCourseAndModule = FolderUtils.appendCourseAndModuleFolders(INDEX_FOLDER_PATH, course, module);
            FolderUtils.createAFolder(directoryWithCourseAndModule);
            for (Lecture lecture : module.getLectures()) {
                createLectureFileInIndex(directoryWithCourseAndModule, lecture);
            }
        }
    }


    private static void createLectureFileInIndex(String directoryWithCourseAndModule, Lecture lecture) {
        String pathWithLectureFolder = directoryWithCourseAndModule +
                "/" +
                lecture.getName() +
                DurationUtils.getDurationString(lecture.getDuration());
        FolderUtils.createAFolder(pathWithLectureFolder);
    }
}
