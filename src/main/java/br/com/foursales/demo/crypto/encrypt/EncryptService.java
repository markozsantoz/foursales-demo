package br.com.foursales.demo.crypto.encrypt;

public interface EncryptService {

    void encrypt(Object object);

    void setNextEncryptor(EncryptService nextEncryptor);

}
