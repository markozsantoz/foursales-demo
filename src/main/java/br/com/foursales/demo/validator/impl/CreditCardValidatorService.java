package br.com.foursales.demo.validator.impl;

import br.com.foursales.demo.entity.CreditCardEntity;
import br.com.foursales.demo.validator.ValidatorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Log4j2
@Service
public class CreditCardValidatorService implements ValidatorService {

    private ValidatorService nextValidator;

    @Override
    public void validate(Object object) {
        if (object instanceof CreditCardEntity) {
            log.info("Validating CreditCardEntity");
            validate((CreditCardEntity) object);
        } else {
            nextValidator.validate(object);
        }
    }

    @Override
    public void setNextValidator(ValidatorService nextValidator) {
        this.nextValidator = nextValidator;
    }

    private void validate(CreditCardEntity entity) {

        if (isBlank(entity.getName()))
            throw new ResponseStatusException(BAD_REQUEST, "Name is required");

        if (isBlank(entity.getBrand()))
            throw new ResponseStatusException(BAD_REQUEST, "Brand is required");

        if (isBlank(entity.getCardNumber()))
            throw new ResponseStatusException(BAD_REQUEST, "Card Number is required");

        if (isBlank(entity.getCvv()))
            throw new ResponseStatusException(BAD_REQUEST, "CVV is required");

        if (isBlank(entity.getExpirationDate()))
            throw new ResponseStatusException(BAD_REQUEST, "Expiration Date is required");

        log.info("CreditCardEntity informed is valid");
    }

}
