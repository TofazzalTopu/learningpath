package com.gr.learningpath.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gr.learningpath.api.TaskDetails;
import com.gr.learningpath.api.request.SessionRequest;
import com.gr.learningpath.api.request.TaskRequest;
import com.gr.learningpath.facade.group.GroupFacade;
import com.gr.learningpath.validator.TaskValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Log4j2
public class TaskController {
    private final ObjectMapper objectMapper;
    private final TaskValidator validator;
    private final GroupFacade groupFacade;

    @PostMapping()
    public TaskDetails createTasks(
            @RequestParam("docs") final MultipartFile[] docs,
            @RequestPart("taskForm") final String taskFormJson,
            final BindingResult result
    ) throws IOException {

        TaskRequest taskForm = null;
        try {
            taskForm = objectMapper.readValue(taskFormJson, TaskRequest.class);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        validator.validate(ObjectUtils.defaultIfNull(taskForm, new SessionRequest()), result);

        return groupFacade.createTask(taskForm, docs);

    }

}
