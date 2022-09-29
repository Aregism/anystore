package com.anystore.service.interfaces;

import com.anystore.model.User;
import com.anystore.util.exception.NotFoundException;

import java.util.List;

public interface AdminService {

    User getById(int id) throws NotFoundException;

    User getByEmail(String email) throws NotFoundException;

    User getByToken(String token) throws NotFoundException;

    User getByActivationCode(String code) throws NotFoundException;

    List<User> getAll();

    void deleteUser(int id);

    void markAccountDeleted(int id);

    void markAccountDeleted(String email);

    void ban(int id);

    void ban(String email);
}
