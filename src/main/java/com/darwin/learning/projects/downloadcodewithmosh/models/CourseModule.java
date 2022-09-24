package com.darwin.learning.projects.downloadcodewithmosh.models;

import lombok.Data;

import java.time.Duration;
import java.util.List;

@Data
public class CourseModule implements Comparable<CourseModule> {
    String name;
    Duration duration;
    double moduleNumber;
    List<Lecture> lectures;

    @Override
    public int compareTo(CourseModule module) {
        return Double.compare(this.moduleNumber, module.getModuleNumber());
    }
}
