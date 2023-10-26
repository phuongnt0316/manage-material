package vn.com.devmaster.services.managematerial.domain;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "IDORDERS", length = 10)
    private String idorders;

    @Column(name = "ORDERS_DATE")
    private Instant ordersDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDCUSTOMER")
    private Customer idcustomer;

    @Column(name = "TOTAL_MONEY")
    private Double totalMoney;

    @Lob
    @Column(name = "NOTES")
    private String notes;

    @Column(name = "NAME_RECIVER", length = 250)
    private String nameReciver;

    @Column(name = "ADDRESS", length = 500)
    private String address;

    @Column(name = "PHONE", length = 50)
    private String phone;

}