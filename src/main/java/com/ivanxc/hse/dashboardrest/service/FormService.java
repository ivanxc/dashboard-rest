package com.ivanxc.hse.dashboardrest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.ivanxc.hse.dashboardrest.dto.ElementAttributeValueCreateEditDto;
import com.ivanxc.hse.dashboardrest.dto.ElementAttributeValueReadDto;
import com.ivanxc.hse.dashboardrest.dto.ElementOnFormCreateDto;
import com.ivanxc.hse.dashboardrest.dto.ElementOnFormReadDto;
import com.ivanxc.hse.dashboardrest.dto.FormCreateDto;
import com.ivanxc.hse.dashboardrest.dto.FormEditDto;
import com.ivanxc.hse.dashboardrest.dto.FormReadDto;
import com.ivanxc.hse.dashboardrest.dto.UserFormCreateReadDto;
import com.ivanxc.hse.dashboardrest.dto.UserFormEditDto;
import com.ivanxc.hse.dashboardrest.entity.Role;
import com.ivanxc.hse.dashboardrest.exception.HaveNoAccessToFormException;
import com.ivanxc.hse.dashboardrest.exception.HaveNoEnoughRightsException;
import com.ivanxc.hse.dashboardrest.exception.JsonConvertationException;
import com.ivanxc.hse.dashboardrest.exception.ResourceNotFoundException;
import com.ivanxc.hse.dashboardrest.mapper.ElementAttributeValueCreateEditMapper;
import com.ivanxc.hse.dashboardrest.mapper.ElementAttributeValueReadMapper;
import com.ivanxc.hse.dashboardrest.mapper.ElementOnFormCreateMapper;
import com.ivanxc.hse.dashboardrest.mapper.ElementOnFormReadMapper;
import com.ivanxc.hse.dashboardrest.mapper.FormCreateMapper;
import com.ivanxc.hse.dashboardrest.mapper.FormEditMapper;
import com.ivanxc.hse.dashboardrest.mapper.FormReadMapper;
import com.ivanxc.hse.dashboardrest.mapper.UserFormCreateReadMapper;
import com.ivanxc.hse.dashboardrest.mapper.UserFormEditMapper;
import com.ivanxc.hse.dashboardrest.repository.ElementAttributeValueRepository;
import com.ivanxc.hse.dashboardrest.repository.ElementOnFormRepository;
import com.ivanxc.hse.dashboardrest.repository.FormRepository;
import com.ivanxc.hse.dashboardrest.repository.UserFormRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FormService {

    private final FormRepository formRepository;
    private final UserFormRepository userFormRepository;
    private final ElementOnFormRepository elementOnFormRepository;
    private final ElementAttributeValueRepository elementAttributeValueRepository;

    private final FormReadMapper formReadMapper;
    private final FormCreateMapper formCreateMapper;
    private final FormEditMapper formEditMapper;
    private final UserFormCreateReadMapper userFormCreateReadMapper;
    private final UserFormEditMapper userFormEditMapper;
    private final ElementOnFormReadMapper elementOnFormReadMapper;
    private final ElementOnFormCreateMapper elementOnFormCreateMapper;
    private final ElementAttributeValueCreateEditMapper elementAttributeValueCreateEditMapper;
    private final ElementAttributeValueReadMapper elementAttributeValueReadMapper;

    private ObjectMapper objectMapper = new ObjectMapper();

    public FormReadDto getForm(long id) {
        if (!hasAccess(id)) {
            throw new HaveNoAccessToFormException("You have no access to this form.");
        }
        return formRepository.findById(id)
            .map(formReadMapper::map)
            .orElseThrow();
    }

    public FormReadDto createForm(FormCreateDto formCreateDto) {
        long userId = AuthService.getAuthInfo().getId();

        FormReadDto createdForm = Optional.of(formCreateDto)
            .map(formCreateMapper::map)
            .map(formRepository::save)
            .map(formReadMapper::map)
            .orElseThrow();

        UserFormCreateReadDto userFormCreateReadDto =
            new UserFormCreateReadDto(userId, createdForm.getId(), Role.OWNER);

        Optional.of(userFormCreateReadDto)
            .map(userFormCreateReadMapper::map)
            .map(userFormRepository::save)
            .map(userFormCreateReadMapper::map)
            .orElseThrow();

        return createdForm;
    }

    public Optional<FormReadDto> update(long formId, FormEditDto formEditDto) {
        if (! (hasRole(formId, Role.OWNER) || hasRole(formId, Role.EDITOR))) {
            throw new HaveNoEnoughRightsException("You have no enough rights on this form.");
        }
        return formRepository.findById(formId)
            .map(entity -> formEditMapper.map(formEditDto, entity))
            .map(formRepository::saveAndFlush)
            .map(formReadMapper::map);
    }

    public FormReadDto patch(long formId, JsonPatch patch) {
        if (! (hasRole(formId, Role.OWNER) || hasRole(formId, Role.EDITOR))) {
            throw new HaveNoEnoughRightsException("You have no enough rights on this form.");
        }

        FormEditDto formEditDto = formRepository.findById(formId)
            .map(formEditMapper::map)
            .orElseThrow(() -> new ResourceNotFoundException("No form with ID = " + formId));

        try {
            JsonNode patched = patch.apply(
                objectMapper.convertValue(formEditDto, JsonNode.class));
            formEditDto = objectMapper.treeToValue(patched, FormEditDto.class);
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new JsonConvertationException(e.getMessage());
        }

        return update(formId, formEditDto).get();
    }

    public boolean deleteForm(long formId) {
        if (!hasRole(formId, Role.OWNER)) {
            throw new HaveNoEnoughRightsException("You have no enough rights on this form.");
        }
        return formRepository.findById(formId)
            .map(entity -> {
                formRepository.delete(entity);
                formRepository.flush();
                return true;
            }).orElse(false);
    }

    // === Users on form

    public List<UserFormCreateReadDto> getUsersOfForm(long formId) {
        if (!hasAccess(formId)) {
            throw new HaveNoAccessToFormException("You have no access to this form.");
        }

        return userFormRepository.getUsersOfForm(formId)
            .stream()
            .map(userFormCreateReadMapper::map)
            .collect(Collectors.toList());
    }

    public UserFormCreateReadDto addUsersToForm(UserFormCreateReadDto userFormCreateReadDto) {
        if (!hasRole(userFormCreateReadDto.getFormId(), Role.OWNER)) {
            throw new HaveNoEnoughRightsException("You have no enough rights on this form.");
        }

        return Optional.of(userFormCreateReadDto)
            .map(userFormCreateReadMapper::map)
            .map(userFormRepository::save)
            .map(userFormCreateReadMapper::map)
            .orElseThrow();
    }

    public Optional<UserFormCreateReadDto> updateUserOnForm(long formId, long userId,
        UserFormEditDto userFormEditDto) {
        if (!hasRole(formId, Role.OWNER)) {
            throw new HaveNoEnoughRightsException("You have no enough rights on this form.");
        }
        return userFormRepository.findByUserIdAndFormId(userId, formId)
            .map(entity -> userFormEditMapper.map(userFormEditDto, entity))
            .map(userFormRepository::saveAndFlush)
            .map(userFormCreateReadMapper::map);
    }

    public UserFormCreateReadDto patchUserOnForm(long formId, long userId, JsonPatch patch) {
        if (!hasRole(formId, Role.OWNER)) {
            throw new HaveNoEnoughRightsException("You have no enough rights on this form.");
        }

        UserFormEditDto userFormEditDto = userFormRepository.findByUserIdAndFormId(userId, formId)
            .map(userFormEditMapper::map)
            .orElseThrow(() -> new ResourceNotFoundException("No user with ID + " + userId +
                " in form with ID = " + formId));

        try {
            JsonNode patched = patch.apply(
                objectMapper.convertValue(userFormEditDto, JsonNode.class));
            userFormEditDto = objectMapper.treeToValue(patched, UserFormEditDto.class);
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new JsonConvertationException(e.getMessage());
        }

        return updateUserOnForm(formId, userId, userFormEditDto).get();
    }

    public boolean removeUsersFromForm(long formId, long userId) {
        if (!hasRole(formId, Role.OWNER)) {
            throw new HaveNoEnoughRightsException("You have no enough rights on this form.");
        }

        return userFormRepository.findByUserIdAndFormId(formId, userId)
            .map(entity -> {
                userFormRepository.delete(entity);
                userFormRepository.flush();
                return true;
            }).orElse(false);
    }

    // === Elements at form

    public List<ElementOnFormReadDto> getElementsOnForm(long formId) {
        if (!hasAccess(formId)) {
            throw new HaveNoAccessToFormException("You have no access to this form.");
        }

        return elementOnFormRepository.findAllByFormId(formId)
            .stream()
            .map(elementOnFormReadMapper::map)
            .collect(Collectors.toList());
    }

    public ElementOnFormReadDto getElementOnForm(long formId, long elementId) {
        if (!hasAccess(formId)) {
            throw new HaveNoAccessToFormException("You have no access to this form.");
        }

        return elementOnFormRepository.findByFormIdAndElementId(formId, elementId)
            .map(elementOnFormReadMapper::map)
            .orElseThrow(() -> new ResourceNotFoundException("No element with ID = " + elementId));
    }

    public ElementOnFormReadDto addElementToForm(long formId, ElementOnFormCreateDto elementOnFormCreateDto) {
        if (! (hasRole(formId, Role.OWNER) || hasRole(formId, Role.EDITOR))) {
            throw new HaveNoEnoughRightsException("You have no enough rights on this form.");
        }

        return Optional.of(elementOnFormCreateDto)
            .map(elementOnFormCreateMapper::map)
            .map(elementOnFormRepository::save)
            .map(elementOnFormReadMapper::map)
            .orElseThrow();
    }

    public Optional<ElementAttributeValueReadDto> updateAttributeValue(long formId, long attributeId,
        ElementAttributeValueCreateEditDto dto) {
        if (! (hasRole(formId, Role.OWNER) || hasRole(formId, Role.EDITOR))) {
            throw new HaveNoEnoughRightsException("You have no enough rights on this form.");
        }


        return elementAttributeValueRepository.findById(attributeId)
            .map(entity -> elementAttributeValueCreateEditMapper.map(dto, entity))
            .map(elementAttributeValueRepository::saveAndFlush)
            .map(elementAttributeValueReadMapper::map);
    }

    public ElementAttributeValueReadDto patchAttributeValue(long formId, long attributeId, JsonPatch patch) {
        if (! (hasRole(formId, Role.OWNER) || hasRole(formId, Role.EDITOR))) {
            throw new HaveNoEnoughRightsException("You have no enough rights on this form.");
        }

        ElementAttributeValueCreateEditDto elementAttributeValueCreateEditDto =
            elementAttributeValueRepository.findById(attributeId)
            .map(elementAttributeValueCreateEditMapper::map)
            .orElseThrow(() -> new ResourceNotFoundException("No attribute with ID = " + attributeId));

        try {
            JsonNode patched = patch.apply(
                objectMapper.convertValue(elementAttributeValueCreateEditDto, JsonNode.class));
            elementAttributeValueCreateEditDto = objectMapper.treeToValue(patched, ElementAttributeValueCreateEditDto.class);
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new JsonConvertationException(e.getMessage());
        }

        return updateAttributeValue(attributeId, formId, elementAttributeValueCreateEditDto).get();
    }

    public boolean removeElementFromForm(long formId, long elementId) {
        if (! (hasRole(formId, Role.OWNER) || hasRole(formId, Role.EDITOR))) {
            throw new HaveNoEnoughRightsException("You have no enough rights on this form.");
        }

        return elementOnFormRepository.findByFormIdAndElementId(formId, elementId)
            .map(entity -> {
                elementOnFormRepository.delete(entity);
                elementOnFormRepository.flush();
                return true;
            }).orElse(false);
    }

    public boolean hasAccess(long formId) {
        long userId = AuthService.getAuthInfo().getId();
        return formRepository.userHasAccessToForm(userId, formId) > 0;
    }

    public boolean hasRole(long formId, Role role) {
        long userId = AuthService.getAuthInfo().getId();
        return formRepository.userHasRoleOnForm(userId, formId, role) > 0;
    }
}
