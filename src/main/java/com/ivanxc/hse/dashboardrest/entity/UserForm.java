package com.ivanxc.hse.dashboardrest.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "user_form")
public class UserForm {
    @EmbeddedId
    private UserFormId id;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role_in_form")
    private Role role;
}
