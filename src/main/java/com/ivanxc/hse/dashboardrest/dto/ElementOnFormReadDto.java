package com.ivanxc.hse.dashboardrest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ivanxc.hse.dashboardrest.entity.ElementDefinition;
import java.util.List;
import lombok.Value;

@Value
public class ElementOnFormReadDto {
    Long id;
    Long formId;
    ElementDefinition elementDefinition;
    List<ElementAttributeValueReadDto> attrubutes;

    @JsonCreator
    public ElementOnFormReadDto(@JsonProperty("id") Long id,
        @JsonProperty("formId") Long formId,
        @JsonProperty("elementDefinition") ElementDefinition elementDefinition,
        @JsonProperty("attributes") List<ElementAttributeValueReadDto> attrubutes) {
        this.id = id;
        this.formId = formId;
        this.elementDefinition = elementDefinition;
        this.attrubutes = attrubutes;
    }
}
