package com.dev.cinema.service;

import javax.management.relation.Role;

public interface RoleService {
    void add(Role role);

    Role getRoleByName(String roleName);
}
