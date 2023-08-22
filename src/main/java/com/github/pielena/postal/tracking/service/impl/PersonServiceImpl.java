package com.github.pielena.postal.tracking.service.impl;

import com.github.pielena.postal.tracking.entity.Person;
import com.github.pielena.postal.tracking.exception.S404ResourceNotFoundException;
import com.github.pielena.postal.tracking.repository.PersonRepository;
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
        return personRepository.findByNameAndAddress_Description(name, addressDescription);
    }

    @Override
    public Optional<Person> findByNameAndIndex(String name, int index) {
        return personRepository.findByNameAndAddress_PostOffice_Index(name, index);
    }
}
