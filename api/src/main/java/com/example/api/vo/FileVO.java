package com.example.api.vo;

import com.example.db.domain.File;
import com.example.db.domain.FileType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FileVO {
    File file;
    FileType fileType;
}
