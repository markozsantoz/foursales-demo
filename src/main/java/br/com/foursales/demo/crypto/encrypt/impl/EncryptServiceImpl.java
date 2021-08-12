package br.com.foursales.demo.crypto.encrypt.impl;

import br.com.foursales.demo.crypto.encrypt.EncryptService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Primary
public class EncryptServiceImpl implements EncryptService {

    private CandidateEntityEncryptService candidateEntityEncryptService;
    private CreditCardEntityEncryptService creditCardEntityEncryptService;

    @Override
    public void encrypt(Object object) {
        buildChainOfResponsability();
        candidateEntityEncryptService.encrypt(object);
    }

    @Override
    public void setNextEncryptor(EncryptService nextEncryptor) {

    }

    private void buildChainOfResponsability() {
        candidateEntityEncryptService.setNextEncryptor(creditCardEntityEncryptService);
        creditCardEntityEncryptService.setNextEncryptor(null);
    }
}
