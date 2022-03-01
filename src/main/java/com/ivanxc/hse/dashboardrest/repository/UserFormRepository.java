package com.ivanxc.hse.dashboardrest.repository;

import com.ivanxc.hse.dashboardrest.entity.UserForm;
import com.ivanxc.hse.dashboardrest.entity.UserFormId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserFormRepository extends JpaRepository<UserForm, UserFormId> {
    @Query("select uf from UserForm uf where uf.id.form.id = :formId")
    List<UserForm> getUsersOfForm(long formId);

    @Query("select uf from UserForm uf where uf.id.user.id = :userId and uf.id.form.id = :formId")
    Optional<UserForm> findByUserIdAndFormId(long userId, long formId);
}
