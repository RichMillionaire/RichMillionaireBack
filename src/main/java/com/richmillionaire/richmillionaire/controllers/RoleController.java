package com.richmillionaire.richmillionaire.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.richmillionaire.richmillionaire.dto.ApiResponse;
import com.richmillionaire.richmillionaire.dto.RoleDto;
import com.richmillionaire.richmillionaire.models.Role;
import com.richmillionaire.richmillionaire.security.HasRole;
import com.richmillionaire.richmillionaire.services.RoleService;

@CrossOrigin
@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    // 🔹 Récupérer tous les rôles
    @GetMapping("")
    public ResponseEntity<ApiResponse<List<Role>>> getAllRoles() {
        try {
            List<Role> roles = roleService.findAll();
            return ResponseEntity.ok(ApiResponse.success("Liste des rôles récupérée ✅", roles));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ApiResponse.error("Erreur serveur: " + e.getMessage()));
        }
    }

    // 🔹 Récupérer un rôle par ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Role>> getRoleById(@PathVariable UUID id) {
        try {
            Role role = roleService.getById(id);
            return ResponseEntity.ok(ApiResponse.success("Rôle récupéré ✅", role));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(ApiResponse.error("Rôle non trouvé: " + e.getMessage()));
        }
    }

    // 🔹 Créer un rôle (ADMIN uniquement)
    @HasRole("ADMIN")
    @PostMapping("")
    public ResponseEntity<ApiResponse<Role>> createRole(@RequestBody RoleDto roleDto) {
        try {
            Role role = roleService.addRole(roleDto);
            return ResponseEntity.ok(ApiResponse.success("Rôle créé ✅", role));
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ApiResponse.error("Erreur serveur: " + e.getMessage()));
        }
    }

    // 🔹 Mettre à jour un rôle (ADMIN uniquement)
    @HasRole("ADMIN")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Role>> updateRole(
            @PathVariable UUID id,
            @RequestBody RoleDto roleDto
    ) {
        try {
            Role updatedRole = roleService.updateRole(id, roleDto);
            return ResponseEntity.ok(ApiResponse.success("Rôle mis à jour ✅", updatedRole));
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(ApiResponse.error("Rôle non trouvé: " + e.getMessage()));
        }
    }

    // 🔹 Supprimer un rôle (ADMIN uniquement)
    @HasRole("ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Role>> deleteRole(@PathVariable UUID id) {
        try {
            Role deletedRole = roleService.deleteById(id);
            return ResponseEntity.ok(ApiResponse.success("Rôle supprimé ✅", deletedRole));
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(ApiResponse.error("Rôle non trouvé: " + e.getMessage()));
        }
    }
}
