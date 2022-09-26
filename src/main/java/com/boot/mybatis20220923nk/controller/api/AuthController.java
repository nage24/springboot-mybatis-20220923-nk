package com.boot.mybatis20220923nk.controller.api;

import com.boot.mybatis20220923nk.domain.User;
import com.boot.mybatis20220923nk.dto.CMRespDto;
import com.boot.mybatis20220923nk.dto.UserSignUpReqDto;
import com.boot.mybatis20220923nk.dto.UserSignUpRespDto;
import com.boot.mybatis20220923nk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final UserRepository userRepository;

    // /api/auth/signup
    @PostMapping("/api/auth/signup")
    public ResponseEntity<?> signUp(UserSignUpReqDto userSignUpReqDto) {
        // formData 로 들어옴. -> HTML 상에서 DTO 로 변환을 스프링이 해주는데 그 때 Getter 가 필요함.

        log.info("{}", userSignUpReqDto);

        User user = userSignUpReqDto.toEntity();



        log.info("마이바티스 보내지기 전 Entity: {}", user); // useGeneratedKeys 를 쓰면 Auto increment 된 user code 를 바로 가져다 쓸 수 있게 되는 것임.

        int result = userRepository.save(user);

        log.info("마이바티스 보내지고 나서 Entity: {}", user);


        UserSignUpRespDto userSignUpRespDto = user.toDto();


        if(result == 0) {
            return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "회원 가입 오류", null));
        }

        return ResponseEntity.ok(new CMRespDto<>(1, "회원 가입 완료", userSignUpRespDto));
    }


}
