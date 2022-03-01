package com.ivanxc.hse.dashboardrest.repository;

import com.ivanxc.hse.dashboardrest.entity.ElementOnForm;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ElementOnFormRepository extends JpaRepository<ElementOnForm, Long> {
    @Query("select eof from ElementOnForm eof where eof.form.id = :formId")
    List<ElementOnForm> findAllByFormId(long formId);

    @Query("select eof from ElementOnForm eof where eof.form.id = :formId and eof.id = :elementId")
    Optional<ElementOnForm> findByFormIdAndElementId(long formId, long elementId);
}
