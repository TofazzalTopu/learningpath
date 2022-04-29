package com.gr.learningpath.facade.group;

import com.gr.learningpath.api.SessionDetails;
import com.gr.learningpath.api.TaskDetails;
import com.gr.learningpath.api.request.SessionRequest;
import com.gr.learningpath.api.request.TaskRequest;
import com.gr.learningpath.api.request.group.GroupRequest;
import com.gr.learningpath.api.response.group.GroupResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface GroupFacade{
    GroupResponse createGroup(final GroupRequest groupRequest, final MultipartFile groupImage) throws IOException;

     List<GroupResponse> getAllGroupList();

     GroupResponse findGroup(final Long id);

    SessionDetails createSession(SessionRequest sessionRequest, MultipartFile[] docs) throws IOException;

    TaskDetails createTask(TaskRequest taskForm, MultipartFile[] docs) throws IOException;
}
