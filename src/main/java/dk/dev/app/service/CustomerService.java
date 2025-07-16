package dk.dev.app.service;

import dk.dev.app.dao.CustomerDao;
import dk.dev.app.exception.CustomerNotFoundException;
import dk.dev.app.exception.DuplicatedCustomerException;
import dk.dev.app.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    @Autowired
    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }


    public Customer addCustomer(Customer customer) {
        if (customerDao.existsByNameIgnoreCaseAndFamilyIgnoreCase(customer.getName(), customer.getFamily())) {
            throw new DuplicatedCustomerException("Customer with full name " + customer.getName() + " " + customer.getFamily() + " is already exists");
        }
        return customerDao.save(customer);
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        if (customerDao.existsById(id)) {
            updatedCustomer.setId(id);
            return customerDao.save(updatedCustomer);
        }
        return null;
    }

    public void deleteCustomer(Long id) {
        if (customerDao.existsById(id)) {
            customerDao.deleteById(id);
        }else
            throw new CustomerNotFoundException("Customer with id " + id + " not found");
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerDao.findById(id);
    }

    public List<Customer> getAllCustomers() {
        return customerDao.findAll();
    }

    public List<Customer> findByName(String name) {
        List<Customer> customers = customerDao.findByNameIgnoreCase(name);
        if (customers.isEmpty()) {
            throw new CustomerNotFoundException("Customer was not found");
        }
        return customers;
    }
}
