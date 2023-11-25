package vn.com.devmaster.services.managematerial.domain;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "payment_method")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NAME", length = 250)
    private String name;

    @Lob
    @Column(name = "NOTES")
    private String notes;

    @Column(name = "UPDATED_BY")
    private Instant updatedBy;

    @Column(name = "UPDATED_DATE")
    private Instant updatedDate;

    @Column(name = "ISACTIVE")
    private Byte isactive;

    @Column(name = "PAYMENT_IMAGE")
    private String paymentImage;

}