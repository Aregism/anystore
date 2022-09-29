package com.anystore.controller;

import com.anystore.model.User;
import com.anystore.service.interfaces.UserService;
import com.anystore.util.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;

@Controller
@RequestMapping("/users")
@RolesAllowed({"ROLE_USER", "ROLE_ADMIN", "ROLE_SUPERADMIN"})
public class UserController {
    // TODO: 28-Sep-22 Add @RequestParam, @requestBody and @pathVariable where necessary
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody User user) throws DuplicateException, InvalidFieldsException {
        userService.save(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify")
    public ResponseEntity<Void> verify(Principal principal, String code) throws TokenMismatchException, NullUserException, TokenTimedOutException, UnknownException {
        userService.verify(principal, code);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/pw-change-request")
    public ResponseEntity<Void> requestPasswordChange(Principal principal, String password) throws NullUserException, IncorrectUsernameOrPasswordException {
        userService.requestPasswordChange(principal, password);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/pw-change-confirm")
    public ResponseEntity<Void> confirmPasswordChange(Principal principal, String password1, String password2, String token) throws TokenMismatchException, NullUserException, TokenTimedOutException, PasswordMismatchException, UnknownException {
        userService.confirmPasswordChange(principal, password1, password2, token);
        return ResponseEntity.ok().build();
    }


}
