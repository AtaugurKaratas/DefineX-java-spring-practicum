package com.definex.credit.service.impl;

import com.definex.credit.dto.CreditRatingDto;
import com.definex.credit.exception.IncorrectCreditRates;
import com.definex.credit.model.CreditRating;
import com.definex.credit.repository.CreditRatingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditRatingServiceImplTest {

    @InjectMocks
    private CreditRatingServiceImpl creditRatingService;

    @Mock
    private CreditRatingRepository creditRatingRepository;

    @Test
    void ShouldThrownNotFoundExceptionWhenRateSumIsNotEqual100() {
        CreditRatingDto creditRatingDto = new CreditRatingDto(24, 25, 25, 25, 250);
        IncorrectCreditRates exception = assertThrows(IncorrectCreditRates.class, () ->
                creditRatingService.saveCurrentCreditRating(creditRatingDto));

        assertEquals("The Sum Of The Odds Is Not 100", exception.getMessage());
    }

    @Test
    void ShouldSaveCurrentCreditRating() {
        CreditRatingDto creditRatingDto = new CreditRatingDto(25, 25, 25, 25, 250);

        CreditRating creditRating = new CreditRating();
        creditRating.setCreditUsageIntensity(creditRatingDto.creditUsageIntensity());
        creditRating.setNewCreditProductLaunches(creditRatingDto.newCreditProductLaunches());
        creditRating.setCreditProductPaymentHabits(creditRatingDto.creditProductPaymentHabits());
        creditRating.setCurrentAccountAndDebitStatus(creditRatingDto.currentAccountAndDebitStatus());
        creditRating.setCreditScoreStartingValue(creditRatingDto.creditScoreStartingValue());

        when(creditRatingRepository.save(creditRating)).thenReturn(creditRating);
        creditRatingService.saveCurrentCreditRating(creditRatingDto);

        verify(creditRatingRepository).save(creditRating);
    }

    @Test
    void ShouldGetCreditRating() {
        creditRatingService.getCreditRating();
        verify(creditRatingRepository).findLastRecord();
    }
}