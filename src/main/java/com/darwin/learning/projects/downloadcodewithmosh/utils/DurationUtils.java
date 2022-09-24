package com.darwin.learning.projects.downloadcodewithmosh.utils;

import lombok.experimental.UtilityClass;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class DurationUtils {
    public static String getMessage(Duration duration) {
        return " (" + duration.toMinutes() + " " + TimeUnit.MINUTES + ")";
    }
    
    public static String getDurationString(Duration duration) {
        StringBuilder stringBuilder = new StringBuilder();
        if (duration.toHours() != 0) {
            stringBuilder.append(" (").append(duration.toHours()).append(" hrs)/");
        } else {
            stringBuilder.append(" (").append(duration.toMinutes()).append(" mins)/");
        }
        return stringBuilder.toString();
    }
    public static String getDurationStringForYoutubeChapter(Duration duration) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(duration.toHours())
                .append(":")
                .append(duration.toMinutes() % 60 < 10 ? "0" : "")
                .append(duration.toMinutes() % 60)
                .append(":")
                .append(duration.getSeconds() % 60 < 10 ? "0" : "")
                .append(duration.getSeconds() % 60);
        return stringBuilder.toString();
    }
}
