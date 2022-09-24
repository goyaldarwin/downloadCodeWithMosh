package com.darwin.learning.projects.downloadcodewithmosh.services.impl;

import com.darwin.learning.projects.downloadcodewithmosh.models.Course;
import com.darwin.learning.projects.downloadcodewithmosh.services.CourseContentProcessor;
import com.darwin.learning.projects.downloadcodewithmosh.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CourseContentSaver implements CourseContentProcessor {
    @Override
    public void process(Course course) {
        preProcess(course, log);
        FileUtils.saveCourseToAFile(course);
        postProcess(course, log);
    }
}
