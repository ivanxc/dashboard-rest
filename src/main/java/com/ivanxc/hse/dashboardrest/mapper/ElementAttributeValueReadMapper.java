package com.ivanxc.hse.dashboardrest.mapper;

import com.ivanxc.hse.dashboardrest.dto.ElementAttributeValueReadDto;
import com.ivanxc.hse.dashboardrest.entity.ElementAttributeValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ElementAttributeValueReadMapper implements Mapper<ElementAttributeValue, ElementAttributeValueReadDto>{

    private final ElementAttributeReadMapper elementAttributeReadMapper;

    @Override
    public ElementAttributeValueReadDto map(ElementAttributeValue from) {
        return new ElementAttributeValueReadDto(
            from.getId(),
            from.getElementOnForm().getId(),
            elementAttributeReadMapper.map(from.getElementAttribute()),
            from.getValue()
        );
    }
}
