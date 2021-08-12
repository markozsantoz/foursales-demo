package br.com.foursales.demo.service.impl;

import br.com.foursales.demo.crypto.decrypt.DecryptService;
import br.com.foursales.demo.crypto.encrypt.EncryptService;
import br.com.foursales.demo.entity.CreditCardEntity;
import br.com.foursales.demo.repository.CreditCardRepository;
import br.com.foursales.demo.service.CandidateService;
import br.com.foursales.demo.service.CreditCardService;
import br.com.foursales.demo.validator.ValidatorService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Log4j2
@Service
@AllArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

    private CandidateService candidateService;
    private ValidatorService validatorService;
    private EncryptService encryptService;
    private DecryptService decryptService;
    private CreditCardRepository repository;

    @Override
    public void saveCreditCard(Integer candidateId, CreditCardEntity creditCard) {
        var candidate = candidateService.findCandidateById(candidateId);
        validatorService.validate(creditCard);
        encryptService.encrypt(creditCard);
        creditCard.setCandidate(candidate);
        repository.save(creditCard);
    }

    @Override
    public CreditCardEntity findCreditCardById(Integer candidateId, Integer creditCardId) {
        var entity = repository.findById(creditCardId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Credit Card not found"));
        if (!entity.getCandidate().getId().equals(candidateId))
            throw new ResponseStatusException(FORBIDDEN, "Credit Card not belongs to informed candidate");
        decryptService.decrypt(entity);
        return entity;
    }

    @Override
    public void deleteCreditCardById(Integer candidateId, Integer creditCardId) {
        var entity = findCreditCardById(candidateId, creditCardId);
        repository.delete(entity);
    }

    @Override
    public void updateCreditCardById(Integer candidateId, CreditCardEntity creditCard) {
        findCreditCardById(candidateId, creditCard.getId());
        saveCreditCard(candidateId, creditCard);
    }
}
