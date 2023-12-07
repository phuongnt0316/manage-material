package vn.com.devmaster.services.managematerial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.com.devmaster.services.managematerial.domain.ProductReceived;
import vn.com.devmaster.services.managematerial.projection.IInventory;
import vn.com.devmaster.services.managematerial.untils.Sql;

import java.util.List;

public interface ProductReceivedRepository extends JpaRepository<ProductReceived, Integer> {
    @Query(value = Sql.GET_RECEIVED, nativeQuery = true)
    List<IInventory> getAllReceived();

    @Query(value = Sql.GET_RECEIVED_BY_INVENTORY, nativeQuery = true)
    List<ProductReceived> getReceivedByInventory();
    @Query(value = Sql.GET_RECEIVED_BY_ID, nativeQuery = true)
    List<ProductReceived> getReceivedByID(int id);

}