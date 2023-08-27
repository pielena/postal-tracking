package com.github.pielena.postal.tracking.service;

import com.github.pielena.postal.tracking.persistence.entity.Person;

import java.util.Optional;

public interface PersonService {

    Optional<Person> findByNameAndAddressDescription(String name, String addressDescription);

    Optional<Person> findByNameAndIndex(String name, int index);
}
