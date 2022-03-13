package com.ivanxc.hse.dashboardrest.mapper;

import com.ivanxc.hse.dashboardrest.dto.FormReadDto;
import com.ivanxc.hse.dashboardrest.entity.Form;
import org.springframework.stereotype.Component;

@Component
public class FormReadMapper implements Mapper<Form, FormReadDto> {

    @Override
    public FormReadDto map(Form from) {
        return new FormReadDto(
            from.getId(),
            from.getTitle(),
            from.getType(),
            from.getCreated(),
            from.getUpdated()
        );
    }

    public FormReadDto map(Form from, long id) {
        return new FormReadDto(
            id,
            from.getTitle(),
            from.getType(),
            from.getCreated(),
            from.getUpdated()
        );
    }

}
