package com.darwin.learning.projects.downloadcodewithmosh.utils;

import com.darwin.learning.projects.downloadcodewithmosh.models.Lecture;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;

import java.time.Duration;

@Slf4j
@UtilityClass
public class LectureUtils {
    public static String getLectureUrlFromId(Lecture lecture) {
        return "https://codewithmosh.com/courses/" + lecture.getCourseId() + "/lectures/" + lecture.getId();
    }

    public static String getSanitizedName(String anchorText) {
        if (anchorText.startsWith("Start ")) {
            anchorText = anchorText.substring(6);
        }
        if (anchorText.contains("(")) {
            return anchorText.substring(0, anchorText.indexOf('(') - 1);
        }
        return anchorText;
    }

    public static Duration getLectureDurationInMinutes(String anchorText) {
        String durationString = "0:0";
        long totalSeconds = 0;
        if (anchorText.contains("(")) {
            durationString = anchorText.substring(anchorText.indexOf('(') + 1, anchorText.indexOf(')'));
        }
        String[] durationArray = durationString.split(":");
        try {
            int minutes = Integer.valueOf(durationArray[0]);
            int seconds = Integer.valueOf(durationArray[1]);
            totalSeconds = seconds + minutes * 60L;
        } catch (Exception e) {
            log.error("Error occurred while getting duration of course for anchorText: {}", anchorText);
        }
        return Duration.ofSeconds(totalSeconds);
    }

    public static Lecture getLecture(Element anchor) {
        Attributes anchorAttributes = anchor.attributes();
        Lecture lecture = new Lecture();
        lecture.setId(anchorAttributes.get("data-ss-lecture-id"));
        lecture.setCourseId(anchorAttributes.get("data-ss-course-id"));
        lecture.setUrl(getLectureUrlFromId(lecture));

        String anchorText = anchor.getElementsByClass("title-container").last().text();
        lecture.setName(getSanitizedName(anchorText));
        lecture.setDuration(getLectureDurationInMinutes(anchorText));
        return lecture;
    }
}
