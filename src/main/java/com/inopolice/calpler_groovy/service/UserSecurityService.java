package com.inopolice.calpler_groovy.service;

import com.inopolice.calpler_groovy.UserRole;
import com.inopolice.calpler_groovy.entity.SiteUser;
import com.inopolice.calpler_groovy.repository.SiteUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserSecurityService implements UserDetailsService {
    @Autowired
    private SiteUserRepository siteUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SiteUser> _siteUser  = siteUserRepository.findByUsername(username);
        if(!_siteUser.isPresent()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다");
        }
        SiteUser siteUser = _siteUser.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
    }
}
