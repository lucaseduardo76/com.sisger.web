package com.sisger.demo.task.domain.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestTaskDTO {
    private String title;
    private String description;
    private LocalDate initialDate;
    private LocalDate finalDate;
    private String userId;
    private String sectionId;
}
