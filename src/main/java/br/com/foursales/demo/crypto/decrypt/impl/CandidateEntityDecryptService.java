package br.com.foursales.demo.crypto.decrypt.impl;


import br.com.foursales.demo.crypto.AbstractCryptoService;
import br.com.foursales.demo.crypto.decrypt.DecryptService;
import br.com.foursales.demo.entity.CandidateEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;


@Log4j2
@Service
public class CandidateEntityDecryptService extends AbstractCryptoService implements DecryptService {

    private DecryptService nextDecrypt;

    @Value("${encryption.key}")
    private String encryptionKey;

    @Override
    public void decrypt(Object object) {
        log.info("Starting method decrypt");
        if (object instanceof CandidateEntity) {
            log.info("Decrypting CandidateEntity");
            decrypt((CandidateEntity) object);
        } else {
            nextDecrypt.decrypt(object);
        }
        log.info("Finished method decrypt");
    }

    @Override
    public void setNextDecrypt(DecryptService nextDecrypt) {
        this.nextDecrypt = nextDecrypt;
    }

    private void decrypt(CandidateEntity entity) {
        decryptName(entity);
        decryptEmail(entity);
        entity.getCreditCards().forEach(cc -> nextDecrypt.decrypt(cc));
    }

    private void decryptName(CandidateEntity entity) {
        if (nonNull(entity.getNameEncrypted())) {
            var decrypted = decryptAES(entity.getNameEncrypted(), encryptionKey);
            entity.setName(decrypted);
        }
    }
    private void decryptEmail(CandidateEntity entity) {
        if (nonNull(entity.getEmailEncrypted())) {
            var decrypted = decryptAES(entity.getEmailEncrypted(), encryptionKey);
            entity.setEmail(decrypted);
        }
    }

}
