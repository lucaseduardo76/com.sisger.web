package com.sisger.demo.section.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisger.demo.company.domain.Company;
import com.sisger.demo.task.domain.Task;
import com.sisger.demo.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tb_section")
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY) // Evita carregar a empresa automaticamente
    @JoinColumn(name = "company_id", nullable = false) // Define que o campo é obrigatório
    private Company company;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Task> tasks;

}
