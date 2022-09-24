package com.darwin.learning.projects.downloadcodewithmosh;

import com.darwin.learning.projects.downloadcodewithmosh.models.Course;
import com.darwin.learning.projects.downloadcodewithmosh.models.DownloadPriority;
import com.darwin.learning.projects.downloadcodewithmosh.models.ProcessingStatus;
import com.darwin.learning.projects.downloadcodewithmosh.services.CourseContentProcessingTracker;
import com.darwin.learning.projects.downloadcodewithmosh.services.CourseContentProcessor;
import com.darwin.learning.projects.downloadcodewithmosh.services.CourseIndexGenerator;
import com.darwin.learning.projects.downloadcodewithmosh.services.CourseWebPageHtmlParser;
import com.darwin.learning.projects.downloadcodewithmosh.services.DescriptionGeneratorForYoutubeChapters;
import com.darwin.learning.projects.downloadcodewithmosh.services.impl.CourseContentDownloader;
import com.darwin.learning.projects.downloadcodewithmosh.services.impl.CourseContentProcessingTrackerImpl;
import com.darwin.learning.projects.downloadcodewithmosh.services.impl.CourseContentSaver;
import com.darwin.learning.projects.downloadcodewithmosh.services.impl.CourseWebPageHtmlParserImpl;
import com.darwin.learning.projects.downloadcodewithmosh.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DownloadCodeWithMoshApplication {

    private static CourseContentProcessor courseContentSaver;
    private static CourseContentProcessor courseContentDownloader;
    private static CourseWebPageHtmlParser courseWebPageHtmlParser;
    private static CourseContentProcessingTracker courseContentProcessingTracker;

    static {
        courseContentSaver = new CourseContentSaver();
        courseContentDownloader = new CourseContentDownloader();
        courseWebPageHtmlParser = new CourseWebPageHtmlParserImpl();
        courseContentProcessingTracker = new CourseContentProcessingTrackerImpl();
    }

    public static void main(String[] args) {
//        DownloadPriority downloadPriority = DownloadPriority.GOOD_TO_HAVE;
//        createJsonFileWithCompleteCourseDetails(downloadPriority);
//        downloadCoursesFromJsonFile(downloadPriority);
        generateIndexForAllTheDownloadedCourses();
    }

    private static void generateIndexForAllTheDownloadedCourses() {
        String filePath = "src/main/resources/all/Courses.json";
        try {
            List<Course> courses = FileUtils.readCoursesFromFile(filePath);
            for (Course course : courses) {
//                CourseIndexGenerator.generateIndex(course);
                DescriptionGeneratorForYoutubeChapters.generateDescription(course);
            }
        } catch (FileNotFoundException e) {
            log.error("File with path: {} does not exist", filePath);
        }
    }

    private static void downloadCoursesFromJsonFile(DownloadPriority downloadPriority) {
        try {
            List<Course> goodToHaveCourses = FileUtils.readCoursesFromFile(downloadPriority.getDirectory());
            for (Course course : goodToHaveCourses) {
                if (Boolean.FALSE.equals(course.getIsProcessed())) {
                    courseContentDownloader.process(course);

                }
            }
        } catch (FileNotFoundException e) {
            log.error("Exception occurred while processing.", e);
        }
    }

    private static void createJsonFileWithCompleteCourseDetails(DownloadPriority downloadPriority) {
        List<Course> coursesToBeProcessed = courseContentProcessingTracker.getCoursesToBeProcessed(downloadPriority);
        log.debug("Processing total of {} courses", coursesToBeProcessed.size());
        List<Course> successfullyProcessedCourses = new ArrayList<>();
        List<Course> erroredOutCourses = new ArrayList<>();
        List<Course> emptyCourses = new ArrayList<>();
        for (Course course : coursesToBeProcessed) {
            try {
                log.debug("Populating course details in course with name: {}", course.getName());
                courseWebPageHtmlParser.addAllModulesAndLectureDetails(course);
                if (course.getModules().isEmpty()) {
                    emptyCourses.add(course);
                } else {
                    courseContentSaver.process(course);
                    successfullyProcessedCourses.add(course);
                }
            } catch (Exception e) {
                erroredOutCourses.add(course);
                log.error("Exception occurred while processing course: {}", course.getName(), e);
            }
        }
        FileUtils.saveCourses(coursesToBeProcessed, ProcessingStatus.success, downloadPriority);
        FileUtils.saveCourses(erroredOutCourses, ProcessingStatus.failed, downloadPriority);
        FileUtils.saveCourses(emptyCourses, ProcessingStatus.empty, downloadPriority);
        courseContentProcessingTracker.updateStatusInTrackingFile(successfullyProcessedCourses);
    }
}
