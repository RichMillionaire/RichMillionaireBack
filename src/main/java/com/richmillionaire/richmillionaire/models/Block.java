package com.richmillionaire.richmillionaire.models;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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