package com.darwin.learning.projects.downloadcodewithmosh.utils;

import com.darwin.learning.projects.downloadcodewithmosh.models.Course;
import com.darwin.learning.projects.downloadcodewithmosh.models.DownloadPriority;
import lombok.experimental.UtilityClass;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;

@UtilityClass
public class CourseUtils {
    public static String getUrlFromId(String id) {
        return "https://codewithmosh.com/courses/enrolled/" + id;
    }

    public static Course getCourseDetails(Element element) {
        Attributes elementAttributes = element.attributes();
        Course course = new Course();
        Element title = element.getElementsByClass("course-listing-title").get(0);
        course.setName(title.text());
        course.setId(elementAttributes.get("data-course-id"));
        course.setUrl(CourseUtils.getUrlFromId(course.getId()));
        course.setDownloadPriority(DownloadPriority.DECIDE_MANUALLY);
        return course;
    }
}
