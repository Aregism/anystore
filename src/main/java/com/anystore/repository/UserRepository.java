package com.anystore.repository;

import com.anystore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User getById(int id);

    User getByEmail(String email);

    User getByToken(String token);

    User getByActivationCode(String code);

    List<User> findAll();

}
