package com.sisger.demo.task.domain.dto;

import com.sisger.demo.section.domain.dto.ResponseSectionDTO;
import com.sisger.demo.task.domain.StatusRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTaskFindByUserDTO {

    private String id;
    private String title;
    private String description;
    private LocalDate initialDate;
    private LocalDate finalDate;
    private String employeeMessage;
    private StatusRole status;
    private ResponseSectionDTO section;

}
