package br.com.foursales.demo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "CANDIDATE")
@EqualsAndHashCode(of = "id")
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Transient
    private String name;
    private String nameEncrypted;
    @Transient
    private String email;
    private String emailEncrypted;
    //@OneToMany
    @Transient
    private List<CreditCardEntity> creditCards = new ArrayList<>();
}
