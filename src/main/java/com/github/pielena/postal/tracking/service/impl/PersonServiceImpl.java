package com.github.pielena.postal.tracking.service.impl;

import com.github.pielena.postal.tracking.persistence.entity.Person;
import com.github.pielena.postal.tracking.persistence.repository.PersonRepository;
import com.github.pielena.postal.tracking.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Override
    public Optional<Person> findByNameAndAddressDescription(String name, String addressDescription) {
        return personRepository.findByNameAndAddressDescription(name, addressDescription);
    }

    @Override
    public Optional<Person> findByNameAndIndex(String name, int index) {
        return personRepository.findByNameAndIndex(name, index);
    }
}
