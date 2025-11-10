package com.richmillionaire.richmillionaire.dto;

import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuyArticlesRequestDto {
    private String buyerPublicKey;
    
    private List<UUID> articleIds;
}