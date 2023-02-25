package com.example.credit.util;

import com.example.credit.dto.CreditResultDto;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class SendSms {

    public void sendUnsuccessfulCreditResult(CreditResultDto creditResultDto){
        Message.creator(
                new PhoneNumber(creditResultDto.phoneNumber()),
                "MGe215f4ced8653a69cd14f8e807e02daa",
                MessageFormat.format("Mr. {0} {1}, your credit application has been rejected"
                        ,creditResultDto.customerName()
                        ,creditResultDto.customerSurname())
        ).create();
    }

    public void sendSuccessfulCreditResult(CreditResultDto creditResultDto){
        Message.creator(
                new PhoneNumber(creditResultDto.phoneNumber()),
                "MGe215f4ced8653a69cd14f8e807e02daa",
                MessageFormat.format("Mr. {0} {1}, your credit application has been accepted." +
                                "Your credit limit is {2} TL"
                        ,creditResultDto.customerName()
                        ,creditResultDto.customerSurname()
                        ,creditResultDto.creditLimit().doubleValue())
        ).create();
    }

}
