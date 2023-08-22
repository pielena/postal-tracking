package com.github.pielena.postal.tracking.service.impl;

import com.github.pielena.postal.tracking.entity.Person;
import com.github.pielena.postal.tracking.exception.S404ResourceNotFoundException;
import com.github.pielena.postal.tracking.repository.PersonRepository;
import com.github.pielena.postal.tracking.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Override
    public Person findByNameAndAddressDescription(String name, String addressDescription) {
        return personRepository.findByNameAndAddress_Description(name, addressDescription)
                .orElseThrow(() -> new S404ResourceNotFoundException(Person.class, name + " and address: " + addressDescription));
    }

    @Override
    public Person findByNameAndIndex(String name, int index) {
        return personRepository.findByNameAndAddress_PostOffice_Index(name, index)
                .orElseThrow(() -> new S404ResourceNotFoundException(Person.class, name + " and index: " + index));
    }
}
