package com.github.pielena.postal.tracking.service;

import com.github.pielena.postal.tracking.entity.Person;

public interface PersonService {

    Person findByNameAndAddressDescription(String name, String addressDescription);

    Person findByNameAndIndex(String name, int index);
}
