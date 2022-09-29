package com.anystore.service.implementations;

import com.anystore.model.UserStorage;
import com.anystore.repository.UserStorageRepository;
import com.anystore.service.interfaces.UserStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserStorageServiceImpl implements UserStorageService {

    @Autowired
    private UserStorageRepository userStorageRepository;

    @Override
    public void save(UserStorage storage) {
        userStorageRepository.save(storage);
    }

    @Override
    public UserStorage getByEmail(String email) {
        return userStorageRepository.getByEmail(email);
    }
}
