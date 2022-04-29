package com.gr.learningpath.facade.group;

import com.gr.learningpath.api.*;
import com.gr.learningpath.api.request.SessionRequest;
import com.gr.learningpath.api.request.TaskRequest;
import com.gr.learningpath.api.request.group.GroupRequest;
import com.gr.learningpath.api.response.group.GroupResponse;
import com.gr.learningpath.domain.*;
import com.gr.learningpath.domain.course.Chapter;
import com.gr.learningpath.domain.document.Document;
import com.gr.learningpath.domain.document.DocumentType;
import com.gr.learningpath.domain.group.Group;
import com.gr.learningpath.mapper.MapperRegistry;
import com.gr.learningpath.service.GroupService;
import com.gr.learningpath.service.UserService;
import com.gr.learningpath.service.course.CourseService;
import com.gr.learningpath.util.DocumentUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupFacadeImpl implements GroupFacade {
    private final MapperRegistry mapperRegistry;
    private final CourseService courseService;
    private final UserService userService;
    private final GroupService groupService;


    @Transactional
    @Override
    public GroupResponse createGroup(final GroupRequest groupRequest, final MultipartFile groupImage) throws IOException {

        if (groupRequest.getId() == null) {
            final List<Student> studentList = groupRequest.getMembers()
                    .stream()
                    .map(DropDown::getValue)
                    .map(userService::getStudentByUserId)
                    .collect(Collectors.toList());

            final List<Member> members = studentList.stream()
                    .map(student -> Member.builder()
                            .status(MemberStatus.ACTIVE)
                            .student(student)
                            .build())
                    .collect(Collectors.toList());

            final Group group = Group.builder()
                    .name(groupRequest.getName())
                    .course(courseService.getCourse(groupRequest.getCourse().getValue(), null))
                    .imageType(groupImage.getContentType())
                    .imageName(groupImage.getOriginalFilename())
                    .imageBinary(groupImage.getBytes())
//                    .department(userService.getDepartment(groupRequest.getDepartment().getValue()))
                    .build();

            members.forEach(group::addMember);
            return mapperRegistry.getMapper(Group.class, GroupResponse.class).map(groupService.saveGroup(group));

        } else {
            final Group groupToBeUpdated = groupService.getGroup(groupRequest.getId());
            groupToBeUpdated.setCourse(courseService.getCourse(groupRequest.getCourse().getValue(), null));
            groupToBeUpdated.setImageName(groupImage.getOriginalFilename());
            groupToBeUpdated.setImageType(groupImage.getContentType());
            groupToBeUpdated.setImageBinary(groupImage.getBytes());
//            groupToBeUpdated.setDepartment(userService.getDepartment(groupRequest.getDepartment().getValue()));

            final List<Long> memberIds = groupRequest.getMembers().stream().map(DropDown::getValue).collect(Collectors.toList());
            groupToBeUpdated.setMembers(groupService.findAllMembersByIds(memberIds));
            return mapperRegistry.getMapper(Group.class, GroupResponse.class).map(groupService.saveGroup(groupToBeUpdated));
        }

    }

    public List<GroupResponse> getAllGroupList() {
        return groupService.getAllGroup()
                .stream()
              .map(group -> mapperRegistry.getMapper(Group.class, GroupResponse.class).map(group))
               .collect(Collectors.toList());
    }

    @Override
    public GroupResponse findGroup(Long id) {
        return mapperRegistry.getMapper(Group.class, GroupResponse.class).map(groupService.getGroup(id));
    }

    @Transactional
    @Override
    public SessionDetails createSession(final SessionRequest sessionForm, final MultipartFile[] docs) throws IOException {
        final Chapter chapter = courseService.getChapter(sessionForm.getChapter().getValue());
        final Group group = groupService.getGroup(sessionForm.getGroupId());
        final Session session = groupService.saveSession(Session.builder()
                .title(sessionForm.getTitle())
                .chapter(chapter)
                .duration(sessionForm.getDuration())
                .group(group)
                .date(sessionForm.getDate())
                .description(sessionForm.getDescription())
                .build());
        final List<Document> documents = DocumentUtil.buildDocuments(docs, null, null, DocumentType.SESSION_DOCS, session, null);

        courseService.saveDocuments(documents);

        final String docsStringCommaSeparated= documents.stream()
                .filter(document -> document.getDocContentCategory().equals(DocumentType.SESSION_DOCS))
                .map(Document::getDocName)
                .collect(Collectors.joining(","));

        final List<Member> members = sessionForm.getAttendances()
                .stream().map(attendance -> Member.builder()
                        .group(group)
                        .student(userService.getStudentByUserId(attendance.getStudentId()))
                        .session(session)
                        .status(MemberStatus.ACTIVE)
                        .evaluationValue(attendance.getEvaluationValue())
                        .present(attendance.getPresent())
                        .build()).collect(Collectors.toList());

        groupService.saveMembers(members);

        return SessionDetails.builder()
                .createDate(session.getDate())
                .chapterTitle(session.getChapter().getChapterTitle())
                .docs(docsStringCommaSeparated)
                .duration(session.getDuration())
                .build();
    }

    @Transactional
    @Override
    public TaskDetails createTask(final TaskRequest taskRequest, final MultipartFile[] docs) throws IOException {
        final Chapter chapter = courseService.getChapter(taskRequest.getChapter().getValue());
        final Session session = courseService.getSession(taskRequest.getSession().getValue());

        final List<Document> documents = DocumentUtil.buildDocuments(docs, null, null, DocumentType.TASK_DOCS, session, null);

        courseService.saveDocuments(documents);

        final Task task = groupService.saveTask(Task.builder()
                .chapter(chapter)
                .session(session)
                .dueDate(taskRequest.getDueDate())
                .startDate(taskRequest.getStartDate())
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .build());

        final String docsStringCommaSeparated = documents.stream()
                .filter(document -> document.getDocContentCategory().equals(DocumentType.SESSION_DOCS))
                .map(Document::getDocName)
                .collect(Collectors.joining(","));

        return TaskDetails
                .builder()
                .chapterName(task.getChapter().getChapterTitle())
                .sessionTitle(task.getSession().getTitle())
                .dueDate(task.getDueDate())
                .startDate(task.getStartDate())
                .title(task.getTitle())
                .description(task.getDescription())
                .docs(docsStringCommaSeparated)
                .build();
    }
}
