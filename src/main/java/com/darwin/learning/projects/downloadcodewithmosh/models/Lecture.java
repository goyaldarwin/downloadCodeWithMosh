package com.darwin.learning.projects.downloadcodewithmosh.models;

import lombok.Data;

import java.time.Duration;

@Data
public class Lecture {
    String name;
    String url;
    String id;
    String courseId;
    String downloadUrl;
    Duration duration;
}
