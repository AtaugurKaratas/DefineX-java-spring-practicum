package com.example.credit.service.impl;

import com.example.credit.dto.CreditRatingDto;
import com.example.credit.exception.IncorrectCreditRates;
import com.example.credit.model.CreditRating;
import com.example.credit.repository.CreditRatingRepository;
import com.example.credit.service.CreditRatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreditRatingServiceImpl implements CreditRatingService {

    private final CreditRatingRepository creditRatingRepository;

    @Override
    public void saveCurrentCreditRating(CreditRatingDto creditRatingDto) {
        double totalCreditRating = creditRatingDto.creditProductPaymentHabits() +
                creditRatingDto.creditUsageIntensity() +
                creditRatingDto.currentAccountAndDebitStatus() +
                creditRatingDto.newCreditProductLaunches();
        if (totalCreditRating != 100) {
            log.warn("Total Credit Rating: {} - The Sum Of The Odds Is Not 100", totalCreditRating);
            throw new IncorrectCreditRates("The Sum Of The Odds Is Not 100");
        }
        CreditRating creditRating = new CreditRating();
        creditRating.setCreditUsageIntensity(creditRatingDto.creditUsageIntensity());
        creditRating.setNewCreditProductLaunches(creditRatingDto.newCreditProductLaunches());
        creditRating.setCreditProductPaymentHabits(creditRatingDto.creditProductPaymentHabits());
        creditRating.setCurrentAccountAndDebitStatus(creditRatingDto.currentAccountAndDebitStatus());
        creditRating.setCreditScoreStartingValue(creditRatingDto.creditScoreStartingValue());
        creditRatingRepository.save(creditRating);
    }

    @Override
    public CreditRating getCreditRating() {
        return creditRatingRepository.findLastRecord();
    }
}
