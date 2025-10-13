package com.richmillionaire.richmillionaire.service;

import java.util.List;

import com.richmillionaire.richmillionaire.models.Block;

public interface BlockService {
    Block findById(int id) throws Exception;
    List<Block> findAll() throws Exception;
    Block save(Block block) throws Exception;
    void deleteById(int id) throws Exception;    
}
