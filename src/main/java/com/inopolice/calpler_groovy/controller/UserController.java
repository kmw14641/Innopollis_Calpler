package com.inopolice.calpler_groovy.controller;

import com.inopolice.calpler_groovy.form.LoginForm;
import com.inopolice.calpler_groovy.form.SiteUserForm;
import com.inopolice.calpler_groovy.service.SiteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/user")
@Controller
public class UserController {
    @Autowired
    private SiteUserService siteUserService;

    @GetMapping("/create")
    public String SiteUserCreate(SiteUserForm siteUserForm) {
        return "siteuser_create";
    }

    @PostMapping("/create")
    public String SiteUserCreate(@Valid SiteUserForm siteUserForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "siteuser_create";
        }
        if(!siteUserForm.getPassword1().equals(siteUserForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "비밀번호가 일치하지 않습니다");
            return "siteuser_create";
        }
        try {
            siteUserService.create(siteUserForm);
        }catch(DataIntegrityViolationException e) {
            bindingResult.rejectValue("", "signupFailed", "이미 등록된 사용자입니다");
            return "siteuser_create";
        }catch(Exception e) {
            bindingResult.rejectValue("","signupFailed", e.getMessage());
            return "siteuser_create";
        }
        return "redirect:/select";
    }

    @GetMapping("/login")
    public String SiteUserLogin(LoginForm loginForm) {
        return "siteuser_login";
    }
}
