package com.ivanxc.hse.dashboardrest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "element_attribute_value")
public class ElementAttributeValue {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "element_on_form_id")
    private ElementOnForm elementOnForm;

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private ElementAttribute elementAttribute;

    @Column(name = "attribute_value")
    private String value;
}
