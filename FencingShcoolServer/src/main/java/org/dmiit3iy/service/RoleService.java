package org.dmiit3iy.service;

import org.dmiit3iy.model.Apprentice;
import org.dmiit3iy.model.Role;

import java.util.List;

public interface RoleService {

    void add(Role role);

    List<Role> get();

    Role get(long id);
    Role getByName(String s);

    Role delete(long id);

}
