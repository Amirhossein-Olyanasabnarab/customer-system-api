package dk.dev.app.dao;

import dk.dev.app.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    Customer save(Customer customer);
    void deleteById(Long id);
    Optional<Customer> findById(Long id);
    List<Customer> findAll();
    boolean existsById(Long id);
    List<Customer> findByNameIgnoreCase(String name);
    boolean existByNameIgnoreCaseAndFamilyIgnoreCase(String name, String family);
}
