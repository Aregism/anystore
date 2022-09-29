package com.anystore.util.helper;

import com.anystore.model.User;
import com.anystore.model.enums.UserStatus;
import com.anystore.repository.UserRepository;
import com.anystore.service.interfaces.AuthorityService;
import com.anystore.util.exception.DuplicateException;
import com.anystore.util.mailing.CustomMailSender;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Set;

@Component
public class UserServiceHelper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomMailSender mailSender;

    public boolean userBodyIsValid(User user) throws DuplicateException {
        if (userRepository.getByEmail(user.getEmail()) != null) {
            throw new DuplicateException("User by that email already exists.");
        }
        return user.getName().matches("[a-zA-Z-]}") &&
                user.getLastName().matches("[a-zA-Z-]") &&
                user.getPassword().length() > 7 &&
                Period.between(user.getDateOfBirth(), LocalDate.now()).getYears() >= 18;
    }

    public String generateToken() {
        return RandomString.make(8);
    }

    public boolean tokenIsValid(User caller) {
        return Period.between(caller.getTokenSentDate(), LocalDate.now()).get(ChronoUnit.HOURS) <= 48;
    }

    public boolean codeIsValid(User caller) {
        return Period.between(caller.getCodeSentDate(), LocalDate.now()).get(ChronoUnit.HOURS) <= 168;
    }

    public boolean usersAreNotNull(User... users) {
        for (User user : users) {
            if (user == null) {
                return false;
            }
        }
        return true;
    }

    public void setup(User user) {
        user.setAuthorities(Set.of(authorityService.getByRole("ROLE_USER")));
        user.setUserStatus(UserStatus.UNVERIFIED);
        user.setActivationCode(generateToken());
        mailSender.sendEmail("Account activation",
                "Welcome to anystore!\n\nYour account activation code is: " +
                        user.getActivationCode() + "\n\nThis code will expire in 1 week.", user.getEmail());
        user.setCodeSentDate(LocalDate.now());
        user.setCodeSentTime(LocalTime.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void resendCode(User caller) {
        caller.setActivationCode(generateToken());
        caller.setCodeSentDate(LocalDate.now());
        caller.setCodeSentTime(LocalTime.now());
        userRepository.save(caller);
        mailSender.sendEmail("Account activation",
                "Welcome back to anystore!\n\nYour new account activation code is: " +
                        caller.getActivationCode() + "\n\nThis code will expire in 1 week.", caller.getEmail());
    }
}
