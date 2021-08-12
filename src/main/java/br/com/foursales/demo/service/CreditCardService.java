package br.com.foursales.demo.service;

import br.com.foursales.demo.entity.CreditCardEntity;

public interface CreditCardService {
    void saveCreditCard(Integer candidateId, CreditCardEntity creditCard);

    CreditCardEntity findCreditCardById(Integer candidateId, Integer creditCardId);

    void deleteCreditCardById(Integer candidateId, Integer creditCardId);

    void updateCreditCardById(Integer candidateId, CreditCardEntity creditCard);
}
