package com.darwin.learning.projects.downloadcodewithmosh.services.impl;

import com.darwin.learning.projects.downloadcodewithmosh.constants.Constants;
import com.darwin.learning.projects.downloadcodewithmosh.models.Course;
import com.darwin.learning.projects.downloadcodewithmosh.models.CourseModule;
import com.darwin.learning.projects.downloadcodewithmosh.models.Lecture;
import com.darwin.learning.projects.downloadcodewithmosh.services.CourseWebPageHtmlParser;
import com.darwin.learning.projects.downloadcodewithmosh.utils.CourseModuleUtils;
import com.darwin.learning.projects.downloadcodewithmosh.utils.LectureUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.darwin.learning.projects.downloadcodewithmosh.utils.HtmlUtils.getHtmlFromUrl;

@Slf4j
public class CourseWebPageHtmlParserImpl implements CourseWebPageHtmlParser {

    @Override
    public List<CourseModule> addAllModulesAndLectureDetails(Course course) {
        return processCourseUrl(course);
    }

    private List<CourseModule> processCourseUrl(Course course) {
        log.debug("Downloading course html from url: {}", course.getUrl());
        Document coursePageHtml = getHtmlFromUrl(course.getUrl());
        Elements modules = coursePageHtml.getElementsByClass(Constants.MODULE_CLASS);
        List<CourseModule> courseModules = getCourseModuleWithLectureDetails(modules);
        long courseDurationInNanos = 0;
        for (CourseModule courseModule : courseModules) {
            long moduleDurationInNanos = 0;
            for (Lecture lecture : courseModule.getLectures()) {
                populateDownloadUrl(lecture);
                moduleDurationInNanos += lecture.getDuration().toNanos();
            }
            courseModule.setDuration(Duration.ofNanos(moduleDurationInNanos));
            courseDurationInNanos += moduleDurationInNanos;
        }
        course.setDuration(Duration.ofNanos(courseDurationInNanos));
        course.setModules(courseModules);
        return courseModules;
    }

    private void populateDownloadUrl(Lecture lecture) {
        Document lectureHtml = getHtmlFromUrl(lecture.getUrl());
        Elements elements = lectureHtml.getElementsByClass("download");
        if (!elements.isEmpty()) {
            lecture.setDownloadUrl(elements.get(0).attributes().get("href"));
        } else {
            lecture.setDownloadUrl(null);
        }
    }

    private List<CourseModule> getCourseModuleWithLectureDetails(Elements modules) {
        log.debug("Processing course modules to get lectures.");
        Double moduleNumber = 1d;
        List<CourseModule> courseModules = new ArrayList<>();
        for (Element module : modules) {
            String moduleName = module.getElementsByClass("section-title").text();
            CourseModule courseModule = new CourseModule();
            courseModule.setModuleNumber(moduleNumber);
            if (moduleName.contains("-")) {
                courseModule.setName(CourseModuleUtils.getSanitizedModuleName(moduleName));
            } else {
                courseModule.setName((moduleNumber++).intValue() + "- " + CourseModuleUtils.getSanitizedModuleName(moduleName));
            }
            courseModule.setLectures(getLectures(module));
            courseModules.add(courseModule);
        }
        return courseModules;
    }

    private List<Lecture> getLectures(Element module) {
        List<Lecture> lectures = new ArrayList<>();
        Elements lectureList = module.getElementsByClass("section-list").get(0).children();
        for (Element lecture : lectureList) {
            Element anchor = lecture.child(0);
            lectures.add(LectureUtils.getLecture(anchor));
        }
        return lectures;
    }
}
