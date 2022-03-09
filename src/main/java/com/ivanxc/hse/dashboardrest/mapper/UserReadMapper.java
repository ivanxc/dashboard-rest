package com.ivanxc.hse.dashboardrest.mapper;

import com.ivanxc.hse.dashboardrest.dto.UserReadDto;
import com.ivanxc.hse.dashboardrest.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserReadMapper implements Mapper<User, UserReadDto> {

    @Override
    public UserReadDto map(User from) {
        return new UserReadDto(
            from.getId(),
            from.getName(),
            from.getLastActivity()
        );
    }

    public UserReadDto map(User from, long id) {
        return new UserReadDto(
            id,
            from.getName(),
            from.getLastActivity()
        );
    }
}
