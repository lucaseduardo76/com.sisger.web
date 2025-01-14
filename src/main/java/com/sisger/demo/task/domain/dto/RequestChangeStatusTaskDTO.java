package com.sisger.demo.task.domain.dto;

import com.sisger.demo.task.domain.StatusRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestChangeStatusTaskDTO {

    private String taskId;
    private StatusRole status;

}
