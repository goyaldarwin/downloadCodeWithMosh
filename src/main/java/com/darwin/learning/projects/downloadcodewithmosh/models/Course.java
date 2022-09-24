package com.darwin.learning.projects.downloadcodewithmosh.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Duration;
import java.util.List;

@Data
@EqualsAndHashCode(of = {"id"})
public class Course implements Comparable<Course> {
    String id;
    String name;
    String url;
    double number;
    Duration duration;
    double sizeOfCourseInMb;
    Boolean isProcessed;
    DownloadPriority downloadPriority;
    List<CourseModule> modules;

    @Override
    public int compareTo(Course o) {
        return Double.compare(this.number, o.number);
    }
}
