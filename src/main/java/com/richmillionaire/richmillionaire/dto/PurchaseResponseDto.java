package com.richmillionaire.richmillionaire.dto;

import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseResponseDto {
    private String message;
    private UUID transactionId;
    private Double totalPaid;
    private List<PurchasedArticleCodeDto> purchasedArticles;
}