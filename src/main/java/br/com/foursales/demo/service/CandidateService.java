package br.com.foursales.demo.service;

import br.com.foursales.demo.entity.CandidateEntity;

public interface CandidateService {
    void saveCandidate(CandidateEntity candidate);

    CandidateEntity findCandidateById(Integer id);

    void deleteCandidateById(Integer id);

    void updateCandidateById(CandidateEntity candidate);

    Iterable<CandidateEntity> findAllCandidates();
}
