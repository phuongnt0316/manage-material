package vn.com.devmaster.services.managematerial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.devmaster.services.managematerial.domain.ProductReceived;

public interface ProductReceivedRepository extends JpaRepository<ProductReceived, Integer> {
}