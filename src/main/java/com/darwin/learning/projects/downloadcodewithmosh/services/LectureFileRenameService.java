package com.darwin.learning.projects.downloadcodewithmosh.services;

import java.io.File;
import java.util.Arrays;

public class LectureFileRenameService {
    private static final String COURSE_PATH = "/Users/darwin/Desktop/Downloads/React1";

    public static void main(String[] args) {
        File courseFile = new File(COURSE_PATH);
        String[] directories = courseFile.list((current, name) -> new File(current, name).isDirectory());
        System.out.println(Arrays.toString(directories));
        for (String directory : directories) {
            File moduleFile = new File(COURSE_PATH + "/" + directory);
            for (String lectureFileName : moduleFile.list()) {
                String newLectureFileName = COURSE_PATH + "/" + directory.replace("React | ", "") + " | " + lectureFileName;
                File newLectureFile = new File(newLectureFileName);
                File currentLectureFile = new File(COURSE_PATH + "/" + directory + "/" + lectureFileName);

                System.out.println("CurrentLectureFile: " + currentLectureFile.getName() + " " + currentLectureFile.renameTo(newLectureFile));
            }
        }
    }
}
