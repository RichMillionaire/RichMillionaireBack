package com.richmillionaire.richmillionaire.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.richmillionaire.richmillionaire.dao.RoleDao;
import com.richmillionaire.richmillionaire.dto.RoleDto;
import com.richmillionaire.richmillionaire.models.Role;

@Service
public class RoleService {

    private final RoleDao roleDao;

    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    // 🔹 Récupérer tous les rôles
    public List<Role> findAll() {
        Iterable<Role> it = roleDao.findAll();
        List<Role> roles = new ArrayList<>();
        it.forEach(roles::add);
        return roles;
    }

    // 🔹 Récupérer un rôle par ID
    public Role getById(UUID id) {
        return roleDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Rôle non trouvé avec l'id: " + id));
    }

    // 🔹 Ajouter un rôle
    @Transactional
    public Role addRole(RoleDto roleDto) {
        Role role = new Role();
        role.setName(roleDto.getName());
        return roleDao.save(role);
    }

    // 🔹 Modifier un rôle
    @Transactional
    public Role updateRole(UUID id, RoleDto roleDto) {
        Role role = getById(id);

        if (roleDto.getName() != null && !roleDto.getName().isBlank()) {
            role.setName(roleDto.getName());
        }

        return roleDao.save(role);
    }

    // 🔹 Supprimer un rôle
    @Transactional
    public Role deleteById(UUID id) {
        Role role = getById(id);
        roleDao.delete(role);
        return role;
    }
}
