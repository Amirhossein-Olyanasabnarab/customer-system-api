package dk.dev.app.dao.impl;

import dk.dev.app.dao.CustomerDao;
import dk.dev.app.enums.CustomerType;
import dk.dev.app.model.Customer;
import dk.dev.app.model.LegalCustomer;
import dk.dev.app.model.RealCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Primary
public class CustomerJdbcDao implements CustomerDao {

    private final JdbcTemplate jdbc;
    @Autowired
    public CustomerJdbcDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private Customer insert(Customer customer) {
        String sql = "INSERT INTO customer (name, family, phone_number, type) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(Connection ->{
            PreparedStatement ps = Connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getFamily());
            ps.setString(3, customer.getPhoneNumber());
            ps.setString(4, customer.getType().name());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        customer.setId(id);

        if(customer instanceof RealCustomer realCustomer) {
            String realCustomerSql = "INSERT INTO real_customer (id, nationality) VALUES (?, ?)";
            jdbc.update(realCustomerSql, id, realCustomer.getNationality());
        } else if (customer instanceof LegalCustomer legalCustomer) {
            String legalCustomerSql = "INSERT INTO legal_customer (id, industry) VALUES (?, ?)";
            jdbc.update(legalCustomerSql, id, legalCustomer.getIndustry());
        }

        return customer;
    }

    private Customer update(Customer customer) {
        String customerSql = "UPDATE customer SET name = ?, family = ?, phone_number = ?, type = ? WHERE id = ?";
        jdbc.update(customerSql, customer.getName(), customer.getFamily(),
                customer.getPhoneNumber(), customer.getType().name(), customer.getId());

        if (customer instanceof RealCustomer realCustomer) {
            String realCustomerSql = "UPDATE real_customer SET nationality = ? WHERE id = ?";
            jdbc.update(realCustomerSql, realCustomer.getNationality(), customer.getId());
        } else if (customer instanceof LegalCustomer legalCustomer) {
            String legalCustomerSql = "UPDATE legal_customer SET industry = ? WHERE id = ?";
            jdbc.update(legalCustomerSql, legalCustomer.getIndustry(), customer.getId());
        }

        return customer;
    }

    @Override
    public Customer save(Customer customer) {
        if(existsById(customer.getId())) {
            return update(customer);
        }else {
            return insert(customer);
        }
    }

    @Override
    public void deleteById(Long id) {
        String customerSql = "DELETE FROM customer WHERE id = ?";
        jdbc.update(customerSql, id);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        if(!existsById(id)) {
            return Optional.empty();
        }
        String customerSql = "SELECT * FROM customer WHERE id = ?";
        Map<String, Object> customerRow = jdbc.queryForMap(customerSql, id);

        CustomerType type = CustomerType.valueOf((String) customerRow.get("type"));
        Customer customer;

        if (type == CustomerType.REAL) {
            String realCustomerSql = "SELECT * FROM real_customer WHERE id = ?";
            Map<String, Object> realCustomerRow = jdbc.queryForMap(realCustomerSql, id);
            customer = RealCustomer.builder()
                    .id(id)
                    .name((String) customerRow.get("name"))
                    .family((String) customerRow.get("family"))
                    .phoneNumber((String) customerRow.get("phone_number"))
                    .type(type)
                    .nationality((String) realCustomerRow.get("nationality"))
                    .build();
        } else {
            String legalCustomerSql = "SELECT * FROM legal_customer WHERE id = ?";
            Map<String, Object> legalCustomerRow = jdbc.queryForMap(legalCustomerSql, id);
            customer = LegalCustomer.builder()
                    .id(id)
                    .name((String) customerRow.get("name"))
                    .family((String) customerRow.get("family"))
                    .phoneNumber((String) customerRow.get("phone_number"))
                    .type(type)
                    .industry((String) legalCustomerRow.get("fax"))
                    .build();
        }

        return Optional.of(customer);
    }

    @Override
    public List<Customer> findAll() {
        return List.of();
    }

    @Override
    public boolean existsById(Long id) {
        String customerSql = "SELECT COUNT(*) FROM customer WHERE id = ?";
        Integer count = jdbc.queryForObject(customerSql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public List<Customer> findByNameIgnoreCase(String name) {
        return List.of();
    }
}
