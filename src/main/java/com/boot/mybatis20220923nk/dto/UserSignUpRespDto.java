package com.boot.mybatis20220923nk.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserSignUpRespDto {
    private int userCode;
    private String userId;
    private String userPassword;
    private String userName;
    private String userEmail;

}
