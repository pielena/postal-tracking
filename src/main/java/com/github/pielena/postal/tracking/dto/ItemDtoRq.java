package com.github.pielena.postal.tracking.dto;

import com.github.pielena.postal.tracking.enums.ItemType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Schema(description = "Request Postal item DTO")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ItemDtoRq {

    @Schema(description = "postal item type", example = "LETTER")
    private ItemType type;

    @Schema(description = "sender post office index", example = "753614")
    @Range(min = 0, max = 999999, message = "index must be greater than zero and can't be over 6 digits")
    private int senderIndex;

    @Schema(description = "sender name", example = "Ivan Ivanov")
    @Length(min = 3, max = 100, message = "name cannot be less than 3 and more than 100 characters long")
    private String senderName;

    @Schema(description = "destination post office index", example = "123456")
    @Range(min = 0, max = 999999, message = "index must be greater than zero and can't be over 6 digits")
    private int recipientIndex;

    @Schema(description = "destination address description", example = "Spring Street, 22")
    @Length(min = 3, max = 255, message = "address cannot be less than 3 and more than 255 characters long")
    private String recipientAddress;

    @Schema(description = "recipient name", example = "Anna Volkova")
    @Length(min = 3, max = 100, message = "name cannot be less than 3 and more than 100 characters long")
    private String recipientName;
}
