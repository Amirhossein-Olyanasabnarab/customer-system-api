package dk.dev.app.dto;

import dk.dev.app.enums.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public class RealCustomerDto extends CustomerDto {
    private String nationality;

    public RealCustomerDto() {
        this.setType(CustomerType.REAL);
    }
}
