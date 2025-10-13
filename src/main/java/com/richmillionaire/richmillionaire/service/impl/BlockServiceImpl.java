package com.richmillionaire.richmillionaire.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.richmillionaire.richmillionaire.dao.BlockDAO;
import com.richmillionaire.richmillionaire.models.Block;
import com.richmillionaire.richmillionaire.service.BlockService;

import jakarta.transaction.Transactional;

@Service
public class BlockServiceImpl implements BlockService{

    private final BlockDAO blockDAO;

    public BlockServiceImpl(BlockDAO blockDAO){
        this.blockDAO = blockDAO;
    }
    
    @Override
    public Block findById(int id) throws Exception{
        return blockDAO.findById(id);
    }

    @Override
    public List<Block> findAll() throws Exception{
        return blockDAO.findAll();
    }

    @Override
    public Block save(Block block) throws Exception{
        return blockDAO.save(block);
    }

    @Override
    @Transactional
    public void deleteById(int id) throws Exception{
        blockDAO.deletebyId(id);        
    }
    
}
