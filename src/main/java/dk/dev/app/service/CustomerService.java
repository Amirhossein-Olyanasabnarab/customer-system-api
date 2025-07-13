package dk.dev.app.service;

import dk.dev.app.dao.CustomerDao;
import dk.dev.app.enums.CustomerType;
import dk.dev.app.exception.CustomerNotFoundException;
import dk.dev.app.model.Customer;
import dk.dev.app.model.LegalCustomer;
import dk.dev.app.model.RealCustomer;
import jakarta.annotation.PostConstruct;
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

//    @PostConstruct
//    public void init() {
//        addCustomer(RealCustomer.builder()
//                .id(1L)
//                .name("Johan")
//                .family("Kahn")
//                .phoneNumber("0049 29 22 22 22")
//                .type(CustomerType.REAL)
//                .nationality("German")
//                .build()
//        );
//
//        addCustomer(LegalCustomer.builder()
//                .id(2L)
//                .name("Johb")
//                .family("Davis")
//                .phoneNumber("40 444 444 444")
//                .type(CustomerType.LEGAL)
//                .industry("Tech")
//                .build()
//        );
//    }

    public Customer addCustomer(Customer customer) {
        return customerDao.save(customer);
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
       if (customerDao.existsById(id)) {
           updatedCustomer.setId(id);
           return customerDao.save(updatedCustomer);
       }
       return null;
    }

    public boolean deleteCustomer(Long id) {
        if (customerDao.existsById(id)) {
            customerDao.deleteById(id);
            return true;
        }
        return false;
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
