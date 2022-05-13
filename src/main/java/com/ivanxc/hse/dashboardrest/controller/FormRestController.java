package com.ivanxc.hse.dashboardrest.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.ivanxc.hse.dashboardrest.dto.ElementAttributeValueReadDto;
import com.ivanxc.hse.dashboardrest.dto.ElementOnFormCreateDto;
import com.ivanxc.hse.dashboardrest.dto.ElementOnFormReadDto;
import com.ivanxc.hse.dashboardrest.dto.FormCreateDto;
import com.ivanxc.hse.dashboardrest.dto.FormReadDto;
import com.ivanxc.hse.dashboardrest.dto.UserFormCreateReadDto;
import com.ivanxc.hse.dashboardrest.exception.ResourceNotFoundException;
import com.ivanxc.hse.dashboardrest.response.DeleteResponse;
import com.ivanxc.hse.dashboardrest.service.FormService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/forms")
@RequiredArgsConstructor
public class FormRestController {

    private final FormService formService;

    // == Form

    @GetMapping("/{form-id}")
    FormReadDto getForm(@PathVariable("form-id") long id) {
        return formService.getForm(id);
    }

    @PostMapping
    FormReadDto createForm(@RequestBody FormCreateDto formCreateDto) {
        return formService.createForm(formCreateDto);
    }

    @PatchMapping("/{form-id}")
    FormReadDto updateForm(@PathVariable("form-id") long id, @RequestBody JsonPatch patch) {
        return formService.patch(id, patch);
    }

    @DeleteMapping("/{form-id}")
    DeleteResponse deleteForm(@PathVariable("form-id") long id) {
        if (formService.deleteForm(id)) {
            return new DeleteResponse("Form was deleted");
        } else {
            throw new ResourceNotFoundException("Form not found");
        }
    }

    // === Users on form

    @GetMapping("/{form-id}/users")
    List<UserFormCreateReadDto> getUsersOfForm(@PathVariable("form-id") long id) {
        return formService.getUsersOfForm(id);
    }

    @PostMapping("/{form-id}/users")
    UserFormCreateReadDto addUsersToForm(@PathVariable("form-id") long id,
        @RequestBody UserFormCreateReadDto userOnForm) {
        return formService.addUsersToForm(userOnForm);
    }

    @PatchMapping("/{form-id}/users/{user-id}")
    UserFormCreateReadDto changeUserOnForm(@PathVariable("form-id") long id, @PathVariable("user-id") long userId,
        @RequestBody JsonPatch patch) {
        return formService.patchUserOnForm(id, userId, patch);
    }

    @DeleteMapping("/{form-id}/users/{user-id}")
    DeleteResponse removeUsersFromForm(@PathVariable(name = "form-id") long formId,
        @RequestParam("user-id") long userId) {
        if (formService.removeUsersFromForm(formId, userId)) {
            return new DeleteResponse("User was deleted from form");
        } else {
            throw new ResourceNotFoundException("User on form not found");
        }
    }

    // === Elements at form

    @GetMapping("/{form-id}/elements")
    List<ElementOnFormReadDto> getElementsOnForm(@PathVariable("form-id") long formId) {
        return formService.getElementsOnForm(formId);
    }

    @GetMapping("/{form-id}/elements/{element-id}")
    ElementOnFormReadDto getElementOnForm(@PathVariable("form-id") long formId,
        @RequestParam("element-id") long elementId) {
        return formService.getElementOnForm(formId, elementId);
    }

    @PostMapping("/{form-id}/elements")
    ElementOnFormReadDto addElementToForm(@PathVariable("form-id") long id,
        @RequestBody ElementOnFormCreateDto elementOnFormCreateDto) {
        return formService.addElementToForm(id, elementOnFormCreateDto);
    }

    @PatchMapping("/{form-id}/elements/{element-id}/{attribute-id}")
    ElementAttributeValueReadDto changeElementAttributeOnForm(@PathVariable("form-id") long formId,
        @PathVariable("attribute-id") long attributeId,
        @RequestBody JsonPatch patch) {
        return formService.patchAttributeValue(formId, attributeId, patch);
    }

    @DeleteMapping("/{form-id}/elements/{element-id}")
    DeleteResponse removeElementFromForm(@PathVariable("form-id") long formId,
        @RequestParam("element-id") long elementId) {
        if (formService.removeElementFromForm(formId, elementId)) {
            return new DeleteResponse("Element was deleted from form");
        } else {
            throw new ResourceNotFoundException("Element not found");
        }
    }

}
