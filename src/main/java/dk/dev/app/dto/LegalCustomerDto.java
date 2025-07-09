package dk.dev.app.dto;

import dk.dev.app.enums.CustomerType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public class LegalCustomerDto extends CustomerDto {

    @Schema(description = "Industry of the customer", example = "Tech")
    private String industry;

    public LegalCustomerDto() {
        this.setType(CustomerType.LEGAL);
    }
}
