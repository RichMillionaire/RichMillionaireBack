package com.richmillionaire.richmillionaire.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.richmillionaire.richmillionaire.dto.ApiResponse;
import com.richmillionaire.richmillionaire.models.User;
import com.richmillionaire.richmillionaire.models.Wallet;
import com.richmillionaire.richmillionaire.services.WalletService;
import com.richmillionaire.richmillionaire.models.Transaction;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping(("/wallets"))
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }
    @GetMapping("")
    public ResponseEntity<ApiResponse<List<Wallet>>> getAllWallets(HttpServletRequest request) {
        try {
            User user = (User) request.getAttribute("user");
            if (user == null) {
                return ResponseEntity.status(401)
                    .body(ApiResponse.error("Utilisateur non authentifié"));
            }
            
            // Retourner uniquement les wallets de l'utilisateur connecté
            List<Wallet> wallets = walletService.findByUser(user);
            return ResponseEntity.ok(ApiResponse.success("Wallets récupérés avec succès", wallets));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error("Erreur lors de la récupération des wallets: " + e.getMessage()));
        }
    }
    @GetMapping("/{id}")
    public Wallet getWallet(String id) throws Exception {
        return walletService.findById(id);
    }
    @PostMapping("")
    public ResponseEntity<ApiResponse<Wallet>> createWallet(HttpServletRequest request) {
        try {
            User user = (User) request.getAttribute("user");
            if (user == null) {
                return ResponseEntity.status(401)
                    .body(ApiResponse.error("Utilisateur non authentifié"));
            }
            
            Wallet wallet = walletService.createWalletForUser(user);
            return ResponseEntity.ok(ApiResponse.success("Wallet créé avec succès", wallet));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error("Erreur lors de la création du wallet: " + e.getMessage()));
        }
    }

    @GetMapping("/my-wallet")
    public ResponseEntity<ApiResponse<Wallet>> getMyWallet(HttpServletRequest request) {
        try {
            User user = (User) request.getAttribute("user");
            if (user == null) {
                return ResponseEntity.status(401)
                    .body(ApiResponse.error("Utilisateur non authentifié"));
            }
            
            List<Wallet> wallets = walletService.findByUser(user);
            if (wallets.isEmpty()) {
                return ResponseEntity.status(404)
                    .body(ApiResponse.error("Aucun wallet trouvé pour cet utilisateur"));
            }
            
            return ResponseEntity.ok(ApiResponse.success("Wallet récupéré avec succès", wallets.get(0)));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error("Erreur lors de la récupération du wallet: " + e.getMessage()));
        }
    }

    @GetMapping("/my-wallets")
    public ResponseEntity<ApiResponse<List<Wallet>>> getMyWallets(HttpServletRequest request) {
        try {
            User user = (User) request.getAttribute("user");
            if (user == null) {
                return ResponseEntity.status(401)
                    .body(ApiResponse.error("Utilisateur non authentifié"));
            }
            
            List<Wallet> wallets = walletService.findByUser(user);
            return ResponseEntity.ok(ApiResponse.success("Wallets récupérés avec succès", wallets));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error("Erreur lors de la récupération des wallets: " + e.getMessage()));
        }
    }

    @GetMapping("/admin/all")
    public ResponseEntity<ApiResponse<List<Wallet>>> getAllWalletsAdmin(HttpServletRequest request) {
        try {
            User user = (User) request.getAttribute("user");
            if (user == null) {
                return ResponseEntity.status(401)
                    .body(ApiResponse.error("Utilisateur non authentifié"));
            }
            
            // Vérifier si l'utilisateur est admin
            boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ADMIN"));
            
            if (!isAdmin) {
                return ResponseEntity.status(403)
                    .body(ApiResponse.error("Accès interdit : droits administrateur requis"));
            }
            
            // Retourner tous les wallets de tous les utilisateurs
            List<Wallet> wallets = walletService.findAll();
            return ResponseEntity.ok(ApiResponse.success("Tous les wallets récupérés avec succès", wallets));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error("Erreur lors de la récupération des wallets: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public void deleteWallet(String id) throws Exception {
        walletService.deleteById(id);
    }

    @PostMapping("/transfer")
    public Transaction transfer(@RequestParam String fromPublicKey, @RequestParam String toPublicKey, @RequestParam double amount) throws Exception{
        return walletService.transfer(fromPublicKey, toPublicKey, amount);
    }
}
