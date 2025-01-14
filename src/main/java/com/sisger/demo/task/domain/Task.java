package com.sisger.demo.task.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisger.demo.section.domain.Section;
import com.sisger.demo.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tb_task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String title;
    private String description;
    private LocalDate initialDate;
    private LocalDate finalDate;
    private String employeeMessage;

    private StatusRole status;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User employee;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

}
