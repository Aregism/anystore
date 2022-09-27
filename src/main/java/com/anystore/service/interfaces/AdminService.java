package com.anystore.service.interfaces;

import com.anystore.model.User;

import java.util.List;

public interface AdminService {

    User getById(int id);

    User getByEmail(String email);

    User getByToken(String token);

    User getByActivationCode(String code);

    List<User> getAll();

    void deleteUser(int id);

    void markAccountDeleted(int id);

    void markAccountDeleted(String email);

    void ban(int id);

    void ban(String email);
}
