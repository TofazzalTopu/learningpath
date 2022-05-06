package com.gr.learningpath.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gr.learningpath.api.SessionDetails;
import com.gr.learningpath.api.request.SessionRequest;
import com.gr.learningpath.facade.group.GroupFacade;
import com.gr.learningpath.validator.GroupValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
@Log4j2
public class SessionController {
    private final GroupFacade groupFacade;
    private final ObjectMapper objectMapper;
    private final GroupValidator validator;

    @PostMapping()
    public SessionDetails createSession(
            @RequestParam("docs") final MultipartFile[] docs,
            @RequestPart("sessionForm") final String sessionFormJson,
            final BindingResult result
    ) throws IOException {

        SessionRequest sessionForm = null;
        try {
            sessionForm = objectMapper.readValue(sessionFormJson, SessionRequest.class);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        validator.validate(ObjectUtils.defaultIfNull(sessionForm, new SessionRequest()), result);

        return groupFacade.createSession(sessionForm, docs);

    }

}
