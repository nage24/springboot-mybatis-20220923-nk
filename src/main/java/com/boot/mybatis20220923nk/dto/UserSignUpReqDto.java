package com.boot.mybatis20220923nk.dto;

import com.boot.mybatis20220923nk.domain.User;
import lombok.Data;

@Data
public class UserSignUpReqDto {
    private String userId;
    private String userPassword;
    private String userName;
    private String userEmail;

    public User toEntity() {
        return User.builder()
                .user_id(userId)
                .user_password(userPassword)
                .user_name(userName)
                .user_email(userEmail)

                .build();
    }
}
