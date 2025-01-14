package com.sisger.demo.user.domain.dto;

import com.sisger.demo.user.domain.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseUserToTaskDTO {
    private String id;
    private String name;
    private Role role;
}
