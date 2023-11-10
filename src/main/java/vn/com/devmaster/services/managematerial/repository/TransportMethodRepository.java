package vn.com.devmaster.services.managematerial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.com.devmaster.services.managematerial.domain.ProductImage;
import vn.com.devmaster.services.managematerial.domain.TransportMethod;

import java.util.List;

public interface TransportMethodRepository extends JpaRepository<TransportMethod, Integer> {
    @Query(value = "select * from transport_method s where s.ISACTIVE=1",nativeQuery = true)
    List<TransportMethod> getTransport();


}