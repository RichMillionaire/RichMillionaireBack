package com.richmillionaire.richmillionaire.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PurchasedArticleCodeDto {
    private UUID articleId;
    private String articleName;
    private String code;
}