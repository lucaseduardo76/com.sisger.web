package com.sisger.demo.user.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDeleteUserDTO {

    private String id;
    private String passwordAuthorization;

}
