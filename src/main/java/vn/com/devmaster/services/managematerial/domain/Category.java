package vn.com.devmaster.services.managematerial.domain;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "category")
public class Category {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "IDPARENT")
    private Integer idparent;

    @Column(name = "NAME", length = 500)
    private String name;

    @Lob
    @Column(name = "NOTES")
    private String notes;

    @Column(name = "IMAGE", length = 250)
    private String icon;

    @Column(name = "CREATED_DATE")
    private Instant createdDate;

    @Column(name = "UPDATED_DATE")
    private Instant updatedDate;

    @Column(name = "CREATED_BY", length = 50)
    private String createdBy;

    @Column(name = "UPDATED_BY", length = 50)
    private String updatedBy;

    @Column(name = "ISACTIVE")
    private Byte isactive;

}