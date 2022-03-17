package com.ivanxc.hse.dashboardrest.mapper;

import com.ivanxc.hse.dashboardrest.dto.ElementOnFormReadDto;
import com.ivanxc.hse.dashboardrest.entity.ElementOnForm;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ElementOnFormReadMapper implements Mapper<ElementOnForm, ElementOnFormReadDto> {

    private final ElementAttributeValueReadMapper elementAttributeValueReadMapper;

    @Override
    public ElementOnFormReadDto map(ElementOnForm from) {
        return new ElementOnFormReadDto(
            from.getId(),
            from.getForm().getId(),
            from.getElement(),
            from.getAttributeValues()
                .stream()
                .map(elementAttributeValueReadMapper::map)
                .collect(Collectors.toList())
        );
    }
}
