package com.definex.credit.dto.request;

public record UpdateGuaranteeRequest(
        String guaranteeId,
        boolean approval) {
}