package com.tutorial.finaldemo.dto;

import lombok.Data;

@Data
public class CreateBlockRequest {
    private int newData;

    private String contractAddress;
}