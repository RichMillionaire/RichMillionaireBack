package com.richmillionaire.richmillionaire.dao;

import java.util.List;

import com.richmillionaire.richmillionaire.models.Block;

public interface BlockDAO {
    void save(Block block) throws Exception; //save block into chain
    List<Block> findAll() throws Exception; //retourne tous les blocks stockés
    Block findById(int id) throws Exception; // retourne un block précis
    void deletebyId(int id) throws Exception;
}
