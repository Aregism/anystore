package com.anystore.repository;

import com.anystore.model.UserStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStorageRepository extends JpaRepository<UserStorage, Integer> {
    UserStorage getByEmail(String email);
}
