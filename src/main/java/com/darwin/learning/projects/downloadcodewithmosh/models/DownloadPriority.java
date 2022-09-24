package com.darwin.learning.projects.downloadcodewithmosh.models;

import lombok.Getter;

@Getter
public enum DownloadPriority {
    MUST("src/main/resources/must/"),
    GOOD_TO_HAVE("src/main/resources/good_to_have/"),
    NOT_NEEDED(null),
    DECIDE_MANUALLY(null);
    String directory;

    DownloadPriority(String directory) {
        this.directory = directory;
    }

    public String getFileName(ProcessingStatus processingStatus) {
        return this.getDirectory() + "Courses_" + processingStatus + ".json";
    }

    public String getFileName(Course course) {
        return this.getDirectory() + "individualCourses/" + course.getName() + ".json";
    }
}
