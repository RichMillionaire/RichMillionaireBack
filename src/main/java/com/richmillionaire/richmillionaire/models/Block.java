package com.richmillionaire.richmillionaire.models;

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

}