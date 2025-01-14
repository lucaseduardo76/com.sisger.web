package com.sisger.demo.user.domain.dto;

import com.sisger.demo.company.domain.dto.ResponseCompanyChildDTO;
import com.sisger.demo.task.domain.dto.ResponseTaskDTO;
import com.sisger.demo.task.domain.dto.ResponseTaskFindByUserDTO;
import com.sisger.demo.user.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseUserDTO {
    private String id;
    private String name;
    private String email;
    private String cpf;
    private Role role;
    private ResponseCompanyChildDTO company;
    private List<ResponseTaskFindByUserDTO> task;
}
