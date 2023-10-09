package vn.com.devmaster.services.managematerial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.devmaster.services.managematerial.domain.Product;

public interface ProductRepository extends JpaRepository<Product,Integer> {

}
