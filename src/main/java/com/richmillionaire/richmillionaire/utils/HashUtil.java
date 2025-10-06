package com.richmillionaire.richmillionaire.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.richmillionaire.richmillionaire.models.Block;

public final class HashUtil {
    private HashUtil() {}

    public static String calculateBlockHash(Block block) {
        String dataToHash = block.getPreviousHash()
                + block.getTimeStamp()
                + block.getNonce()
                + block.getData();

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));

            StringBuilder buffer = new StringBuilder();
            for (byte b : bytes) {
                buffer.append(String.format("%02x", b));
            }
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }
}
