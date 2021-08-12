package br.com.foursales.demo.service.impl;

import br.com.foursales.demo.crypto.decrypt.DecryptService;
import br.com.foursales.demo.crypto.encrypt.EncryptService;
import br.com.foursales.demo.entity.CandidateEntity;
import br.com.foursales.demo.repository.CandidateRepository;
import br.com.foursales.demo.service.CandidateService;
import br.com.foursales.demo.validator.ValidatorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@AllArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private CandidateRepository repository;
    private ValidatorService validatorService;
    private EncryptService encryptService;
    private DecryptService decryptService;

    @Override
    public void saveCandidate(CandidateEntity candidate) {
        validatorService.validate(candidate);
        encryptService.encrypt(candidate);
        repository.save(candidate);
    }

    @Override
    public CandidateEntity findCandidateById(Integer id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Candidate not found"));
        decryptService.decrypt(entity);
        return entity;
    }

    @Override
    public void deleteCandidateById(Integer id) {
        var entity = findCandidateById(id);
        repository.delete(entity);
    }

    @Override
    public void updateCandidateById(CandidateEntity candidate) {
        findCandidateById(candidate.getId());
        saveCandidate(candidate);
    }

    @Override
    public Iterable<CandidateEntity> findAllCandidates(){
        return repository.findAll();
    }
}
