package com.ivanxc.hse.dashboardrest.mapper;

import com.ivanxc.hse.dashboardrest.dto.FormCreateDto;
import com.ivanxc.hse.dashboardrest.entity.Form;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class FormCreateMapper implements Mapper<FormCreateDto, Form> {

    @Override
    public Form map(FormCreateDto from) {
        Form form = new Form();
        copy(from, form);
        return form;
    }

    @Override
    public Form map(FormCreateDto from, Form to) {
        copy(from, to);
        return to;
    }

    public void copy(FormCreateDto from, Form to) {
        to.setTitle(from.getTitle());
        to.setType(from.getType());
        to.setUpdated(LocalDateTime.now());
        to.setCreated(LocalDateTime.now());
    }
}
