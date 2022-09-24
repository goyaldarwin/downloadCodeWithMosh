package com.darwin.learning.projects.downloadcodewithmosh.services;

import com.darwin.learning.projects.downloadcodewithmosh.models.Course;
import com.darwin.learning.projects.downloadcodewithmosh.models.CourseModule;

import java.util.List;

public interface CourseWebPageHtmlParser {
    List<CourseModule> addAllModulesAndLectureDetails(Course course);
}
