package com.github.pielena.postal.tracking.persistence.repository;

import com.github.pielena.postal.tracking.persistence.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {

    Optional<Person> findByNameAndAddress_Description(String name, String addressDescription);

    Optional<Person> findByNameAndAddress_PostOffice_Index(String name, int index);
}
