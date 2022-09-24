package com.darwin.learning.projects.downloadcodewithmosh.services.impl;

import com.darwin.learning.projects.downloadcodewithmosh.models.Course;
import com.darwin.learning.projects.downloadcodewithmosh.models.CourseModule;
import com.darwin.learning.projects.downloadcodewithmosh.models.Lecture;
import com.darwin.learning.projects.downloadcodewithmosh.services.CourseContentProcessor;
import com.darwin.learning.projects.downloadcodewithmosh.utils.DurationUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CourseContentPrinter implements CourseContentProcessor {
    private static final String DELIMITER = " || ";
    private static final String SPACE = "      ";

    @Override
    public void process(Course course) {
        preProcess(course, log);
        for (CourseModule module : course.getModules()) {
            String moduleDetails = module.getName() + DurationUtils.getMessage(module.getDuration());
            System.out.println(moduleDetails);
            for (Lecture lecture : module.getLectures()) {
                String lectureDetails = SPACE + lecture.getName() +
                        DELIMITER + lecture.getUrl() +
                        DELIMITER + lecture.getDownloadUrl() +
                        DELIMITER + DurationUtils.getMessage(lecture.getDuration());
                System.out.println(lectureDetails);
            }
        }
        postProcess(course, log);
    }
}
