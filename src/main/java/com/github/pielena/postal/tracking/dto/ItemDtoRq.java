package com.github.pielena.postal.tracking.dto;

import com.github.pielena.postal.tracking.enums.ItemType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "name must be specified")
    @Length(max = 100, message = "name can't be over 100 characters")
    private String senderName;

    @Schema(description = "destination post office index", example = "123456")
    @Range(min = 0, max = 999999, message = "index must be greater than zero and can't be over 6 digits")
    private int recipientIndex;

    @Schema(description = "destination address description", example = "Spring Street, 22")
    @NotBlank(message = "address must be specified")
    @Length(max = 255, message = "address can't be over 255 characters")
    private String recipientAddress;

    @Schema(description = "recipient name", example = "Anna Volkova")
    @NotBlank(message = "name must be specified")
    @Length(max = 100, message = "name can't be over 100 characters")
    private String recipientName;
}
