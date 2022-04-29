package com.gr.learningpath.service.course;

import com.gr.learningpath.domain.*;
import com.gr.learningpath.domain.course.Chapter;
import com.gr.learningpath.domain.course.Course;
import com.gr.learningpath.domain.course.CourseContent;
import com.gr.learningpath.domain.document.Document;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public interface CourseService {

    Optional<List<Course>> getAllCourses(Long userId);

    List<Chapter> getAllChapter(Long courseId);

    Optional<Department> findDepartment(final Long departmentId);

    Department getDepartment(Long departmentId);

    Course saveCourse(final Course course);

    Course updateCourse(final Course course);

    Course getCourse(@Nonnull Long id, @Nonnull Long userId);

    void deleteCourse(@Nonnull Long courseId);

    Chapter getChapter(@Nonnull Long id);

    Session getSession(@Nonnull Long id);

    Optional<Session> findSession(@Nonnull Long id);

    Optional<Course> findCourse(@Nonnull Long id, @Nonnull Long userId);

    Optional<Chapter> findChapterByCourseIdAndChapterId(@Nonnull Long chapterId, @Nonnull final Long courseId);

    Optional<Chapter> findChapter(@Nonnull Long id);

    Chapter saveChapter(@Nonnull Chapter chapter);

    Course updateChapter(@Nonnull Course course);

    CourseContent saveCourseContent(@Nonnull CourseContent courseContent);

    List<Document> saveDocuments(List<Document> allDocs);

    Optional<List<Document>> fetchDocuments(Long chapterId);

    void deleteChapter(Long chapterId, Long userId);

    void deleteDocuments(Long chapterId);

    Optional<CourseContent> findCourseContent(Long courseId);

    void deleteCourseContent(Long courseId, Long userId);

    void publishCourse(Long courseId, boolean isPrivate);

    void deletePublication(Long courseId);

}

