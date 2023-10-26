package vn.com.devmaster.services.managematerial.domain;

import lombok.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders_details")
public class OrdersDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "IDORD")
    private Integer idord;

    @Column(name = "IDPRODUCT")
    private Integer idproduct;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "QTY")
    private Integer qty;

}