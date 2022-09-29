package com.anystore.config;

import com.anystore.model.Authority;
import com.anystore.model.User;
import com.anystore.service.interfaces.AdminService;
import com.anystore.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminService adminService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = null;
        try {
            user = adminService.getByEmail(name);
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException("Wrong username: " + name);
        }
        if (user == null) {
            throw new UsernameNotFoundException("Wrong username: " + name);
        }
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority : user.getAuthorities()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getRole());
            grantedAuthorities.add(grantedAuthority);
        }
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), grantedAuthorities);
    }
}
