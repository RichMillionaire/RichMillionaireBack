package com.richmillionaire.richmillionaire.core;


import java.util.Arrays;

import com.richmillionaire.richmillionaire.models.Block;
import com.richmillionaire.richmillionaire.utils.HashUtil;

public class ProofOfXXXX {
    public String mineBlock(Block block, int prefix) {
        String prefixString = zeros(prefix);
        String hash = HashUtil.calculateBlockHash(block);

        while (!hash.startsWith(prefixString)) {
            block.setNonce(block.getNonce() + 1);
            hash = HashUtil.calculateBlockHash(block);
        }

        block.setHash(hash);
        return hash;
    }

    private String zeros(int n) {
        char[] chars = new char[n];
        Arrays.fill(chars, '0');
        return new String(chars);
    }
}