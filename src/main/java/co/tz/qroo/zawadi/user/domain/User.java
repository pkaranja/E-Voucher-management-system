package co.tz.qroo.zawadi.user.domain;

import co.tz.qroo.zawadi.user.model.ActiveStatus;
import co.tz.qroo.zawadi.user.model.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "Users")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class User {

    @Id
    @Column(nullable = false, updatable = false, columnDefinition = "char(36)")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @Column(nullable = false, unique = true, length = 100)
    private String externalId;

    @Column
    private String address;

    @Column(nullable = false)
    private LocalDateTime lastLogin;

    @Column(nullable = false, unique = true, length = 15)
    private String phoneNumber;

    @Column(nullable = false)
    private Long giftcardsPurchased;

    @Column(nullable = false)
    private Long giftcardsReceived;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ActiveStatus status;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private Integer age;

    @Column(length = 50)
    private String location;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(length = 50)
    private String nationality;

    @Column(length = 50)
    private String govtId;

    @Column
    private LocalDate govtIdExpiryDate;

    @Column(length = 50)
    private String govtIdType;

    @Column(length = 50)
    private String region;

    @Column(nullable = false)
    private Boolean privacyPolicyConsent;

    @Column(nullable = false)
    private LocalDate privacyPolicyConsentDate;

    @Column(nullable = false)
    private Boolean termsAndConditionConsent;

    @Column(nullable = false)
    private LocalDate termsAndConditionConsentDate;

    @Column
    private Boolean isAutopayOn;

    @Column
    private Boolean phoneNumberValidated;

    @Column(length = 20)
    private String longitude;

    @Column(length = 20)
    private String latitude;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}