package br.com.foursales.demo.crypto.decrypt.impl;

import br.com.foursales.demo.crypto.decrypt.DecryptService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Primary
public class DecryptServiceImpl implements DecryptService {

    private CandidateEntityDecryptService candidateEntityDecryptService;
    private CreditCardEntityDecryptService creditCardEntityDecryptService;

    @Override
    public void decrypt(Object object) {
        buildChainOfResponsability();
        candidateEntityDecryptService.decrypt(object);
    }

    @Override
    public void setNextDecrypt(DecryptService nextDecrypt) {

    }

    private void buildChainOfResponsability() {
        candidateEntityDecryptService.setNextDecrypt(creditCardEntityDecryptService);
        creditCardEntityDecryptService.setNextDecrypt(null);
    }
}
