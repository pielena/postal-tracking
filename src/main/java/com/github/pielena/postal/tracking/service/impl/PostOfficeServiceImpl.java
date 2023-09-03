package com.github.pielena.postal.tracking.service.impl;

import com.github.pielena.postal.tracking.persistence.entity.PostOffice;
import com.github.pielena.postal.tracking.persistence.repository.PostOfficeRepository;
import com.github.pielena.postal.tracking.service.PostOfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostOfficeServiceImpl implements PostOfficeService {

    private final PostOfficeRepository postOfficeRepository;

    @Override
    public Optional<PostOffice> findByIndex(int index) {
        return postOfficeRepository.findByIndex(index);
    }
}
