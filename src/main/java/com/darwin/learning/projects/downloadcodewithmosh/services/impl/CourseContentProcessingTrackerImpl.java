package com.darwin.learning.projects.downloadcodewithmosh.services.impl;

import com.darwin.learning.projects.downloadcodewithmosh.models.Course;
import com.darwin.learning.projects.downloadcodewithmosh.models.DownloadPriority;
import com.darwin.learning.projects.downloadcodewithmosh.services.CourseContentProcessingTracker;
import com.darwin.learning.projects.downloadcodewithmosh.utils.CourseUtils;
import com.darwin.learning.projects.downloadcodewithmosh.utils.FileUtils;
import com.darwin.learning.projects.downloadcodewithmosh.utils.HtmlUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.darwin.learning.projects.downloadcodewithmosh.constants.Constants.URL_ALL_COURSES;

@Slf4j
public class CourseContentProcessingTrackerImpl implements CourseContentProcessingTracker {

    private static final String TRACKING_FILE_NAME = "src/main/resources/Courses_Tracker.json";

    @Override
    public void saveCoursesWithoutModulesInTrackingFile() {
        Document allCoursesPageHtml = HtmlUtils.getHtmlFromUrl(URL_ALL_COURSES);
        Elements courseElements = allCoursesPageHtml.getElementsByClass("course-listing");
        List<Course> courses = new ArrayList<>();
        double courseNumber = 1d;
        for (Element element : courseElements) {
            Course course = CourseUtils.getCourseDetails(element);
            course.setNumber(courseNumber++);
            courses.add(course);
        }
        FileUtils.saveCourses(TRACKING_FILE_NAME, courses);
    }

    @Override
    public List<Course> getCoursesToBeProcessed(DownloadPriority downloadPriority) {
        saveCoursesWithoutModulesInTrackingFile();
        return getCoursesFromTrackingFile().stream()
                .filter(course -> course.getDownloadPriority().equals(downloadPriority) && !course.getIsProcessed())
                .collect(Collectors.toList());
    }

    @Override
    public void updateStatusInTrackingFile(List<Course> successfulCourses) {
        List<Course> coursesFromTrackingFile = getCoursesFromTrackingFile();
        for (int i = 0; i < coursesFromTrackingFile.size(); i++) {
            if (successfulCourses.contains(coursesFromTrackingFile.get(i))) {
                coursesFromTrackingFile.get(i).setIsProcessed(true);
            }
        }
        FileUtils.saveCourses(TRACKING_FILE_NAME, coursesFromTrackingFile);
    }

//    private List<Course> downloadSingleCourse() {
//        Course course = new Course();
//        course.setName("Mastering React");
//        course.setUrl("https://codewithmosh.com/courses/enrolled/357787");
////        course.setName("The Ultimate Docker Course");
////        course.setUrl("https://codewithmosh.com/courses/enrolled/1359863");
//
//        List<Course> courses = new ArrayList<>();
//        courses.add(course);
//        return courses;
//    }

    private List<Course> getCoursesFromTrackingFile() {
        try {
            return FileUtils.readCoursesFromFile(TRACKING_FILE_NAME);
        } catch (FileNotFoundException e) {
            log.error("Could not read the file with name: {}", TRACKING_FILE_NAME);
        }
        return new ArrayList<>();
    }
}
