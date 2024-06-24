package com.rv.cards.service;

import com.rv.cards.dto.CardsDto;
import org.springframework.stereotype.Service;


public interface ICardsService {

    void createCard(String mobileNumber);

    CardsDto fetchCard(String mobileNumber);

    boolean updateCardDetails(CardsDto cardsDto);

    boolean deleteCard(String mobileNumber);
}
