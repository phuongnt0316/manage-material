package vn.com.devmaster.services.managematerial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vn.com.devmaster.services.managematerial.domain.Customer;
import vn.com.devmaster.services.managematerial.untils.Sql;

import javax.transaction.Transactional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query(value = Sql.CUSTOMER_LOGIN, nativeQuery = true)
    Customer getCustomerByID(String user, String pass);

    @Query(value = Sql.CUSTOMER_BY_USER_NAME, nativeQuery = true)
    Customer getCustomerByUsername(String username);
    @Query(value = Sql.CUSTOMER_BY_EMAIL, nativeQuery = true)
    Customer getCustomerByEmail(String email);
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = Sql.UPDATE_PASSWORD_BY_EMAIL, nativeQuery = true)
    int changePassword(String email, String password);
}
