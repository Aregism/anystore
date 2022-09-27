package com.anystore.service.implementations;

import com.anystore.model.User;
import com.anystore.model.enums.UserStatus;
import com.anystore.repository.AuthorityRepository;
import com.anystore.repository.UserRepository;
import com.anystore.service.interfaces.AdminService;
import com.anystore.util.annotations.NotRecommended;
import com.anystore.util.helper.UserServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserServiceHelper userHelper;

    @Override
    public User getById(int id) {
        return userRepository.getById(id);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    @Override
    public User getByToken(String token) {
        return userRepository.getByToken(token);
    }

    @Override
    public User getByActivationCode(String code) {
        return userRepository.getByActivationCode(code);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    @NotRecommended
    @Transactional
    public void deleteUser(int id) {
        userRepository.delete(userRepository.getById(id));
    }

    @Override
    public void markAccountDeleted(int id) {
        User fromDB = userRepository.getById(id);
        fromDB.setUserStatus(UserStatus.DELETED);
        fromDB.setEmail(Integer.toString(fromDB.getId()));
        userRepository.save(fromDB);
    }

    @Override
    public void markAccountDeleted(String email) {
        User fromDB = userRepository.getByEmail(email);
        fromDB.setUserStatus(UserStatus.DELETED);
        fromDB.setEmail(Integer.toString(fromDB.getId()));
        userRepository.save(fromDB);
    }

    @Override
    public void ban(int id) {
        User fromDB = userRepository.getById(id);
        fromDB.setUserStatus(UserStatus.BANNED);
        fromDB.setEmail(Integer.toString(fromDB.getId()));
        userRepository.save(fromDB);
    }

    @Override
    public void ban(String email) {
        User fromDB = userRepository.getByEmail(email);
        fromDB.setUserStatus(UserStatus.BANNED);
        fromDB.setEmail(Integer.toString(fromDB.getId()));
        userRepository.save(fromDB);

    }
}
