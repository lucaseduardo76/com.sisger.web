package com.sisger.demo.task.domain.dto;

import com.sisger.demo.task.domain.StatusRole;
import com.sisger.demo.user.domain.dto.ResponseUserToTaskDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTaskDTO {

    private String id;
    private String title;
    private String description;
    private LocalDate initialDate;
    private LocalDate finalDate;
    private String employeeMessage;
    private StatusRole status;
    private ResponseUserToTaskDTO employee;

}
