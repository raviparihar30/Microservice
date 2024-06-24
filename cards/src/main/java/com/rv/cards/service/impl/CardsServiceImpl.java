package com.rv.cards.service.impl;

import com.rv.cards.constants.CardsConstants;
import com.rv.cards.dto.CardsDto;
import com.rv.cards.entity.Cards;
import com.rv.cards.exception.CardAlreadyExistsException;
import com.rv.cards.exception.ResourceNotFoundException;
import com.rv.cards.mapper.CardsMapper;
import com.rv.cards.repository.CardsRepository;
import com.rv.cards.service.ICardsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {

    CardsRepository cardsRepository;

    @Override
    public void createCard(String mobileNumber) {
        Optional<Cards> optionalCards=cardsRepository.findByMobileNumber(mobileNumber);
        if(optionalCards.isPresent()){
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber "+mobileNumber);
        }
        cardsRepository.save(createNewCard(mobileNumber));
    }

    @Override
    public CardsDto fetchCard(String mobileNumber) {
        Cards cards=cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(()->new ResourceNotFoundException("Card","mobileNumber",mobileNumber));
        return CardsMapper.mapToCardsDto(cards,new CardsDto());
    }

    @Override
    public boolean updateCardDetails(CardsDto cardsDto) {
        Cards cards=cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(()->new ResourceNotFoundException("Card","CardNumber",cardsDto.getCardNumber()));
        CardsMapper.mapToCards(cardsDto,cards);
        cardsRepository.save(cards);
        return true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        Cards cards=cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(()->new ResourceNotFoundException("Card","MobileNumber",mobileNumber));
        cardsRepository.findById(cards.getCardId());
        return true;
    }


    private Cards createNewCard(String mobileNumber)
    {
        Cards newCards=new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCards.setCardNumber(Long.toString(randomCardNumber));
        newCards.setMobileNumber(mobileNumber);
        newCards.setCardType(CardsConstants.CREDIT_CARD);
        newCards.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCards.setAmountUsed(0);
        newCards.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return newCards;
    }

}
