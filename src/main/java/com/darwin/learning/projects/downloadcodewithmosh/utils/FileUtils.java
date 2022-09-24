package com.darwin.learning.projects.downloadcodewithmosh.utils;

import com.darwin.learning.projects.downloadcodewithmosh.models.Course;
import com.darwin.learning.projects.downloadcodewithmosh.models.DownloadPriority;
import com.darwin.learning.projects.downloadcodewithmosh.models.ProcessingStatus;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@UtilityClass
public class FileUtils {

    private static final Type COURSE_TYPE = new TypeToken<ArrayList<Course>>() {
    }.getType();
    private static final Gson gson = new Gson();

    public void saveCourses(List<Course> courses, ProcessingStatus processingStatus, DownloadPriority downloadPriority) {
        saveCourses(downloadPriority.getFileName(processingStatus), courses);
    }

    public void saveCourses(String filePath, List<Course> courses) {
        // save all the courses to a file
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            fileOutputStream.write(gson.toJson(courses).getBytes());
        } catch (Exception e) {
            log.error("Exception occurred while saving the courses to a file", e);
        }
    }

    public void saveCourseToAFile(Course course) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(course.getDownloadPriority().getFileName(course))) {
            fileOutputStream.write(gson.toJson(course).getBytes());
        } catch (Exception e) {
            log.error("Exception occurred while saving the courses to a file", e);
        }
    }

    public static List<Course> readCoursesFromFile(String filName) throws FileNotFoundException {
        return gson.fromJson(new FileReader(filName), COURSE_TYPE);
    }
}
