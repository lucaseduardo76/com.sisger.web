package com.sisger.demo.company.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisger.demo.section.domain.Section;
import com.sisger.demo.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@EqualsAndHashCode
@Table(name = "tb_company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String cnpj;

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
