package com.sisger.demo.task.domain.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestEmployeeMessageDTO {
    @NotEmpty(message = "taskId cannot be empty")
    private String taskId;
    @NotNull(message = "userId cannot be null")
    private String message;
}
