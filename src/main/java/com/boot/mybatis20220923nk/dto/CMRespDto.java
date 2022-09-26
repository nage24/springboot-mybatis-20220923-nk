package com.boot.mybatis20220923nk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CMRespDto<T> {
    private int code;
    private String msg;
    private T data;
}
