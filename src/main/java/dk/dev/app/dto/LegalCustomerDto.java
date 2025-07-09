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
public class LegalCustomerDto extends CustomerDto {
    private String industry;

    public LegalCustomerDto() {
        this.setType(CustomerType.LEGAL);
    }
}
