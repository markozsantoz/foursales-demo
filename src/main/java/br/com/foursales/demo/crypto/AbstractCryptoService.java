package br.com.foursales.demo.crypto;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Log4j2
@Service
public abstract class AbstractCryptoService {

    @Value("${encryption.ivParameterSpec}")
    private String ivParameterSpec;

    @Value("${encryption.algorithm}")
    private String algorithm;

    @Value("${encryption.transformation}")
    private String transformation;

    public String encryptAES(String text, String key) {
        try {
            var cipher = getCipher(key, Cipher.ENCRYPT_MODE);
            var bytes = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            return new String(Base64.getEncoder().encode(bytes));

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Failed to encrypt", e);
        }
    }

    public String decryptAES(String text, String key) {
        try {
            var cipher = getCipher(key, Cipher.DECRYPT_MODE);
            var bytes = Base64.getDecoder().decode(text.getBytes(StandardCharsets.UTF_8));
            var decValue = cipher.doFinal(bytes);
            return new String(decValue);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Failed to decrypt", e);
        }
    }

    private Cipher getCipher(String key, int decryptMode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        var keySpec = new SecretKeySpec(key.getBytes(), algorithm);
        var param = new IvParameterSpec(ivParameterSpec.getBytes());
        var cipher = Cipher.getInstance(transformation);
        cipher.init(decryptMode, keySpec, param);
        return cipher;
    }
}
