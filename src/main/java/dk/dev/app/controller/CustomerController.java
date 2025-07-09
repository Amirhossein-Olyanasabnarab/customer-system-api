package dk.dev.app.controller;

import dk.dev.app.dto.CustomerDto;
import dk.dev.app.dto.LegalCustomerDto;
import dk.dev.app.dto.RealCustomerDto;
import dk.dev.app.facade.CustomerFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerFacade customerFacade;
    @Autowired
    public CustomerController(CustomerFacade customerFacade) {
        this.customerFacade = customerFacade;
    }

    @Operation(summary = "Get all customers", description = "Retrieve a list of all customers")
    @GetMapping
    public List<CustomerDto> getAllCustomers() {
        return customerFacade.getAllCustomers();
    }
    @Operation(summary = "Get a customer by id", description = "Retrieve a customer")
    @GetMapping("/{id}")
    public CustomerDto getCustomerById(@PathVariable("id") Long id) {
        return customerFacade.getCustomerById(id);
    }

    @Operation(summary = "Get customers by name", description = "Retrieve a list of customers by their name")
    @GetMapping("/name/{name}")
    public List<CustomerDto> getCustomersByName(@PathVariable("name") String name) {
        return customerFacade.getCustomerByName(name);
    }

    @Operation(summary = "Delete a customer", description = "Remove a customer from the customer system")
    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable("id") Long id) {
        boolean success = customerFacade.deleteCustomer(id);
        if (success) {
            return "Customer with id " + id + " successfully deleted";
        }else
            return "Customer with id " + id + " could not be deleted";
    }

    @Operation(summary = "Add a new customer", description = "Create a new customer")
    @PostMapping
    public CustomerDto addCustomer(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Customer object to be added",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            oneOf = {
                                    RealCustomerDto.class,
                                    LegalCustomerDto.class
                            }
                    ),
                    examples = {
                            @ExampleObject(
                                    name = "Real Customer Example",
                                    value = "{"
                                            + "\"name\": \"John\","
                                            + "\"family\": \"Doe\","
                                            + "\"phoneNumber\": \"+1234567890\","
                                            + "\"type\": \"REAL\","
                                            + "\"nationality\": \"UK\""
                                            + "}"
                            ),
                            @ExampleObject(
                                    name = "Legal Customer Example",
                                    value = "{"
                                            + "\"name\": \"John\","
                                            + "\"family\": \"Doe\","
                                            + "\"phoneNumber\": \"+1234567890\","
                                            + "\"type\": \"LEGAL\","
                                            + "\"industry\": \"Tech\""
                                            + "}"
                            )
                    }
            )
    )
            @RequestBody CustomerDto customerDto) {
        return customerFacade.addCustomer(customerDto);
    }
}
