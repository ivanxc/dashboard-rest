package com.ivanxc.hse.dashboardrest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class ElementAttributeValueCreateEditDto {
    Long elementOnFormId;
    Long attributeId;
    String attributeValue;

    @JsonCreator
    public ElementAttributeValueCreateEditDto(@JsonProperty("elementOnFormId") Long elementOnFormId,
        @JsonProperty("attributeId") Long attributeId,
        @JsonProperty("attributeValue") String attributeValue) {
        this.elementOnFormId = elementOnFormId;
        this.attributeId = attributeId;
        this.attributeValue = attributeValue;
    }
}
