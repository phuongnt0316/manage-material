package vn.com.devmaster.services.managematerial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.devmaster.services.managematerial.domain.OrdersTransport;

public interface OrdersTransportRepository extends JpaRepository<OrdersTransport, Integer> {
}