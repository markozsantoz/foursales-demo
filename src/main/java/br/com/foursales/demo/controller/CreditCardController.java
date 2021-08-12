package br.com.foursales.demo.controller;

import br.com.foursales.demo.api.CreditCardApi;
import br.com.foursales.demo.entity.CreditCardEntity;
import br.com.foursales.demo.model.CreditCard;
import br.com.foursales.demo.model.CreditCardRequest;
import br.com.foursales.demo.service.CreditCardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Log4j2
@RestController
@AllArgsConstructor
public class CreditCardController extends BaseController implements CreditCardApi {

    private ObjectMapper mapper;
    private CreditCardService service;

    @Override
    public ResponseEntity<Void> createCreditCard(Integer candidateId, @Valid CreditCardRequest body) {
        log.info("Starting request POST /candidates/{candidateId}/credit-cards");
        var entity = mapper.convertValue(body, CreditCardEntity.class);
        service.saveCreditCard(candidateId, entity);
        log.info("Finished request POST /candidates/{candidateId}/credit-cards");
        return ResponseEntity.created(getLocation(entity.getId())).build();
    }

    @Override
    public ResponseEntity<Void> deleteCreditCard(Integer candidateId, Integer creditCardId) {
        log.info("Starting request DELETE /candidates/{candidateId}/credit-cards/{creditCardId}");
        service.deleteCreditCardById(candidateId, creditCardId);
        log.info("Finished request DELETE /candidates/{candidateId}/credit-cards/{creditCardId}");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<CreditCard> getCreditCard(Integer candidateId, Integer creditCardId) {
        log.info("Starting request GET /candidates/{candidateId}/credit-cards/{creditCardId}");
        var entity = service.findCreditCardById(candidateId, creditCardId);
        var creditCard = mapper.convertValue(entity, CreditCard.class);
        log.info("Finished request GET /candidates/{candidateId}/credit-cards/{creditCardId}");
        return ResponseEntity.ok(creditCard);
    }

    @Override
    public ResponseEntity<Void> updateCreditCard(Integer candidateId, Integer creditCardId, @Valid CreditCardRequest body) {
        log.info("Starting request PUT /candidates/{candidateId}/credit-cards/{creditCardId}");
        var entity = mapper.convertValue(body, CreditCardEntity.class);
        entity.setId(creditCardId);
        service.updateCreditCardById(candidateId, entity);
        log.info("Finished request PUT /candidates/{candidateId}/credit-cards/{creditCardId}");
        return ResponseEntity.noContent().build();
    }
}
