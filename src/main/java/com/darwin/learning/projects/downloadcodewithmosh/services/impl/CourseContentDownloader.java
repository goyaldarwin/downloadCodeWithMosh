package com.darwin.learning.projects.downloadcodewithmosh.services.impl;

import com.darwin.learning.projects.downloadcodewithmosh.models.Course;
import com.darwin.learning.projects.downloadcodewithmosh.models.CourseModule;
import com.darwin.learning.projects.downloadcodewithmosh.models.Lecture;
import com.darwin.learning.projects.downloadcodewithmosh.services.CourseContentProcessor;
import com.darwin.learning.projects.downloadcodewithmosh.utils.DurationUtils;
import com.darwin.learning.projects.downloadcodewithmosh.utils.FolderUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

@Slf4j
public class CourseContentDownloader implements CourseContentProcessor {
    private static final String DOWNLOAD_FOLDER_PATH = "/Users/darwin/Desktop/Downloads/";

    @Override
    public void process(Course course) {
        preProcess(course, log);
        for (CourseModule module : course.getModules()) {
            log.debug("Downloading lectures of module: {}", module.getName());
            String directoryToDownloadLecture = FolderUtils.appendCourseAndModuleFolders(DOWNLOAD_FOLDER_PATH, course, module);
            FolderUtils.createAFolder(directoryToDownloadLecture);
            for (Lecture lecture : module.getLectures()) {
                downloadLecture(directoryToDownloadLecture, lecture);
            }
        }
        postProcess(course, log);
    }

    private void downloadLecture(String directoryToDownloadLecture, Lecture lecture) {
        if (lecture.getDownloadUrl() != null) {
            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new URL(lecture.getDownloadUrl()).openStream());
                 FileOutputStream fileOutputStream = new FileOutputStream(directoryToDownloadLecture + "/" + lecture.getName() + ".mp4")) {
                log.debug("Downloading lectureName: {} lectureUrl: {}", lecture.getName(), lecture.getDownloadUrl());
                byte[] dataBuffer = new byte[2048];
                int bytesRead;
                while ((bytesRead = bufferedInputStream.read(dataBuffer, 0, 2048)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
