package com.gr.learningpath.controller.group;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gr.learningpath.api.request.group.GroupRequest;
import com.gr.learningpath.api.response.group.GroupResponse;
import com.gr.learningpath.facade.group.GroupFacade;
import com.gr.learningpath.validator.GroupValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
@Log4j2
public class GroupController {
    private final GroupFacade groupFacade;
    private final ObjectMapper objectMapper;
    private final GroupValidator validator;

    @PostMapping()
    public GroupResponse createGroup(@RequestParam("groupImage") final MultipartFile groupImage,
                                     @RequestPart("groupForm") final String groupFormJson,
                                     final BindingResult result
    ) throws IOException {
        if (groupImage == null) {
            throw new IllegalStateException();
        }
        GroupRequest groupRequest = null;
        try {
            groupRequest = objectMapper.readValue(groupFormJson, GroupRequest.class);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        validator.validate(ObjectUtils.defaultIfNull(groupRequest, new GroupRequest()), result);

        return groupFacade.createGroup(groupRequest, groupImage);

    }
    @GetMapping()
    public List<GroupResponse> getGroupList(){
        return groupFacade.getAllGroupList();
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public GroupResponse findArea(@PathVariable Long id) {
        return groupFacade.findGroup(id);
    }
}
