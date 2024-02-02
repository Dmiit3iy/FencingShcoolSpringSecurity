package org.dmiit3iy.service;

import org.dmiit3iy.model.Role;
import org.dmiit3iy.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class RoleServiceImp implements RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void add(Role role) {
        try {
            roleRepository.save(role);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("This role already added!");
        }
    }

    @Override
    public List<Role> get() {
        return roleRepository.findAll();
    }

    @Override
    public Role get(long id) {
        return roleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Role does not exists!"));
    }
    @Override
    public Role getByName(String s){
        return roleRepository.findByRole(s).orElseThrow(() -> new IllegalArgumentException("Role does not exists!"));
    }
    @Override
    public Role delete(long id) {
        Role role = get(id);
        roleRepository.deleteById(id);
        return role;
    }
}
