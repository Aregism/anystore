package com.anystore.util.helper;

import com.anystore.model.enums.UserStatus;
import com.anystore.repository.AuthorityRepository;
import com.anystore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static com.anystore.model.Authority.*;
import static com.anystore.model.User.superadminUser;

public class PostConstructInitialization {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    static {
        superadminUser.setName("Areg");
        superadminUser.setLastName("Grigoryan");
        superadminUser.setEmail("aregism@gmail.com");
        superadminUser.setPassword("password");
        superadminUser.setUserStatus(UserStatus.VERIFIED);
        superadminUser.setAccountActivatedDate(LocalDate.now());
        superadminUser.setAccountActivatedTime(LocalTime.now());
        superadminUser.setAuthorities(Set.of());
    }

    static {
        superadminAuthority.setRole("ROLE_SUPERADMIN");
        adminAuthority.setRole("ROLE_ADMIN");
        userAuthority.setRole("ROLE_USER");
    }

    @PostConstruct
    private void init() {
        authorityRepository.saveAll(List.of(superadminAuthority, adminAuthority, userAuthority));
        userRepository.save(superadminUser);
    }
}
