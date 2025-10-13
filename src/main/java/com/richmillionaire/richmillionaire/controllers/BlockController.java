package com.richmillionaire.richmillionaire.controllers;

import com.richmillionaire.richmillionaire.models.Block;
import com.richmillionaire.richmillionaire.service.BlockService;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/blocks")
public class BlockController {

    private final BlockService blockService;

    public BlockController(BlockService blockService) {
        this.blockService = blockService;
    }
    @GetMapping("")
    public List<Block> getAllBlocks() throws Exception{
        return blockService.findAll();
    }
    @GetMapping("/{id}")
    public Block getBlock(int id) throws Exception {
        return blockService.findById(id);
    }
    @PostMapping("")
    public Block createBlock(Block block) throws Exception {
        return blockService.save(block);
    }
    @DeleteMapping("/{id}")
    public void deleteBlock(int id) throws Exception {
        blockService.deleteById(id);
    }
}
