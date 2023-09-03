package com.github.pielena.postal.tracking.service;

import com.github.pielena.postal.tracking.persistence.entity.PostOffice;

import java.util.Optional;

public interface PostOfficeService {

    Optional<PostOffice> findByIndex(int index);
}
