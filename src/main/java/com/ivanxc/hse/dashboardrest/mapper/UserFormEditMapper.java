package com.ivanxc.hse.dashboardrest.mapper;

import com.ivanxc.hse.dashboardrest.dto.UserFormCreateReadDto;
import com.ivanxc.hse.dashboardrest.dto.UserFormEditDto;
import com.ivanxc.hse.dashboardrest.entity.UserForm;
import org.springframework.stereotype.Component;

@Component
public class UserFormEditMapper implements Mapper<UserFormEditDto, UserForm> {

    @Override
    public UserForm map(UserFormEditDto from) {
        UserForm userForm = new UserForm();
        copy(from, userForm);
        return userForm;
    }

    public UserFormEditDto map(UserForm userForm) {
        return new UserFormEditDto(
            userForm.getRole()
        );
    }

    public UserFormEditDto map(UserFormCreateReadDto userFormCreateReadDto) {
        return new UserFormEditDto(
            userFormCreateReadDto.getUserRole()
        );
    }

    @Override
    public UserForm map(UserFormEditDto from, UserForm to) {
        copy(from, to);
        return to;
    }

    private void copy(UserFormEditDto from, UserForm to) {
        to.setRole(from.getUserRole());
    }
}
