package br.com.foursales.demo.crypto.decrypt;

public interface DecryptService {

    void decrypt(Object object);

    void setNextDecrypt(DecryptService nextDecrypt);

}