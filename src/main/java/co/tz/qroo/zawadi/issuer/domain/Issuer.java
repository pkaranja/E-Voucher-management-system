package co.tz.qroo.zawadi.issuer.domain;

import co.tz.qroo.zawadi.category.domain.Category;
import co.tz.qroo.zawadi.user.model.ActiveStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "Issuers")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Issuer {

    @Id
    @Column(nullable = false, updatable = false, columnDefinition = "char(36)")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column
    private String website;

    @Column
    private String address;

    @Column(nullable = false)
    private Long hits;

    @Column(nullable = false)
    private String logo;

    @Column
    private String facebook;

    @Column
    private String instagram;

    @Column(length = 7)
    private String primaryColor;

    @Column(length = 7)
    private String secondaryColor;

    @Column(length = 7)
    private String primaryFontColor;

    @Column(length = 7)
    private String secondaryFontColor;

    @Column(name = "\"description\"", columnDefinition = "longtext")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal minAmount;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal maxAmount;

    @Column(nullable = false)
    private Boolean popular;

    @Column(nullable = false)
    private Boolean featured;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ActiveStatus status;

    @ManyToMany
    @JoinTable(
            name = "IssuerCategories",
            joinColumns = @JoinColumn(name = "issuerId"),
            inverseJoinColumns = @JoinColumn(name = "categoryId")
    )
    private Set<Category> category;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
