package com.ivanxc.hse.dashboardrest.mapper;

import com.ivanxc.hse.dashboardrest.dto.UserCreateEditDto;
import com.ivanxc.hse.dashboardrest.entity.User;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User>{

    @Override
    public User map(UserCreateEditDto from) {
        User user = new User();
        copy(from, user);
        return user;
    }

    public UserCreateEditDto map(User user) {
        return new UserCreateEditDto(
            user.getName(),
            user.getPassword()
        );
    }

    @Override
    public User map(UserCreateEditDto from, User to) {
        copy(from, to);
        return to;
    }

    private void copy(UserCreateEditDto from, User to) {
        to.setName(from.getName());
        to.setPassword(from.getPassword());
        to.setLastActivity(LocalDateTime.now());
    }
}
