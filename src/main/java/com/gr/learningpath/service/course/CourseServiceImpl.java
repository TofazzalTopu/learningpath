package com.gr.learningpath.service.course;

import com.gr.learningpath.domain.Department;
import com.gr.learningpath.domain.Session;
import com.gr.learningpath.domain.User;
import com.gr.learningpath.domain.course.*;
import com.gr.learningpath.domain.document.Document;
import com.gr.learningpath.exceptions.EntityAlreadyExistsException;
import com.gr.learningpath.exceptions.EntityNotFoundException;
import com.gr.learningpath.repositories.SessionRepository;
import com.gr.learningpath.repositories.UserRepository;
import com.gr.learningpath.repositories.course.*;
import com.gr.learningpath.repositories.department.DepartmentRepository;
import com.gr.learningpath.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;
    private final ChapterRepository chapterRepository;
    private final CourseContentRepository courseContentRepository;
    private final CoursePublicationRepository coursePublicationRepository;
    private final DocumentRepository documentRepository;
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;


    public Optional<List<Course>> getAllCourses(Long userId) {
        return courseRepository.findByUserId(userId);
    }

    @Override
    public List<Chapter> getAllChapter(Long courseId) {
        return chapterRepository.findAllByCourseId(courseId);
    }

    public Optional<Course> findCourse(@Nonnull Long id, @Nonnull Long userId) {
        return courseRepository.findByIdAndUser(id, userId);
    }

    public Optional<Course> findCourseByTitle(@Nonnull String title, @Nonnull Long userId) {
        return courseRepository.findByTitleAndUserId(title, userId);
    }

    public Optional<Course> findByTitleForUpdateCourse(@Nonnull String title, @Nonnull Long id, @Nonnull Long userId) {
        return courseRepository.findByTitleForUpdateCourse(title, id, userId);
    }

    @Transactional
    public void deleteCourse(@Nonnull Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new EntityNotFoundException(Course.class);
        }
        courseRepository.delete(courseRepository.getOne(courseId));
    }

    public Optional<Chapter> findChapterByCourseIdAndChapterId(@Nonnull Long chapterId, @Nonnull final Long courseId) {
        return chapterRepository.findByCourseIdAndId(courseId, chapterId);
    }


    @Override
    public Optional<Chapter> findChapter(@Nonnull final Long id) {
        return chapterRepository.findById(id);
    }

    @Transactional
    @Override
    public Chapter saveChapter(@Nonnull final Chapter chapter) {

        if (!Objects.isNull(chapter.getChapterTitle()) &&
                Objects.isNull(chapter.getId()) && // if chapter id is null means that we are in create chapter
                findByChapterTitleAndCourse(chapter.getChapterTitle(), chapter.getCourse().getId()).isPresent()) // if chapterTitle already exists throw exception

            throw new EntityAlreadyExistsException(Chapter.class);
        else
            return chapterRepository.save(chapter);
    }

    @Transactional
    @Override
    public Course updateChapter(@Nonnull Course course) {
        return courseRepository.save(course);
    }

    public List<CourseChapterDTO> findByCourseIdAndChapterTitle(@Nonnull Long courseId, @Nonnull final String chapterTitle) {
        return chapterRepository.findByCourseIdAndChapterTitle(courseId, chapterTitle);
    }

    public Optional<List<Chapter>> findByChapterTitleAndCourse(@Nonnull final String chapterTitle, @Nonnull Long courseId) {
        return chapterRepository.findByChapterTitleAndCourse(chapterTitle, courseId);
    }

    @Transactional
    @Override
    public CourseContent saveCourseContent(@Nonnull CourseContent courseContent) {
        return courseContentRepository.save(courseContent);
    }

    @Transactional
    @Override
    public List<Document> saveDocuments(List<Document> allDocs) {
        return documentRepository.saveAll(allDocs);
    }

    @Transactional
    @Override
    public Optional<List<Document>> fetchDocuments(Long chapterId) {
        return documentRepository.findAllByChapterId(chapterId);
    }

    public Course getCourse(@Nonnull Long courseId, @Nonnull Long userId) {
        Course course = findCourse(courseId, userId).orElseThrow(() -> new EntityNotFoundException(Course.class));
        List<Chapter> chapter = chapterRepository.findByCourseId(courseId);
        course.setChapterList(chapter);
        return course;
    }

    @Override
    public Chapter getChapter(@Nonnull final Long id) {
        return findChapter(id).orElseThrow(() -> new EntityNotFoundException(Chapter.class));
    }

    public Optional<Department> findDepartment(final Long departmentId) {
        return departmentRepository.findById(departmentId);
    }

    public Department getDepartment(Long departmentId) {
        return findDepartment(departmentId).orElseThrow(() -> new EntityNotFoundException(Department.class));
    }

    @Transactional
    @Override
    public Course saveCourse(final Course course) {
        if (!Objects.isNull(course.getTitle()) && findCourseByTitle(course.getTitle(), course.getUser().getId()).isPresent())
            throw new EntityAlreadyExistsException(Course.class);
        else
            return courseRepository.save(course);
    }

    @Transactional
    @Override
    public Course updateCourse(final Course course) {
        if (!Objects.isNull(course.getTitle()) && findByTitleForUpdateCourse(course.getTitle(), course.getId(), course.getUser().getId()).isPresent())
            throw new EntityAlreadyExistsException(Course.class);
        else
            return courseRepository.save(course);
    }

    @Transactional
    @Override
    public void deleteChapter(Long chapterId, Long userId) {
        chapterRepository.delete(chapterRepository.getOne(chapterId));
    }

    @Transactional
    @Override
    public void deleteDocuments(Long chapterId) {
        documentRepository.deleteAll(documentRepository.findAllByChapterId(chapterId).get());
    }

    @Override
    public Session getSession(@Nonnull final Long id) {
        return findSession(id).orElseThrow(() -> new EntityNotFoundException(Session.class));
    }

    @Override
    public Optional<Session> findSession(@Nonnull final Long id) {
        return sessionRepository.findById(id);
    }

    public Optional<CourseContent> findCourseContent(@Nonnull final Long courseId) {
        return courseContentRepository.findById(courseId);
    }

    @Transactional
    @Override
    public void deleteCourseContent(@Nonnull final Long courseContentId, @Nonnull final Long userId) {
        courseContentRepository.delete(courseContentRepository.getOne(courseContentId));
    }

    @Transactional
    @Override
    public void publishCourse(Long courseId, boolean isPrivate) {

        final Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException(Course.class));
        isPrivate = course.getCoursePublication() != null ? !course.getCoursePublication().isPrivate() : isPrivate;

        CoursePublication coursePublication;
        if (course.getCoursePublication() == null) {
            coursePublication = CoursePublication.builder()
                    .course(course)
                    .isPrivate(isPrivate)
                    .build();
        } else {
            coursePublication = coursePublicationRepository.findByCourseId(courseId);
            coursePublication.setPrivate(isPrivate);
        }

        final Optional<User> user = userRepository.findById(course.getUser().getId());
        user.get().setIsMentor(true);
        userRepository.save(user.get());

        coursePublicationRepository.save(coursePublication);

    }

    @Transactional
    @Override
    public void deletePublication(@Nonnull Long courseId){

        if (coursePublicationRepository.existsByCourseId(courseId) != 0) {
            coursePublicationRepository.delete(coursePublicationRepository.findByCourseId(courseId));
        } else {
            throw new EntityNotFoundException(CoursePublication.class);
        }

        final User user = userRepository.findByUsername(SecurityUtils.getLoggedInUser().get())
                .orElseThrow(() -> new EntityNotFoundException(User.class));

        List<Course> userCourses = courseRepository.findByUserId(user.getId())
                        .orElseThrow(() -> new EntityNotFoundException(Course.class));

        user.setIsMentor(false);
        for (Course course: userCourses){
            if (coursePublicationRepository.existsByCourseId(course.getId()) != 0){
                user.setIsMentor(true);
            };
        }
    }



}


