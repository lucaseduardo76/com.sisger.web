package com.sisger.demo.exception.details;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ValidationDetails extends ExceptionDetails {

    private final String field;
    private final String fieldsMessage;

}
