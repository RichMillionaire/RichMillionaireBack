package com.richmillionaire.richmillionaire.models;

import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

import com.google.gson.Gson;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
public class Transaction implements Serializable {
    
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID id;

   // on travail en binaire pour signer et vérifier les clés
    @Lob
    @Column(name = "from_address", nullable = false)
    private byte[] from;

    @Column(name = "from_string", nullable = false, length = 2048)
    private String fromString;

    @Lob
    @Column(name = "to_address", nullable = false)
    private byte[] to;

    @Column(name = "to_string", nullable = false, length = 2048)
    private String toString;


    @Column(nullable = false)
    private Integer value;

    @Column(nullable = false)
    private String timestamp;

    @Lob
    private byte[] signature;

    @Column(nullable = false, length = 2048)
    private String signatureString;

    @ManyToOne
    @JoinColumn(name = "block_id", nullable = false)
    private Block block;

    public Transaction(byte[] from, byte[] to, Integer value, byte[] signature, Block block, String timeStamp) {

        Base64.Encoder encoder = Base64.getEncoder();
        this.from = from;
        this.fromString = encoder.encodeToString(from);
        this.to = to;
        this.toString = encoder.encodeToString(to);
        this.value = value;
        this.signature = signature;
        this.signatureString = encoder.encodeToString(signature);
        this.block = block;
        this.timestamp = timeStamp;
    }
    //Constructeur pour signer et créer une nouvelle trans  
    public Transaction(Wallet fromWallet, byte[] toAddress, Integer value, Block block, Signature signing) throws InvalidKeyException, SignatureException, java.security.GeneralSecurityException
    {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] fromBytes = Base64.getDecoder().decode(fromWallet.getPublicKey());
        this.from = fromBytes;
        this.fromString = fromWallet.getPublicKey();
        this.to = toAddress;
        this.toString = encoder.encodeToString(toAddress);
        this.value = value;
        this.block = block;
        this.timestamp = LocalDateTime.now().toString();

        signing.initSign(fromWallet.getDecodedPrivateKey());
        signing.update(this.toString().getBytes());
        this.signature = signing.sign();
        this.signatureString = encoder.encodeToString(this.signature);
    }

    //Verify transac
    public Boolean isVerified(Signature signing) throws InvalidKeyException, SignatureException, NoSuchAlgorithmException, InvalidKeySpecException{
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(getFrom()); //formzat standard pour représenter une clé pub
        KeyFactory keyFactory = KeyFactory.getInstance("DSA"); //KeyFactory sert à transformer des données bin  en vraies clés java
        PublicKey publicKey = keyFactory.generatePublic(keySpec);

        signing.initVerify(publicKey);
        signing.update(this.toString().getBytes());
        return signing.verify(this.getSignature());
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "from=" + Arrays.toString(from) +
                ", to=" + Arrays.toString(to) +
                ", value=" + value +
                ", timeStamp=" + timestamp +
                ", blockId=" + (block != null ? block.getId() : null) +
                '}';
    }

     @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction that)) return false;
        return Arrays.equals(getSignature(), that.getSignature());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getSignature());
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
