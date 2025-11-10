package com.richmillionaire.richmillionaire.dao;

import java.util.List;

import com.richmillionaire.richmillionaire.models.Block;

public interface BlockDAO {

    Block save(Block block) throws Exception;

    List<Block> findAll() throws Exception; 

    Block findById(int id) throws Exception;
    
    void deletebyId(int id) throws Exception;
    Block findLastBlock() throws Exception;
}
