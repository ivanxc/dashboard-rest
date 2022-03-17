package com.ivanxc.hse.dashboardrest.mapper;

import com.ivanxc.hse.dashboardrest.dto.ElementAttributeReadDto;
import com.ivanxc.hse.dashboardrest.entity.ElementAttribute;
import org.springframework.stereotype.Component;

@Component
public class ElementAttributeReadMapper implements Mapper<ElementAttribute, ElementAttributeReadDto> {

    @Override
    public ElementAttributeReadDto map(ElementAttribute from) {
        return new ElementAttributeReadDto(
            from.getId(),
            from.getName()
        );
    }
}
