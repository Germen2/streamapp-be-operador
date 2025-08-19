package germen.streamapp.operador.service;

import germen.streamapp.operador.model.Role;
import germen.streamapp.operador.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Optional<Role> getRoleByName(String name){
        return roleRepository.findRoleByName(name);
    }

}
