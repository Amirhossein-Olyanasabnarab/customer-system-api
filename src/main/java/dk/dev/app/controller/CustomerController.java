package dk.dev.app.controller;

import dk.dev.app.dto.CustomerDto;
import dk.dev.app.dto.LegalCustomerDto;
import dk.dev.app.dto.RealCustomerDto;
import dk.dev.app.exception.CustomerNotFoundException;
import dk.dev.app.facade.CustomerFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer was found",
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
                                                    + "\"nationality\": \"British\""
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
                    )),
            @ApiResponse(responseCode = "404", description = "Customer was not found",
                    content = @Content(
                            schema = @Schema(type = "string"),
                            mediaType = "text/plain"
                    )
            ),
    })
    public ResponseEntity<?> getCustomerById(@PathVariable("id") Long id) {
        try {
            CustomerDto customer = customerFacade.getCustomerById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(customer);
        } catch (CustomerNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(exception.getMessage());
        }
    }

    @Operation(summary = "Get customers by name", description = "Retrieve a list of customers by their name")
    @GetMapping("/name/{name}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(
                                            oneOf = {
                                                    RealCustomerDto.class,
                                                    LegalCustomerDto.class
                                            }
                                    )
                            ),
                            examples = {
                                    @ExampleObject(
                                            name = "Real Customer Example",
                                            value = "{"
                                                    + "\"name\": \"John\","
                                                    + "\"family\": \"Doe\","
                                                    + "\"phoneNumber\": \"+1234567890\","
                                                    + "\"type\": \"REAL\","
                                                    + "\"nationality\": \"British\""
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
            ),
            @ApiResponse(responseCode = "404",
                    description = "No customers found with the given name", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = {
                            @ExampleObject(
                                    name = "Real Customer Example",
                                    value = "{"
                                            + "\"name\": \"John\","
                                            + "\"family\": \"Doe\","
                                            + "\"phoneNumber\": \"+1234567890\","
                                            + "\"type\": \"REAL\","
                                            + "\"nationality\": \"British\""
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
    })
    public ResponseEntity<?> getCustomersByName(@PathVariable("name") String name) {
        List<CustomerDto> customers = customerFacade.getCustomerByName(name);
        if (customers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Customer was not found");
        } else
            return ResponseEntity.status(HttpStatus.OK)
                    .body(customers);
    }

    @Operation(summary = "Delete a customer", description = "Remove a customer from the customer system")
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Remove a customer from customer system"),
            @ApiResponse(responseCode = "204", description = "Customer not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
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
    })
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") Long id) {
        boolean success = customerFacade.deleteCustomer(id);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Customer deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new dk.dev.app.dto.ErrorResponse(
                            HttpStatus.NOT_FOUND.value(),
                            "Customer was not found"
                    ));
        }
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

    @Operation(summary = "Update an existing customer", description = "Update the details of an existing customer")
    @PutMapping("/{id}")
    public CustomerDto updateCustomer(@PathVariable Long id, @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Updated customer object",
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
    ) @RequestBody CustomerDto customerDto) {
        return customerFacade.updateCustomer(id, customerDto);
    }
}
