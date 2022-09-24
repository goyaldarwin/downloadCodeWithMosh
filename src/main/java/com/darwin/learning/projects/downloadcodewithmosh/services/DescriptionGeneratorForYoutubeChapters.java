package com.darwin.learning.projects.downloadcodewithmosh.services;

import com.darwin.learning.projects.downloadcodewithmosh.models.Course;
import com.darwin.learning.projects.downloadcodewithmosh.models.CourseModule;
import com.darwin.learning.projects.downloadcodewithmosh.models.Lecture;
import com.darwin.learning.projects.downloadcodewithmosh.utils.DurationUtils;

import java.time.Duration;

public class DescriptionGeneratorForYoutubeChapters {
    public static void generateDescription(Course course) {
        for (CourseModule module : course.getModules()) {
            Duration sum = Duration.ZERO;
            if (course.getName().equals("The Ultimate HTML5 CSS3 Series: Part 3")) {
                for (Lecture lecture : module.getLectures()) {
                    System.out.println(DurationUtils.getDurationStringForYoutubeChapter(sum) + " " + lecture.getName());
                    sum = sum.plus(lecture.getDuration());
                }
            }
        }
    }

}
