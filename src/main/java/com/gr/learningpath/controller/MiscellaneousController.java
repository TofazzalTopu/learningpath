package com.gr.learningpath.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.gr.learningpath.api.DropDown;
import com.gr.learningpath.api.request.connection.ConnectionRequest;
import com.gr.learningpath.api.request.connection.ConnectionResponse;
import com.gr.learningpath.api.request.department.DepartmentCoursesRequest;
import com.gr.learningpath.api.response.department.DepartmentCoursesResponse;
import com.gr.learningpath.api.response.department.DepartmentResponse;
import com.gr.learningpath.api.response.department.DepartmentWithCoursesResponse;
import com.gr.learningpath.facade.department.DepartmentFacade;
import com.gr.learningpath.facade.user.UserFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Log4j2
public class MiscellaneousController {
    private final UserFacade userFacade;
    private final DepartmentFacade departmentFacade;
    private final ObjectMapper objectMapper;

    @GetMapping("/departments")
    public List<DepartmentResponse> getDepartments() {
        return departmentFacade.getDepartments();
    }

    @GetMapping("/departments/{departmentId}/courses")
    public List<DepartmentCoursesResponse> getDepartmentCourses(@PathVariable @Nonnull final Long departmentId) {
        return departmentFacade.getDepartmentCourses(departmentId);
    }

    @GetMapping("/departments-with-courses")
    public List<DepartmentWithCoursesResponse> getDepartmentWithCourses() {
        return departmentFacade.getDepartmentWithCourses();
    }

    @GetMapping("/department/courses")
    public List<DepartmentCoursesResponse> getDepartmentCourses(@RequestBody final String departmentJson) throws InvalidFormatException {

        DepartmentCoursesRequest departmentRequest;
        try {
            departmentRequest = objectMapper.readValue(departmentJson, DepartmentCoursesRequest.class);
        } catch (IOException e) {
            log.error(e.getMessage());
            //TODO handle this exception with @ControllerAdvice
            throw InvalidFormatException.from(null,
                    String.format("%s", e.getMessage()), departmentJson, byte[].class);
        }

        return departmentFacade.getDepartmentCourses(departmentRequest.getDepartmentId());
    }

    @GetMapping("/members-dropdown")
    public List<DropDown> getMemberList() {
        return userFacade.getMemberList();
    }

    @GetMapping("/courses-dropdown")
    public List<DropDown> getCourseList() {
        return  userFacade.getCourseList();
    }

    @GetMapping("/chapters-dropdown")
    public List<DropDown> getChapterList(){
        return userFacade.getChapterList();
    }

    @PostMapping("/connect-with")
    public void connectWithUser(@RequestBody ConnectionRequest connectUsersRequest) throws IllegalAccessException {
        userFacade.connectWithUser(connectUsersRequest.getFromUserId(), connectUsersRequest.getToUserId());
    }

    @GetMapping("/connections")
    public List<ConnectionResponse> getUserConnections() {
        return userFacade.getUserConnections();
    }

    @DeleteMapping("/connect-with/{connectionId}")
    public void deleteUserConnection(@PathVariable Long connectionId) {
        userFacade.deleteUserConnection(connectionId);
    }

}
