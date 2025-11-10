package com.richmillionaire.richmillionaire.models;

import java.security.MessageDigest;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "block") // table name
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Block {

    
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String currHash;

    @Column(nullable = false)
    private String previousHash;

    @Column(nullable = false)
    private String data;

    @Column(nullable = false)
    private long timeStamp;

    @Column(nullable = false)
    private int nonce;

    @Column(nullable = false)
    private String minedBy;

    public String calculateHash() {
        try {
            String dataToHash = this.previousHash 
                                + Long.toString(this.timeStamp) 
                                + this.data 
                                + Integer.toString(this.nonce);
            
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(dataToHash.getBytes("UTF-8"));
            
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < hashBytes.length; i++) {
                String hex = Integer.toHexString(0xff & hashBytes[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du calcul du hash: " + e.getMessage());
        }
    }

    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');
        
        this.currHash = calculateHash();
        
        while(!this.currHash.substring(0, difficulty).equals(target)) {
            this.nonce++;
            this.currHash = calculateHash();
        }
        
        System.out.println("Bloc miné ! Hash: " + this.currHash);
    }
}