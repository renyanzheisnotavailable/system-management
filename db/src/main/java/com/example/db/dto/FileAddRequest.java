package com.example.db.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileAddRequest {

    private Double filesize;

    private String name;

    private String pathName;

    private Integer filetypeId;

    private String version;

    private int number;
}
