package com.inopolice.calpler_groovy.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class CommentForm {
    @NotEmpty(message = "내용은 필수항목입니다")
    private String text;
}
