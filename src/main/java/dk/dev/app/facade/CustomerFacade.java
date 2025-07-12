package dk.dev.app.facade;

import dk.dev.app.dto.CustomerDto;
import dk.dev.app.exception.CustomerNotFoundException;
import dk.dev.app.mapper.CustomerMapper;
import dk.dev.app.model.Customer;
import dk.dev.app.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerFacade {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;
    @Autowired
    public CustomerFacade(CustomerService customerService, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }

    public CustomerDto addCustomer(CustomerDto customerDto) {
        Customer entity = customerMapper.toEntity(customerDto);
        entity = customerService.addCustomer(entity);
        return customerMapper.toDto(entity);
    }

    public CustomerDto updateCustomer(Long id, CustomerDto updatedCustomerDto) {
        Customer entity = customerMapper.toEntity(updatedCustomerDto);
        entity = customerService.updateCustomer(id, entity);
        return entity != null ? this.customerMapper.toDto(entity) : null;
    }

    public boolean deleteCustomer(Long id) {
        return customerService.deleteCustomer(id);
    }
    public CustomerDto getCustomerById(Long id) throws CustomerNotFoundException {
        Optional<Customer> entity = customerService.getCustomerById(id);
        return entity.map(customerMapper::toDto)
                .orElseThrow(() ->new CustomerNotFoundException("Customer Not Found with id " + id));
    }
    public List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers()
                .stream()
                .map(customerMapper::toDto)
                .toList();
    }

    public List<CustomerDto> getCustomerByName(String name) {
        return customerService.findByName(name)
                .stream()
                .map(customerMapper::toDto)
                .toList();
    }
}
