package vn.com.devmaster.services.managematerial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.com.devmaster.services.managematerial.domain.Customer;
import vn.com.devmaster.services.managematerial.domain.Product;
import vn.com.devmaster.services.managematerial.untils.Sql;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    @Query(value = Sql.CUSTOMER_LOGIN,nativeQuery = true)
    List<Customer> getCustomerByID(String user, String pass);
}
