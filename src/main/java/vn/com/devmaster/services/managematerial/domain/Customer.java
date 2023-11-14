package vn.com.devmaster.services.managematerial.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NAME", length = 250)
    private String name;

    @Column(name = "USERNAME", length = 50)
    private String username;

    @Column(name = "PASSWORD", length = 50)
    private String password;

    @Column(name = "ADDRESS", length = 500)
    private String address;

    @Column(name = "EMAIL", length = 150)
    private String email;

    @Column(name = "PHONE", length = 50)
    private String phone;

    @Column(name = "CREATED_DATE")
    private Instant createdDate;

    @Column(name = "ISACTIVE")
    private Byte isactive;
    @OneToMany
    @JoinColumn(name = "idcustomer")
    List<Order> orders=new ArrayList<>();
    @Column(name = "ROLE")
    private Integer role;

}