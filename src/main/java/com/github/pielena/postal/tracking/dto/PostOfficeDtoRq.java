package com.github.pielena.postal.tracking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PostOfficeDtoRq {
    private int index;
    private String name;
    private String address;
}
