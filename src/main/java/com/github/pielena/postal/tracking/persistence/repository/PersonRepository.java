package com.github.pielena.postal.tracking.persistence.repository;

import com.github.pielena.postal.tracking.persistence.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {

    @Query("SELECT p FROM Person p WHERE p.name = :name AND p.address.description = :addressDescription")
    Optional<Person> findByNameAndAddressDescription(String name, String addressDescription);

    @Query("SELECT p FROM Person p WHERE p.name = :name AND p.address.postOffice.index = :index")
    Optional<Person> findByNameAndIndex(String name, int index);
}
