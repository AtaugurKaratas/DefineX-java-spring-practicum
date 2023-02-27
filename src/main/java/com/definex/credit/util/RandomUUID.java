package com.definex.credit.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RandomUUID {

    public String sendRandomValue(){
        return generateRandomUUID();
    }

    public String generateRandomUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}