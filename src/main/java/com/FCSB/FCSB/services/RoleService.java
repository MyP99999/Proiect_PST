package com.FCSB.FCSB.services;

import com.FCSB.FCSB.entities.Department;
import com.FCSB.FCSB.entities.Role;
import com.FCSB.FCSB.repositories.DepartmentRepository;
import com.FCSB.FCSB.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findRoleById(Integer id) {
        return roleRepository.findRoleById(id).orElse(null);
    }
}
