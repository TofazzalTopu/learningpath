package com.gr.learningpath.service;

import com.gr.learningpath.domain.*;
import com.gr.learningpath.domain.group.Group;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public interface GroupService {

    List<Group> getAllGroup();

    Group saveGroup(final Group group);

    Group getGroup(final Long id);

    Optional<Group> findGroup(@Nonnull Long id);

    List<Member> findAllMembersByIds(final List<Long> ids);

    Session saveSession(final Session session);

    List<Member> saveMembers(final List<Member> members);

    Task saveTask(final Task task);
}

