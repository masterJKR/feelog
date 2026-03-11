package com.feelog.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupDto {
    @NotBlank(message = "이메일을 입력하세요")
    private String email;

    @Size(min=3 , max=12, message = "비밀번호는 최소3자  최대 12자 입니다.")
    private String password;
    @NotBlank(message = "이름을 입력하세요")
    private String name;

    private String passwordConfirm;



}
