package com.github.pielena.postal.tracking.service.impl;

import com.github.pielena.postal.tracking.entity.PostOffice;
import com.github.pielena.postal.tracking.exception.S404ResourceNotFoundException;
import com.github.pielena.postal.tracking.repository.PostOfficeRepository;
import com.github.pielena.postal.tracking.service.PostOfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostOfficeServiceImpl implements PostOfficeService {

    private final PostOfficeRepository postOfficeRepository;

    @Override
    public PostOffice findByIndex(int index) {
        return postOfficeRepository.findByIndex(index)
                .orElseThrow(() -> new S404ResourceNotFoundException(PostOffice.class, index));
    }
}
