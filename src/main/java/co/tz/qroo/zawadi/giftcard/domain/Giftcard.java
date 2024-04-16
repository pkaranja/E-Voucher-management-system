package co.tz.qroo.zawadi.giftcard.domain;

import co.tz.qroo.zawadi.giftcard.model.GiftcardStatus;
import co.tz.qroo.zawadi.issuer.domain.Issuer;
import co.tz.qroo.zawadi.theme.domain.Theme;
import co.tz.qroo.zawadi.transaction.domain.Transaction;
import co.tz.qroo.zawadi.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "Giftcards")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Giftcard {

    @Id
    @Column(nullable = false, updatable = false, columnDefinition = "char(36)")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @Column(nullable = false, unique = true, length = 12)
    private String code;

    @Column(nullable = false)
    private Integer cvv;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal value;

    @Column(nullable = false)
    private LocalDate expirationDate;

    @Column(columnDefinition = "longtext")
    private String message;

    @Column(nullable = false)
    private String purchaserName;

    @Column(nullable = false)
    private String receipientName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GiftcardStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issuer_id", nullable = false)
    private Issuer issuer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchaser_id", nullable = false)
    private User purchaser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id")
    private User recipient;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id", unique = true)
    private Theme theme;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", unique = true)
    private Transaction transaction;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
