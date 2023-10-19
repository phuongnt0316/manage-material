package vn.com.devmaster.services.managematerial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.com.devmaster.services.managematerial.DTO.CartDto;
import vn.com.devmaster.services.managematerial.domain.Cart;
import vn.com.devmaster.services.managematerial.untils.Sql;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {

    @Query(value = Sql.CUSTOMER_BY_ID,nativeQuery = true)
    List<Cart> getCartByIdCustomer(Integer idcustomer);
}
