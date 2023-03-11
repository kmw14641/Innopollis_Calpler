package com.inopolice.calpler_groovy.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class BoardForm {
    @NotEmpty(message = "이름은 필수항목입니다")
    @Size(max=45, message = "이름의 최대 길이는 45자입니다")
    private String name;
    @NotEmpty(message = "출신 대학은 필수항목입니다")
    @Size(max=45, message = "출신 대학의 최대 길이는 150자입니다")
    private String department;
    @NotEmpty(message = "직업은 필수항목입니다")
    @Size(max=45, message = "직업의 최대 길이는 150자입니다")
    private String job;
    @Size(max=45, message = "자신의 경험의 최대 길이는 150자입니다")
    private String experience;
    @NotEmpty(message = "설명은 필수항목입니다")
    private String text;
    @Size(max=45, message = "링크의 최대 길이는 150자입니다")
    private String link;
    private String tag;
    private String university;
}
