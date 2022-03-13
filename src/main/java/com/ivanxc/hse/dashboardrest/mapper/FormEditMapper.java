package com.ivanxc.hse.dashboardrest.mapper;

import com.ivanxc.hse.dashboardrest.dto.FormEditDto;
import com.ivanxc.hse.dashboardrest.entity.Form;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class FormEditMapper implements Mapper<FormEditDto, Form> {

    @Override
    public Form map(FormEditDto from) {
        Form form = new Form();
        copy(from, form);
        return form;
    }

    public FormEditDto map(Form form) {
        return new FormEditDto(
            form.getTitle()
        );
    }

    @Override
    public Form map(FormEditDto from, Form to) {
        copy(from, to);
        return to;
    }

    public void copy(FormEditDto from, Form to) {
        to.setTitle(from.getTitle());
        to.setUpdated(LocalDateTime.now());
    }
}
