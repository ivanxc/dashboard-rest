package com.ivanxc.hse.dashboardrest.repository;

import com.ivanxc.hse.dashboardrest.entity.Form;
import com.ivanxc.hse.dashboardrest.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FormRepository extends JpaRepository<Form, Long> {
    @Query("select count(uf) from UserForm  uf"
        + " where uf.id.form.id = :formId and uf.id.user.id = :userId"
        + " and uf.role = :role")
    long userHasRoleOnForm(long userId, long formId, Role role);

    @Query("select count(uf) from UserForm  uf"
        + " where uf.id.form.id = :formId and uf.id.user.id = :userId")
    long userHasAccessToForm(long userId, long formId);
}
