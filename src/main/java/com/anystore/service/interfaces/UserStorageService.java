package com.anystore.service.interfaces;

import com.anystore.model.UserStorage;

public interface UserStorageService {

    void save(UserStorage storage);

    UserStorage getByEmail(String email);
}
