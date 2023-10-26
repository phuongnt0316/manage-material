package vn.com.devmaster.services.managematerial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.devmaster.services.managematerial.domain.OrdersDetail;

public interface OrdersDetailRepository extends JpaRepository<OrdersDetail, Integer> {
}