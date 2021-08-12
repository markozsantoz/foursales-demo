package br.com.foursales.demo.validator;

public interface ValidatorService {

    void validate(Object object);

    void setNextValidator(ValidatorService validator);
}
