package dk.dev.app.dao.impl;

import dk.dev.app.dao.CustomerDao;
import dk.dev.app.model.Customer;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
@Primary
@Profile("jpa")
public interface CustomerJpaDao extends JpaRepository<Customer, Long>, CustomerDao {

}
