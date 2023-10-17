package com.example.api.dto;

import lombok.Data;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Validated
@ToString
@Data
public class UserUpdateInformationRequest {

    @NotNull
    private Integer id;
    @NotBlank
    private String name;
    @NotNull
    private Integer departmentId;
    @NotBlank
    private String sex;
    @NotBlank
    private String address;
    @NotNull
    private Integer companyId;


}
