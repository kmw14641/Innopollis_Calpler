package com.inopolice.calpler_groovy.service;

import com.inopolice.calpler_groovy.entity.SiteUser;
import com.inopolice.calpler_groovy.form.SiteUserForm;
import com.inopolice.calpler_groovy.repository.SiteUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SiteUserService {
    @Autowired
    private SiteUserRepository siteUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public SiteUser userView(Integer id) { return siteUserRepository.findById(id).get(); }

    public void create(SiteUserForm siteUserForm) {
        SiteUser siteUser = new SiteUser();
        siteUser.setUsername(siteUserForm.getUsername());
        siteUser.setEmail(siteUserForm.getEmail());
        siteUser.setPassword(passwordEncoder.encode(siteUserForm.getPassword1()));
        siteUserRepository.save(siteUser);
    }
}
