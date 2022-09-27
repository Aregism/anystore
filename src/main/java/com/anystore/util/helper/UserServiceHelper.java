package com.anystore.util.helper;

import com.anystore.model.User;
import com.anystore.repository.UserRepository;
import com.anystore.util.exception.DuplicateException;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

@Component
public class UserServiceHelper {

    @Autowired
    private UserRepository userRepository;

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
        return Period.between(caller.getTokenSentDate(), LocalDate.now()).get(ChronoUnit.HOURS) <= 168;
    }

    public boolean usersAreNotNull(User... users) {
        for (User user : users) {
            if (user == null) {
                return false;
            }
        }
        return true;
    }

}
