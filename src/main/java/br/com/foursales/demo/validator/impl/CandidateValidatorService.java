package br.com.foursales.demo.validator.impl;

import br.com.foursales.demo.entity.CandidateEntity;
import br.com.foursales.demo.validator.ValidatorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Log4j2
@Service
public class CandidateValidatorService implements ValidatorService {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-\\+]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private ValidatorService nextValidator;

    @Override
    public void validate(Object object) {
        if (object instanceof CandidateEntity) {
            log.info("Validating CandidateEntity");
            validate((CandidateEntity) object);
        } else {
            nextValidator.validate(object);
        }
    }

    @Override
    public void setNextValidator(ValidatorService nextValidator) {
        this.nextValidator = nextValidator;
    }

    private void validate(CandidateEntity entity) {

        if (isBlank(entity.getName()))
            throw new ResponseStatusException(BAD_REQUEST, "Name is required");

        if (isBlank(entity.getEmail()))
            throw new ResponseStatusException(BAD_REQUEST, "E-mail is required");

        if (isEmailInvalid(entity.getEmail()))
            throw new ResponseStatusException(BAD_REQUEST, "E-mail is invalid");

        log.info("CandidateEntity informed is valid");
    }

    private static boolean isEmailInvalid(final String email) {
        if (email == null)
            return false;
        var pattern = Pattern.compile(EMAIL_PATTERN);
        var matcher = pattern.matcher(email);
        return !matcher.find();
    }
}
