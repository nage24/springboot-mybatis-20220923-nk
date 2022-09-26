package com.boot.mybatis20220923nk.domain;

import com.boot.mybatis20220923nk.dto.UserSignUpRespDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    private int user_code;
    private String user_id;
    private String user_password;
    private String user_name;
    private String user_email;

    public UserSignUpRespDto toDto() {
        return UserSignUpRespDto.builder()
                .userCode(user_code)
                .userId(user_id)
                .userPassword(user_password)
                .userName(user_name)
                .userEmail(user_email)

                .build();
    }
}


