package br.com.foursales.demo.validator.impl;

import br.com.foursales.demo.validator.ValidatorService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@AllArgsConstructor
public class ValidatorServiceImpl implements ValidatorService {

    private CandidateValidatorService candidateValidatorService;
    private CreditCardValidatorService creditCardValidatorService;

    @Override
    public void validate(Object object) {
        buildChainOfResponsability();
        candidateValidatorService.validate(object);
    }

    @Override
    public void setNextValidator(ValidatorService validator) {

    }

    private void buildChainOfResponsability() {
        candidateValidatorService.setNextValidator(creditCardValidatorService);
        creditCardValidatorService.setNextValidator(null);
    }
}
