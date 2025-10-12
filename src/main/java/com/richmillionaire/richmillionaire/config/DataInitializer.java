package com.richmillionaire.richmillionaire.config;

import com.richmillionaire.richmillionaire.dao.RoleDao;
import com.richmillionaire.richmillionaire.entity.RoleEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final RoleDao roleDao;

    @Override
    public void run(String... args) throws Exception {
        initializeRoles();
    }

    private void initializeRoles() {
        // Vérifier et créer ROLE_USER
        if (roleDao.findByName("ROLE_USER").isEmpty()) {
            RoleEntity userRole = RoleEntity.builder()
                    .name("ROLE_USER")
                    .build();
            roleDao.save(userRole);
            log.info("Rôle ROLE_USER créé avec succès");
        } else {
            log.info("Rôle ROLE_USER existe déjà");
        }

        // Vérifier et créer ROLE_ADMIN
        if (roleDao.findByName("ROLE_ADMIN").isEmpty()) {
            RoleEntity adminRole = RoleEntity.builder()
                    .name("ROLE_ADMIN")
                    .build();
            roleDao.save(adminRole);
            log.info("Rôle ROLE_ADMIN créé avec succès");
        } else {
            log.info("Rôle ROLE_ADMIN existe déjà");
        }
    }
}

