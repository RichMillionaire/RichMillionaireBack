package com.richmillionaire.richmillionaire.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.richmillionaire.richmillionaire.dao.RoleDao;
import com.richmillionaire.richmillionaire.dao.UserDao;
import com.richmillionaire.richmillionaire.dto.AuthResponse;
import com.richmillionaire.richmillionaire.dto.LoginRequest;
import com.richmillionaire.richmillionaire.dto.RegisterRequest;
import com.richmillionaire.richmillionaire.models.Role;
import com.richmillionaire.richmillionaire.models.User;
import com.richmillionaire.richmillionaire.utils.JwtUtil;

@Service
public class AuthService {

    private final UserDao userDao;
    private final JwtUtil jwtUtil;
    private final RoleDao roleDao;

    public AuthService(UserDao userDao, JwtUtil jwtUtil, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.jwtUtil = jwtUtil;
    }

    public void register(RegisterRequest request) throws Exception {
        if (request.getUsername().contains("@")) {
            throw new Exception("Le username ne doit pas contenir '@'");
        }
        if (userDao.findByUsername(request.getUsername()).isPresent()) {
            throw new Exception("Username déjà pris");
        }
        if (userDao.findByEmail(request.getEmail()).isPresent()) {
            throw new Exception("Email déjà utilisé");
        }

        String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(hashedPassword);
        Role userRole = roleDao.findByName("USER")
            .orElseThrow(() -> new Exception("Le rôle USER n'existe pas !"));

        Set<Role> roles = new HashSet<>();
        roles.add(userRole);

        user.setRoles(roles); 

        userDao.save(user);
    }

    public AuthResponse login(LoginRequest request) throws Exception {
    Optional<User> optionalUser = userDao.findByUsernameOrEmail(request.getUsernameOrEmail(),null);
    if (optionalUser.isEmpty()) {
        throw new Exception("Utilisateur non trouvé");
    }

    User user = optionalUser.get();

    if (!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
        throw new Exception("Mot de passe incorrect");
    }

    String token = jwtUtil.generateToken(user);

    Set<String> roleNames = user.getRoles().stream()
            .map(Role::getName)
            .collect(Collectors.toSet());

    return new AuthResponse(token, user.getUsername(), user.getEmail(), roleNames, user.getId(), null, null, null, null);
}

}
