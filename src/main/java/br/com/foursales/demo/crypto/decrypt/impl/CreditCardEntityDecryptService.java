package br.com.foursales.demo.crypto.decrypt.impl;


import br.com.foursales.demo.crypto.AbstractCryptoService;
import br.com.foursales.demo.crypto.decrypt.DecryptService;
import br.com.foursales.demo.entity.CandidateEntity;
import br.com.foursales.demo.entity.CreditCardEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;


@Log4j2
@Service
public class CreditCardEntityDecryptService extends AbstractCryptoService implements DecryptService {

    private DecryptService nextDecrypt;

    @Value("${encryption.key}")
    private String encryptionKey;

    @Override
    public void decrypt(Object object) {
        log.info("Starting method decrypt");
        if (object instanceof CreditCardEntity) {
            log.info("Decrypting CandidateEntity");
            decrypt((CreditCardEntity) object);
        } else {
            nextDecrypt.decrypt(object);
        }
        log.info("Finished method decrypt");
    }

    @Override
    public void setNextDecrypt(DecryptService nextDecrypt) {
        this.nextDecrypt = nextDecrypt;
    }

    private void decrypt(CreditCardEntity entity) {
        decryptName(entity);
        decryptCardNumber(entity);
        decryptCvv(entity);
        decryptExpirationDate(entity);
    }

    private void decryptName(CreditCardEntity entity) {
        if (nonNull(entity.getNameEncrypted())) {
            var decrypted = decryptAES(entity.getNameEncrypted(), encryptionKey);
            entity.setName(decrypted);
        }
    }

    private void decryptCardNumber(CreditCardEntity entity) {
        if (nonNull(entity.getCardNumberEncrypted())) {
            var decrypted = decryptAES(entity.getCardNumberEncrypted(), encryptionKey);
            entity.setCardNumber(decrypted);
        }
    }

    private void decryptCvv(CreditCardEntity entity) {
        if (nonNull(entity.getCvvEncrypted())) {
            var decrypted = decryptAES(entity.getCvvEncrypted(), encryptionKey);
            entity.setCvv(decrypted);
        }
    }

    private void decryptExpirationDate(CreditCardEntity entity) {
        if (nonNull(entity.getExpirationDateEncrypted())) {
            var decrypted = decryptAES(entity.getExpirationDateEncrypted(), encryptionKey);
            entity.setExpirationDate(decrypted);
        }
    }

}
