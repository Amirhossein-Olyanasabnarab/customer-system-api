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
public class RealCustomerDto extends CustomerDto {

    @Schema(description = "Nationality of the customer", example = "British")
    private String nationality;

    public RealCustomerDto() {
        this.setType(CustomerType.REAL);
    }
}
