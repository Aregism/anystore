package com.anystore.service.implementations;

import com.anystore.model.Authority;
import com.anystore.repository.AuthorityRepository;
import com.anystore.service.interfaces.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public Authority getById(int id) {
        return authorityRepository.getById(id);
    }

    @Override
    public Authority getByRole(String role) {
        return authorityRepository.getByRole(role);
    }
}
