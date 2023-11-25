package vn.com.devmaster.services.managematerial.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    @Column(name = "TITLE")
    private String title;

    @Lob
    @Column(name = "CONTENT_L")
    private String contentL;

    @Column(name = "DATE_BLOG")
    private LocalDate dateBlog;

    @Column(name = "BLOG_TOPIC", length = 100)
    private String blogTopic;

    @Column(name = "CREATED_DATE")
    private Instant createdDate;

    @Column(name = "CREATED_BY")
    private Integer createdBy;

    @Column(name = "UPDATED_DATE")
    private Instant updatedDate;

    @Column(name = "UPDATED_BY")
    private Integer updatedBy;

    @Lob
    @Column(name = "BLOG_IMAGE")
    private String blogImage;

    @Column(name = "ISACTIVE")
    private Byte isactive;

}