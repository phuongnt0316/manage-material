package vn.com.devmaster.services.managematerial.domain;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders_transport")
public class OrdersTransport {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDORD")
    private Order idord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDTRANSPORT")
    private TransportMethod idtransport;

    @Column(name = "TOTAL")
    private Integer total;

    @Column(name = "NOTES")
    private Integer notes;

}