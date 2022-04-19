package com.ivanxc.hse.dashboardrest.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.ivanxc.hse.dashboardrest.dto.FormReadDto;
import com.ivanxc.hse.dashboardrest.dto.UserCreateEditDto;
import com.ivanxc.hse.dashboardrest.dto.UserReadDto;
import com.ivanxc.hse.dashboardrest.exception.ResourceNotFoundException;
import com.ivanxc.hse.dashboardrest.response.DeleteResponse;
import com.ivanxc.hse.dashboardrest.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @PostMapping
    public UserReadDto createUser(@RequestBody UserCreateEditDto userCreateEditDto) {
        return userService.createUser(userCreateEditDto);
    }

    @GetMapping("/me")
    public UserReadDto getUserInfo() {
        return userService.getUserInfo();
    }

    @GetMapping("/me/forms")
    public List<FormReadDto> getUserForms() {
        return userService.getUserForms();
    }

    @PatchMapping(path = "/me")
    public UserReadDto updateUser(@RequestBody JsonPatch patch) {
        return userService.patch(patch);
    }

    @DeleteMapping("/me")
    public DeleteResponse deleteUser() {
        if (userService.deleteUser()) {
            return new DeleteResponse("User was deleted");
        } else {
            throw new ResourceNotFoundException("User not found");
        }
    }

}
