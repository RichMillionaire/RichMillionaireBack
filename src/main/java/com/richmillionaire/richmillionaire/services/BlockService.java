package com.richmillionaire.richmillionaire.services;

import java.util.List;

import com.richmillionaire.richmillionaire.dto.CreateBlockRequest;
import com.richmillionaire.richmillionaire.models.Block;

public interface BlockService {
    Block findById(int id) throws Exception;
    List<Block> findAll() throws Exception;
    Block save(CreateBlockRequest request) throws Exception;
    void deleteById(int id) throws Exception;    
}
