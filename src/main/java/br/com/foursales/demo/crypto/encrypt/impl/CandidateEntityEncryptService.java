package br.com.foursales.demo.crypto.encrypt.impl;


import br.com.foursales.demo.crypto.AbstractCryptoService;
import br.com.foursales.demo.crypto.encrypt.EncryptService;
import br.com.foursales.demo.entity.CandidateEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;


@Log4j2
@Service
public class CandidateEntityEncryptService extends AbstractCryptoService implements EncryptService {

    private EncryptService nextEncryptor;

    @Value("${encryption.key}")
    private String encryptionKey;

    @Override
    public void encrypt(final Object object) {
        log.info("Starting method encrypt");
        if (object instanceof CandidateEntity) {
            log.info("Encrypting CandidateEntity");
            encrypt((CandidateEntity) object);
        } else {
            nextEncryptor.encrypt(object);
        }
        log.info("Finished method encrypt");
    }

    @Override
    public void setNextEncryptor(EncryptService nextEncryptor) {
        this.nextEncryptor = nextEncryptor;
    }

    private void encrypt(CandidateEntity entity) {
        encryptName(entity);
        encryptEmail(entity);
        entity.getCreditCards().forEach(cc -> nextEncryptor.encrypt(cc));
    }

    private void encryptName(CandidateEntity entity) {
        if (nonNull(entity.getName())) {
            var encrypted = encryptAES(entity.getName(), encryptionKey);
            entity.setNameEncrypted(encrypted);
        }
    }
    private void encryptEmail(CandidateEntity entity) {
        if (nonNull(entity.getEmail())) {
            var encrypted = encryptAES(entity.getEmail(), encryptionKey);
            entity.setEmailEncrypted(encrypted);
        }
    }
}
