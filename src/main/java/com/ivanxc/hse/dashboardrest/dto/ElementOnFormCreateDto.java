package com.ivanxc.hse.dashboardrest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class ElementOnFormCreateDto {
    Long elementDefinitionId;
    Long formId;

    @JsonCreator
    public ElementOnFormCreateDto(@JsonProperty("elementDefinitionId") Long elementDefinitionId,
        @JsonProperty("formId") Long formId) {
        this.elementDefinitionId = elementDefinitionId;
        this.formId = formId;
    }
}
