package com.example.api.dto;

import lombok.Data;

@Data
public class FileAddRequest {

    private Double filesize;

    private String name;

    private String pathName;

    private Integer filetypeId;

    private String version;

    private int number;
}
