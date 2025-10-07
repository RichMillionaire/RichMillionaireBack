package com.richmillionaire.richmillionaire.models;


import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Table;



@Entity
@Table(name = "wallets")
@Getter
@Setter
public class Wallet {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2048)
    private String publicKey;
    //normalement pas stockée mais pour simplicité du projet letsgo (secu pas le + improtant la)
    @Column(nullable = false, length = 2048)
    private String privateKey;

    public static final int KEY_SIZE = 2048;

    public Wallet() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DSA");
        keyPairGen.initialize(KEY_SIZE);
        KeyPair keyPair = keyPairGen.generateKeyPair();

        Base64.Encoder encoder = Base64.getEncoder();
        this.publicKey = encoder.encodeToString(keyPair.getPublic().getEncoded());
        this.privateKey = encoder.encodeToString(keyPair.getPrivate().getEncoded());
    }

    public Wallet(PublicKey pubKey, PrivateKey privKey) {
        Base64.Encoder encoder = Base64.getEncoder();
        this.publicKey = encoder.encodeToString(pubKey.getEncoded());
        this.privateKey = encoder.encodeToString(privKey.getEncoded());
    }

    public PublicKey getDecodedPublicKey() throws GeneralSecurityException {
        byte[] keyBytes = Base64.getDecoder().decode(this.publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance("DSA");
        return keyFactory.generatePublic(new java.security.spec.X509EncodedKeySpec(keyBytes));
    }

    public PrivateKey getDecodedPrivateKey() throws GeneralSecurityException {
        byte[] keyBytes = Base64.getDecoder().decode(this.privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance("DSA");
        return keyFactory.generatePrivate(new java.security.spec.PKCS8EncodedKeySpec(keyBytes));
    }

    public String toJson() {
        return new com.google.gson.Gson().toJson(this);
    }
}
