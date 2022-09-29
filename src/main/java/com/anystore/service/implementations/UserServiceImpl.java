package com.anystore.service.implementations;

import com.anystore.model.User;
import com.anystore.model.enums.UserStatus;
import com.anystore.repository.UserRepository;
import com.anystore.service.interfaces.UserService;
import com.anystore.util.exception.*;
import com.anystore.util.helper.UserServiceHelper;
import com.anystore.util.mailing.CustomMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserServiceHelper userHelper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomMailSender mailSender;

    @Override
    @Transactional
    public void save(User user) throws DuplicateException, InvalidFieldsException {
        if (userHelper.userBodyIsValid(user)) {
            userHelper.setup(user);
            userRepository.save(user);
        } else {
            throw new InvalidFieldsException("One or more pieces of entered information are invalid.");
        }
    }

    @Override
    @Transactional
    public void verify(Principal principal, String code) throws TokenTimedOutException, TokenMismatchException, UnknownException, NullUserException {
        User caller = userRepository.getByEmail(principal.getName());
        User fromDb = userRepository.getByActivationCode(code);
        if (!userHelper.usersAreNotNull(caller, fromDb)) {
            throw new NullUserException("Could not process this request.");
        }
        if (caller.getActivationCode().equals(fromDb.getActivationCode()) && userHelper.codeIsValid(caller)) {
            fromDb.setUserStatus(UserStatus.VERIFIED);
            fromDb.setActivationCode(null);
            fromDb.setAccountActivatedDate(LocalDate.now());
            fromDb.setAccountActivatedTime(LocalTime.now());
            userRepository.save(fromDb);
        } else if (!userHelper.codeIsValid(caller)) {
            userHelper.resendCode(caller);
            throw new TokenTimedOutException("Activation code has timed out.");
        } else if (caller.getActivationCode().equals(code)) {
            throw new TokenMismatchException("Activation code is invalid.");
        } else {
            throw new UnknownException("Something unexpected happened. Please try again.");
        }
    }

    @Override
    @Transactional
    public void requestPasswordChange(Principal principal, String password) throws IncorrectUsernameOrPasswordException, NullUserException {
        User caller = userRepository.getByEmail(principal.getName());
        if (!userHelper.usersAreNotNull(caller)) {
            throw new NullUserException("Could not process this request.");
        }
        if (passwordEncoder.matches(password, caller.getPassword())) {
            caller.setTokenSentDate(LocalDate.now());
            caller.setTokenSentTime(LocalTime.now());
            caller.setToken(userHelper.generateToken());
            userRepository.save(caller);
            mailSender.sendEmail("Your password change token.",
                    "Hi,\n\nYour password change token is: " + caller.getToken(),
                    caller.getEmail());
        } else {
            mailSender.sendEmail("Account vulnerable.",
                    "Hello.\n\nYou are receiving this email, because someone just tried changing your password.\n" +
                            "If this was you, please try again, if not, we recommend immediately changing it yourself.\n\nThank you.",
                    caller.getEmail());
            throw new IncorrectUsernameOrPasswordException("Incorrect password.");
        }
    }

    @Override
    @Transactional
    public void confirmPasswordChange(Principal principal, String password1, String password2, String token) throws TokenMismatchException, TokenTimedOutException, PasswordMismatchException, UnknownException, NullUserException {
        User caller = userRepository.getByEmail(principal.getName());
        User byToken = userRepository.getByToken(token);
        if (!userHelper.usersAreNotNull(caller, byToken)) {
            throw new NullUserException("Could not process this request.");
        }
        if (caller.getToken().equals(token) && password1.equals(password2) && userHelper.tokenIsValid(caller)) {
            byToken.setToken(null);
            byToken.setPassword(passwordEncoder.encode(password1));
            userRepository.save(byToken);
        } else if (!caller.getToken().equals(token)) {
            throw new TokenMismatchException("Password change token is invalid.");
        } else if (!userHelper.tokenIsValid(caller)) {
            mailSender.sendEmail("Password change token has timed out",
                    "The password change token provided has timed out. Please request a new token and try again.",
                    caller.getEmail());
            throw new TokenTimedOutException("Password change token has timed out.");
        } else if (!password1.equals(password2)) {
            throw new PasswordMismatchException("Provided passwords did not match.");
        } else {
            throw new UnknownException("Something unexpected happened. Please try again.");
        }
    }
}
