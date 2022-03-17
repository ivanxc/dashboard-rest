package com.ivanxc.hse.dashboardrest.mapper;

import com.ivanxc.hse.dashboardrest.dto.ElementAttributeValueCreateEditDto;
import com.ivanxc.hse.dashboardrest.entity.ElementAttribute;
import com.ivanxc.hse.dashboardrest.entity.ElementAttributeValue;
import com.ivanxc.hse.dashboardrest.entity.ElementOnForm;
import com.ivanxc.hse.dashboardrest.repository.ElementAttributeRepository;
import com.ivanxc.hse.dashboardrest.repository.ElementOnFormRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ElementAttributeValueCreateEditMapper
    implements Mapper<ElementAttributeValueCreateEditDto, ElementAttributeValue> {

    private final ElementOnFormRepository elementOnFormRepository;
    private final ElementAttributeRepository elementAttributeRepository;

    @Override
    public ElementAttributeValue map(ElementAttributeValueCreateEditDto from) {
        ElementAttributeValue elementAttributeValue = new ElementAttributeValue();
        copy(from, elementAttributeValue);
        return elementAttributeValue;
    }

    public ElementAttributeValueCreateEditDto map(ElementAttributeValue elementAttributeValue) {
        return new ElementAttributeValueCreateEditDto(
            elementAttributeValue.getElementOnForm().getId(),
            elementAttributeValue.getElementAttribute().getId(),
            elementAttributeValue.getValue()
        );
    }

    @Override
    public ElementAttributeValue map(ElementAttributeValueCreateEditDto from,
        ElementAttributeValue to) {
        copy(from, to);
        return to;
    }

    public void copy(ElementAttributeValueCreateEditDto from, ElementAttributeValue to) {
        to.setElementOnForm(getElementOnForm(from.getElementOnFormId()));
        to.setElementAttribute(getElementAttribute(from.getAttributeId()));
        to.setValue(from.getAttributeValue());
    }

    private ElementOnForm getElementOnForm(Long id) {
        return Optional.ofNullable(id)
            .flatMap(elementOnFormRepository::findById)
            .orElse(null);
    }

    private ElementAttribute getElementAttribute(Long id) {
        return Optional.ofNullable(id)
            .flatMap(elementAttributeRepository::findById)
            .orElse(null);
    }
}
