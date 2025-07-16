package dk.dev.app.dao.impl;

import dk.dev.app.dao.CustomerDao;
import dk.dev.app.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class CustomerInMemoryDao implements CustomerDao {

    private final AtomicLong currentId = new AtomicLong(0);
    private final Map<Long, Customer> customers = new ConcurrentHashMap<>();

    @Override
    public Customer save(Customer customer) {
        if (!existsById(customer.getId())) {
            customer.setId(currentId.incrementAndGet());
        }
        customers.put(customer.getId(), customer);
        return customer;
    }

    @Override
    public void deleteById(Long id) {
        customers.remove(id);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        if (!existsById(id)) {
            return Optional.empty();
        }
        return Optional.of(customers.get(id));
    }

    @Override
    public List<Customer> findAll() {
        return customers.values()
                .stream().toList();
    }

    @Override
    public boolean existsById(Long id) {
        if (id == null) {
            return false;
        }else
            return customers.containsKey(id);
    }

    @Override
    public List<Customer> findByNameIgnoreCase(String name) {
        return customers.values()
                .stream()
                .filter(customer -> customer.getPhoneNumber() != null &&
                        customer.getName().equalsIgnoreCase(name))
                .toList();
    }

    @Override
    public boolean existByNameIgnoreCaseAndFamilyIgnoreCase(String name, String family) {
        return false;
    }
}
