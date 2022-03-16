package com.ivanxc.hse.dashboardrest.mapper;

import com.ivanxc.hse.dashboardrest.dto.UserFormCreateReadDto;
import com.ivanxc.hse.dashboardrest.entity.Form;
import com.ivanxc.hse.dashboardrest.entity.User;
import com.ivanxc.hse.dashboardrest.entity.UserForm;
import com.ivanxc.hse.dashboardrest.entity.UserFormId;
import com.ivanxc.hse.dashboardrest.repository.FormRepository;
import com.ivanxc.hse.dashboardrest.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFormCreateReadMapper implements Mapper<UserFormCreateReadDto, UserForm> {

    private final UserRepository userRepository;
    private final FormRepository formRepository;

    @Override
    public UserForm map(UserFormCreateReadDto from) {
        UserForm userForm = new UserForm();
        copy(from, userForm);
        return userForm;
    }

    public UserFormCreateReadDto map(UserForm from) {
        return new UserFormCreateReadDto(
            from.getId().getUser().getId(),
            from.getId().getForm().getId(),
            from.getRole()
        );
    }

    @Override
    public UserForm map(UserFormCreateReadDto from, UserForm to) {
        copy(from, to);
        return to;
    }

    private void copy(UserFormCreateReadDto from, UserForm to) {
        UserFormId userFormId = new UserFormId();
        userFormId.setForm(getForm(from.getFormId()));
        userFormId.setUser(getUser(from.getUserId()));
        to.setId(userFormId);
        to.setRole(from.getUserRole());
    }

    private User getUser(long userId) {
        return Optional.ofNullable(userId)
            .flatMap(userRepository::findById)
            .orElse(null);
    }

    private Form getForm(Long formId) {
        return Optional.ofNullable(formId)
            .flatMap(formRepository::findById)
            .orElse(null);
    }
}
