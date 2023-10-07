package vn.com.devmaster.services.managematerial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.devmaster.services.managematerial.domain.Customer;

@Repository
public interface MaterialRepository extends JpaRepository<Customer, Integer> {
}
