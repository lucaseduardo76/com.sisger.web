package com.sisger.demo.company.domain.dto;

import com.sisger.demo.section.domain.Section;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCompanyDTO {
    private String id;
    private String name;
    private String cnpj;
    private List<Section> sections;
}
