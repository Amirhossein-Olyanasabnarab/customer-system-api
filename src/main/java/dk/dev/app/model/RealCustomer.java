package dk.dev.app.model;

import dk.dev.app.enums.CustomerType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@Entity
@Table(name = "reak_customer")
@PrimaryKeyJoinColumn(name = "id")
@DiscriminatorValue("REAL")
public class RealCustomer extends Customer {
    private String nationality;

    public RealCustomer(){
        this.setType(CustomerType.REAL);
    }
}
