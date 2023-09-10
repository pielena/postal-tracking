package com.github.pielena.postal.tracking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Schema(description = "Operation searching by different parameters (all of them are optional)")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class OperationSearchDtoRq {

    @Schema(description = "item id", example = "7af49324-d3a3-4550-9448-38f00103565b")
    private String itemId;

    @Schema(description = "index", example = "753614")
    @Range(min = 0, max = 999999, message = "index must be greater than zero and can't be over 6 digits")
    private Integer index;

    @Schema(description = "state", example = "arrived")
    private String state;

    @Schema(description = "whether the post office is a destination or not", example = "false")
    private Boolean isDestination;

    @Schema(description = "lower boundary of the time period (including this date)", example = "24-08-2023")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateFrom;

    @Schema(description = "upper limit of the time period (including this date)", example = "30-08-2023")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateTo;

    @Schema(description = "page index for pagination, default value = 1", example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
    private int pageIndex = 1;

    @Schema(description = "page size for pagination, default value = 10", example = "10",
            accessMode = Schema.AccessMode.READ_ONLY)
    private int pageSize = 10;
}
