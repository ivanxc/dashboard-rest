package com.ivanxc.hse.dashboardrest.mapper;

import com.ivanxc.hse.dashboardrest.dto.ElementOnFormCreateDto;
import com.ivanxc.hse.dashboardrest.entity.ElementDefinition;
import com.ivanxc.hse.dashboardrest.entity.ElementOnForm;
import com.ivanxc.hse.dashboardrest.entity.Form;
import com.ivanxc.hse.dashboardrest.repository.ElementDefinitionRepository;
import com.ivanxc.hse.dashboardrest.repository.FormRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ElementOnFormCreateMapper implements Mapper<ElementOnFormCreateDto, ElementOnForm> {

    private final ElementDefinitionRepository elementDefinitionRepository;
    private final FormRepository formRepository;

    @Override
    public ElementOnForm map(ElementOnFormCreateDto from) {
        ElementOnForm elementOnForm = new ElementOnForm();
        copy(from, elementOnForm);
        return elementOnForm;
    }

    @Override
    public ElementOnForm map(ElementOnFormCreateDto from, ElementOnForm to) {
        copy(from, to);
        return to;
    }

    public void copy(ElementOnFormCreateDto from, ElementOnForm to) {
        to.setElement(getElementDefinition(from.getElementDefinitionId()));
        to.setForm(getForm(from.getFormId()));
    }

    private ElementDefinition getElementDefinition(Long elementDefinitionId) {
            return Optional.ofNullable(elementDefinitionId)
                .flatMap(elementDefinitionRepository::findById)
                .orElse(null);
    }

    private Form getForm(Long formId) {
        return Optional.ofNullable(formId)
            .flatMap(formRepository::findById)
            .orElse(null);
    }
}
