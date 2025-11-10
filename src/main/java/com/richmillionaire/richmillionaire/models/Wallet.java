package com.richmillionaire.richmillionaire.models;


import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Table;



@Entity
@Table(name = "wallets")
@Getter
@Setter
public class Wallet {
    
    
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "public_key", nullable = false, length = 2048)
    private String publicKey;

    @Column(name = "private_key", nullable = false, length = 2048)
    private String privateKey;

    @Column(nullable = false)
    private Double balance = 0.0;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;


    public static final int KEY_SIZE = 2048;

    public Wallet() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DSA");
        keyPairGen.initialize(KEY_SIZE);
        KeyPair keyPair = keyPairGen.generateKeyPair();

        Base64.Encoder encoder = Base64.getEncoder();
        this.publicKey = encoder.encodeToString(keyPair.getPublic().getEncoded());
        this.privateKey = encoder.encodeToString(keyPair.getPrivate().getEncoded());
        this.balance = 0.0;
    }

    public Wallet(PublicKey pubKey, PrivateKey privKey) {
        Base64.Encoder encoder = Base64.getEncoder();
        this.publicKey = encoder.encodeToString(pubKey.getEncoded());
        this.privateKey = encoder.encodeToString(privKey.getEncoded());
    }
    @JsonIgnore
    public PublicKey getDecodedPublicKey() throws GeneralSecurityException {
        byte[] keyBytes = Base64.getDecoder().decode(this.publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance("DSA");
        return keyFactory.generatePublic(new java.security.spec.X509EncodedKeySpec(keyBytes));
    }
    @JsonIgnore
    public PrivateKey getDecodedPrivateKey() throws GeneralSecurityException {
        byte[] keyBytes = Base64.getDecoder().decode(this.privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance("DSA");
        return keyFactory.generatePrivate(new java.security.spec.PKCS8EncodedKeySpec(keyBytes));
    }

    public String toJson() {
        return new com.google.gson.Gson().toJson(this);
    }    
}
