package com.aula126.project.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aula126.project.models.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    
    @Query(value = "SELECT * FROM contacts c WHERE c.u_id = :u_id", nativeQuery = true)
    List<Contact> findByUser(@Param("u_id") long id);

    @Query(value = "SELECT * FROM contacts c WHERE c.g_id = :g_id", nativeQuery = true)
    List<Contact> findByGroup(@Param("g_id") long id);

    Contact findById(long id);

    @Transactional
    void deleteByUserId(long userId);
}
