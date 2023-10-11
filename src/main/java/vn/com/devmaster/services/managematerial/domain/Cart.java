package vn.com.devmaster.services.managematerial.domain;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "id_customer", nullable = false)
    private Integer idCustomer;

    @Column(name = "id_product", nullable = false)
    private Integer idProduct;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

}