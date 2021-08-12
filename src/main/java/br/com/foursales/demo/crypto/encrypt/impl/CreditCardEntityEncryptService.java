package br.com.foursales.demo.crypto.encrypt.impl;


import br.com.foursales.demo.crypto.AbstractCryptoService;
import br.com.foursales.demo.crypto.encrypt.EncryptService;
import br.com.foursales.demo.entity.CandidateEntity;
import br.com.foursales.demo.entity.CreditCardEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;


@Log4j2
@Service
public class CreditCardEntityEncryptService extends AbstractCryptoService implements EncryptService {

    private EncryptService nextEncryptor;

    @Value("${encryption.key}")
    private String encryptionKey;

    @Override
    public void encrypt(Object object) {
        log.info("Starting method encrypt");
        if (object instanceof CreditCardEntity) {
            log.info("Encrypting CandidateEntity");
            encrypt((CreditCardEntity) object);
        } else {
            nextEncryptor.encrypt(object);
        }
        log.info("Finished method encrypt");
    }

    @Override
    public void setNextEncryptor(EncryptService nextEncryptor) {
        this.nextEncryptor = nextEncryptor;
    }

    private void encrypt(CreditCardEntity entity) {
        encryptName(entity);
        encryptCardNumber(entity);
        encryptCvv(entity);
        encryptExpirationDate(entity);
    }

    private void encryptName(CreditCardEntity entity) {
        if (nonNull(entity.getName())) {
            var encrypted = encryptAES(entity.getName(), encryptionKey);
            entity.setNameEncrypted(encrypted);
        }
    }

    private void encryptCardNumber(CreditCardEntity entity) {
        if (nonNull(entity.getCardNumber())) {
            var encrypted = encryptAES(entity.getCardNumber(), encryptionKey);
            entity.setCardNumberEncrypted(encrypted);
        }
    }

    private void encryptCvv(CreditCardEntity entity) {
        if (nonNull(entity.getCvv())) {
            var encrypted = encryptAES(entity.getCvv(), encryptionKey);
            entity.setCvvEncrypted(encrypted);
        }
    }

    private void encryptExpirationDate(CreditCardEntity entity) {
        if (nonNull(entity.getExpirationDate())) {
            var encrypted = encryptAES(entity.getExpirationDate(), encryptionKey);
            entity.setExpirationDateEncrypted(encrypted);
        }
    }
}
