package vn.com.devmaster.services.managematerial.domain;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product_received")
public class ProductReceived {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PRODUCT")
    private Product idProduct;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "RECEIVED_DATE")
    private Instant receivedDate;

    @Column(name = "RECEIVED_BY")
    private Integer receivedBy;

}