package vn.com.devmaster.services.managematerial.domain;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders_payment")
public class OrdersPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "IDORD")
    private Integer idord;


    @Column(name = "IDPAYMENT")
    private Integer idpayment;

    @Column(name = "TOTAL")
    private Integer total;

    @Column(name = "NOTES")
    private Integer notes;

    @Column(name = "STATUS", length = 50)
    private String status;

}