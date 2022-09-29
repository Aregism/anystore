package com.anystore.service.interfaces;

import com.anystore.model.User;
import com.anystore.util.exception.*;

import java.security.Principal;

public interface UserService {

    void save(User user) throws DuplicateException, InvalidFieldsException;

    void verify(Principal principal, String activationCode) throws TokenTimedOutException, TokenMismatchException, UnknownException, NullUserException;

    void requestPasswordChange(Principal principal, String password) throws IncorrectUsernameOrPasswordException, NullUserException;

    void confirmPasswordChange(Principal principal, String password1, String password2, String token) throws TokenMismatchException, TokenTimedOutException, PasswordMismatchException, UnknownException, NullUserException;

}
