package com.anystore.service.implementations;

import com.anystore.model.User;
import com.anystore.model.enums.UserStatus;
import com.anystore.repository.UserRepository;
import com.anystore.service.interfaces.AdminService;
import com.anystore.util.annotations.NotRecommended;
import com.anystore.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getById(int id) throws NotFoundException {
        User byId = userRepository.getById(id);
        if (byId != null) {
            return byId;
        }
        throw new NotFoundException("User by that id was not found");
    }

    @Override
    public User getByEmail(String email) throws NotFoundException {
        User byEmail = userRepository.getByEmail(email);
        if (byEmail != null) {
            return byEmail;
        }
        throw new NotFoundException("User by that email was not found");
    }

    @Override
    public User getByToken(String token) throws NotFoundException {
        User byToken = userRepository.getByToken(token);
        if (byToken != null) {
            return byToken;
        }
        throw new NotFoundException("User by that token was not found");
    }

    @Override
    public User getByActivationCode(String code) throws NotFoundException {
        User byCode = userRepository.getByEmail(code);
        if (byCode!=null){
            return byCode;
        }
        throw new NotFoundException("User by that activation code was not found");
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
