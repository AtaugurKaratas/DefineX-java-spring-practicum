package com.example.credit.dto.request;

public record UpdateGuaranteeRequest(
        String guaranteeId,
        boolean approval) {
}