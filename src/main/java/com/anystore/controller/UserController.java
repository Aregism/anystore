package com.anystore.controller;

import com.anystore.model.User;
import com.anystore.service.interfaces.UserService;
import com.anystore.util.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;

@Controller
@RequestMapping("/users")
@RolesAllowed({"ROLE_USER", "ROLE_ADMIN", "ROLE_SUPERADMIN"})
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody User user) throws DuplicateException, InvalidFieldsException {
        userService.save(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify")
    public ResponseEntity<Void> verify(Principal principal, @RequestParam String code) throws TokenMismatchException, NullUserException, TokenTimedOutException, UnknownException {
        userService.verify(principal, code);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/pw-change-request")
    public ResponseEntity<Void> requestPasswordChange(Principal principal, @RequestParam String password) throws NullUserException, IncorrectUsernameOrPasswordException {
        userService.requestPasswordChange(principal, password);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/pw-change-confirm")
    public ResponseEntity<Void> confirmPasswordChange(
            Principal principal,
            @RequestParam String password1,
            @RequestParam String password2,
            @RequestParam String token) throws TokenMismatchException, NullUserException, TokenTimedOutException, PasswordMismatchException, UnknownException {
        userService.confirmPasswordChange(principal, password1, password2, token);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-account")
    public ResponseEntity<Void> deleteAccount(Principal principal, String password) {
        userService.deleteAccount(principal, password);
        return ResponseEntity.ok().build();
    }
}
