package com.anystore.service.interfaces;

import com.anystore.model.Authority;

public interface AuthorityService {

    Authority getById(int id);

    Authority getByRole(String role);
}
