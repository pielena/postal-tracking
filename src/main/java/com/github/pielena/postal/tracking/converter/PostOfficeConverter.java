package com.github.pielena.postal.tracking.converter;

import com.github.pielena.postal.tracking.dto.PostOfficeDtoRq;
import com.github.pielena.postal.tracking.entity.PostOffice;
import org.springframework.stereotype.Component;

@Component
public class PostOfficeConverter {

    public PostOffice dtoToEntity(PostOfficeDtoRq postOfficeDtoRq) {
        return PostOffice.builder()
                .index(postOfficeDtoRq.getIndex())
                .name(postOfficeDtoRq.getName())
                .build();
    }
}
