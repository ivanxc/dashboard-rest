package com.ivanxc.hse.dashboardrest.repository;

import com.ivanxc.hse.dashboardrest.entity.Form;
import com.ivanxc.hse.dashboardrest.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);

    @Query("select uf.id.form from UserForm uf where uf.id.user.id = :userId")
    List<Form> findUserForms(long userId);
}
