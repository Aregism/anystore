package com.anystore.controller;

import com.anystore.model.User;
import com.anystore.service.interfaces.AdminService;
import com.anystore.util.annotations.NotRecommended;
import com.anystore.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RolesAllowed({"ROLE_ADMIN", "ROLE_SUPERADMIN"})
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable int id) throws NotFoundException {
        return ResponseEntity.ok(adminService.getById(id));
    }

    @GetMapping("/by-email")
    public ResponseEntity<User> getByEmail(@RequestParam String email) throws NotFoundException {
        return ResponseEntity.ok(adminService.getByEmail(email));
    }

    @GetMapping("/by-token")
    public ResponseEntity<User> getByToken(@RequestParam String token) throws NotFoundException {
        return ResponseEntity.ok(adminService.getByToken(token));
    }

    @GetMapping("/by-code")
    public ResponseEntity<User> getByActivationCode(@RequestParam String code) throws NotFoundException {
        return ResponseEntity.ok(adminService.getByActivationCode(code));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(adminService.getAll());
    }

    @NotRecommended
    @RolesAllowed({"ROLE_SUPERADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        adminService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/mark-deleted/{id}")
    public ResponseEntity<Void> markDeleted(@PathVariable int id) {
        adminService.markAccountDeleted(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/mark-deleted/by-email")
    public ResponseEntity<Void> markDeleted(@RequestParam String email) {
        adminService.markAccountDeleted(email);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/ban/{id}")
    public ResponseEntity<Void> ban(@PathVariable int id) {
        adminService.ban(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/ban")
    public ResponseEntity<Void> ban(@RequestParam String email) {
        adminService.ban(email);
        return ResponseEntity.ok().build();
    }


}
