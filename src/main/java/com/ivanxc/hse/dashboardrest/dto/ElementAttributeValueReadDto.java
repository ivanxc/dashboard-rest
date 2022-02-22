package com.ivanxc.hse.dashboardrest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class ElementAttributeValueReadDto {
    Long id;
    Long elementOnFormId;
    ElementAttributeReadDto attributeDefinition;
    String attributeValue;

    @JsonCreator
    public ElementAttributeValueReadDto(@JsonProperty("id") Long id,
        @JsonProperty("elementOnFormId") Long elementOnFormId,
        @JsonProperty("attributeDefinition") ElementAttributeReadDto attributeDefinition,
        @JsonProperty("attributeValue") String attributeValue) {
        this.id = id;
        this.elementOnFormId = elementOnFormId;
        this.attributeDefinition = attributeDefinition;
        this.attributeValue = attributeValue;
    }
}
