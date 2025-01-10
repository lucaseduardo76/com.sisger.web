package com.sisger.demo.company.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisger.demo.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tb_company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String razaoSocial;
    private Integer cnpj;
    private Integer cpf;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "mainAccountId", referencedColumnName = "id")
    private User mainAccount;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Section> section;


    @JsonIgnore
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> employees;
}
