package com.example.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<U> {
    private int code;
    private String msg;
    private U data;
    public Result (int code, U data, String msg) {
        this.data = data;
        this.msg = msg;
        this.code = code;
    }
}
