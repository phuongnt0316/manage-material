package vn.com.devmaster.services.managematerial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.devmaster.services.managematerial.domain.Cart;
@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {
}
