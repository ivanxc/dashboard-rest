package com.ivanxc.hse.dashboardrest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.ivanxc.hse.dashboardrest.dto.FormReadDto;
import com.ivanxc.hse.dashboardrest.dto.UserCreateEditDto;
import com.ivanxc.hse.dashboardrest.dto.UserReadDto;
import com.ivanxc.hse.dashboardrest.entity.User;
import com.ivanxc.hse.dashboardrest.exception.JsonConvertationException;
import com.ivanxc.hse.dashboardrest.exception.ResourceNotFoundException;
import com.ivanxc.hse.dashboardrest.exception.UserAlreadyExistsException;
import com.ivanxc.hse.dashboardrest.mapper.FormReadMapper;
import com.ivanxc.hse.dashboardrest.mapper.UserCreateEditMapper;
import com.ivanxc.hse.dashboardrest.mapper.UserReadMapper;
import com.ivanxc.hse.dashboardrest.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final FormReadMapper formReadMapper;
    private final UserCreateEditMapper userCreateEditMapper;
    private ObjectMapper objectMapper = new ObjectMapper();

    public UserReadDto createUser(UserCreateEditDto userCreateEditDto) {
        if (userRepository.findByName(userCreateEditDto.getName()).isPresent()) {
            throw new UserAlreadyExistsException("User with login " +
                userCreateEditDto.getName() + " already exists");
        }

        return Optional.of(userCreateEditDto)
            .map(userCreateEditMapper::map)
            .map(userRepository::save)
            .map(userReadMapper::map)
            .orElseThrow();
    }

    public Optional<User> getByName(String name) {
        return userRepository.findByName(name);
    }

    public UserReadDto getUserInfo() {
        long userId = AuthService.getAuthInfo().getId();

        return userRepository.findById(userId)
            .map(userReadMapper::map)
            .orElseThrow();
    }

    public List<FormReadDto> getUserForms() {
        long userId = AuthService.getAuthInfo().getId();

        return userRepository.findUserForms(userId)
            .stream()
            .map(formReadMapper::map)
            .collect(Collectors.toList());
    }

    public Optional<UserReadDto> update(UserCreateEditDto userDto) {
        long userId = AuthService.getAuthInfo().getId();

        return userRepository.findById(userId)
            .map(entity -> userCreateEditMapper.map(userDto, entity))
            .map(userRepository::saveAndFlush)
            .map(userReadMapper::map);
    }

    public UserReadDto patch(JsonPatch patch) {
        long userId = AuthService.getAuthInfo().getId();

        UserCreateEditDto userCreateEditDto = userRepository.findById(userId)
            .map(userCreateEditMapper::map)
            .orElseThrow(() -> new ResourceNotFoundException("No user with ID = " + userId));

        try {
            JsonNode patched = patch.apply(
                objectMapper.convertValue(userCreateEditDto, JsonNode.class));
            userCreateEditDto = objectMapper.treeToValue(patched, UserCreateEditDto.class);
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new JsonConvertationException(e.getMessage());
        }

        return update(userCreateEditDto).get();
    }

    public boolean deleteUser() {
        long userId = AuthService.getAuthInfo().getId();

        return userRepository.findById(userId)
            .map(entity -> {
                userRepository.delete(entity);
                userRepository.flush();
                return true;
            }).orElse(false);
    }

}