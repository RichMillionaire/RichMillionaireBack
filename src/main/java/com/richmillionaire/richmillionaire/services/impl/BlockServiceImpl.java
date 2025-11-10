package com.richmillionaire.richmillionaire.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.richmillionaire.richmillionaire.dao.BlockDAO;
import com.richmillionaire.richmillionaire.dto.CreateBlockRequest;
import com.richmillionaire.richmillionaire.models.Block;
import com.richmillionaire.richmillionaire.services.BlockService;

import jakarta.transaction.Transactional;

@Service
public class BlockServiceImpl implements BlockService{

    private final BlockDAO blockDAO;
    private static final int DIFFICULTY = 0;

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
    @Transactional
    public Block save(CreateBlockRequest request) throws Exception{
        
        Block newBlock = new Block();
        newBlock.setData(request.getData()); 

        
        newBlock.setTimeStamp(System.currentTimeMillis());

        
        Block lastBlock = blockDAO.findLastBlock();

        if (lastBlock == null) {
            newBlock.setPreviousHash("0");
        } else {
            newBlock.setPreviousHash(lastBlock.getCurrHash()); 
        }

        newBlock.setMinedBy(request.getMinedBy());
        newBlock.setNonce(0);

        // === LE SERVEUR MINE LE BLOC ===
        newBlock.mineBlock(DIFFICULTY);
        
        // On sauvegarde le bloc qui a été miné
        return blockDAO.save(newBlock);
    }

    @Override
    @Transactional
    public void deleteById(int id) throws Exception{
        blockDAO.deletebyId(id);        
    }
    @Override
    @Transactional
    public Block update(Block block) throws Exception {
        return blockDAO.save(block); 
    }
    
}
