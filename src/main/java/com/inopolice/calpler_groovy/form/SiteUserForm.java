package com.inopolice.calpler_groovy.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SiteUserForm {
    @Size(min = 4, max = 45)
    @NotEmpty(message = "ID를 입력해주세요")
    private String username;
    @NotEmpty(message = "비밀번호를 입력해주세요")
    private String password1;
    @NotEmpty(message = "비밀번호가 일치하지 않습니다")
    private String password2;
    @NotEmpty(message = "이메일을 입력해주세요")
    @Email
    private String email;
}
