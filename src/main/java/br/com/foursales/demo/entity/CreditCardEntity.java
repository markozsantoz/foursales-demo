package br.com.foursales.demo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity(name = "CREDIT_CARD")
@EqualsAndHashCode(of = {"id", "candidate"})
public class CreditCardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    private CandidateEntity candidate;
    @Transient
    private String name;
    private String nameEncrypted;
    private String brand;
    @Transient
    private String cardNumber;
    private String cardNumberEncrypted;
    @Transient
    private String cvv;
    private String cvvEncrypted;
    @Transient
    private String expirationDate;
    private String expirationDateEncrypted;
}
