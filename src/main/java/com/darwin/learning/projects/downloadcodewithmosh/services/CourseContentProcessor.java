package com.darwin.learning.projects.downloadcodewithmosh.services;

import com.darwin.learning.projects.downloadcodewithmosh.models.Course;
import org.slf4j.Logger;

public interface CourseContentProcessor {
    default void preProcess(Course course, Logger logger) {
        logger.debug("Processing course: {}", course.getName());
    }

    void process(Course course);

    default void postProcess(Course course, Logger logger) {
        course.setIsProcessed(true);
        logger.debug("Downloaded course. Total duration: {} hours.", course.getDuration().toHours());
        logger.debug("Downloaded course. Total duration: {} minutes.", course.getDuration().toMinutes());
    }
}
