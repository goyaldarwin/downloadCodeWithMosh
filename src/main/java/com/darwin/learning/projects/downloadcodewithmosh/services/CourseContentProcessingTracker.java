package com.darwin.learning.projects.downloadcodewithmosh.services;

import com.darwin.learning.projects.downloadcodewithmosh.models.Course;
import com.darwin.learning.projects.downloadcodewithmosh.models.DownloadPriority;

import java.util.List;

public interface CourseContentProcessingTracker {
    void saveCoursesWithoutModulesInTrackingFile();

    List<Course> getCoursesToBeProcessed(DownloadPriority downloadPriority);

    void updateStatusInTrackingFile(List<Course> coursesToDownload);
}
