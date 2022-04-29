package com.gr.learningpath.service;

import com.gr.learningpath.domain.Member;
import com.gr.learningpath.domain.Session;
import com.gr.learningpath.domain.Task;
import com.gr.learningpath.domain.group.Group;
import com.gr.learningpath.exceptions.EntityNotFoundException;
import com.gr.learningpath.repositories.GroupRepository;
import com.gr.learningpath.repositories.MemberRepository;
import com.gr.learningpath.repositories.SessionRepository;
import com.gr.learningpath.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final SessionRepository sessionRepository;
    private final TaskRepository taskRepository;


    public List<Group> getAllGroup() { return groupRepository.findAll(); }

    @Transactional
    @Override
    public Group saveGroup(final Group group) {
        return groupRepository.save(group);
    }

    @Override
    public Group getGroup(final Long id) {
        return findGroup(id).orElseThrow(() -> new EntityNotFoundException(Group.class));
    }

    public Optional<Group> findGroup(@Nonnull Long id) {
        return groupRepository.findById(id);
    }

    @Override
    public List<Member> findAllMembersByIds(final List<Long> ids) {
        return memberRepository.findAllById(ids);
    }

    @Transactional
    @Override
    public Session saveSession(final Session session) {
        return sessionRepository.save(session);
    }

    @Transactional
    @Override
    public List<Member> saveMembers(final List<Member> members) {
        return memberRepository.saveAll(members);
    }

    @Transactional
    @Override
    public Task saveTask(final Task task) {
        return taskRepository.save(task);
    }
}
