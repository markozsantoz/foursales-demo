package br.com.foursales.demo.controller;

import br.com.foursales.demo.api.CandidateApi;
import br.com.foursales.demo.entity.CandidateEntity;
import br.com.foursales.demo.model.Candidate;
import br.com.foursales.demo.model.CandidateRequest;
import br.com.foursales.demo.model.Candidates;
import br.com.foursales.demo.service.CandidateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Log4j2
@RestController
@AllArgsConstructor
public class CandidateController extends BaseController implements CandidateApi {

    private ObjectMapper mapper;
    private CandidateService service;

    @Override
    public ResponseEntity<Void> createCandidate(@Valid CandidateRequest body) {
        log.info("Starting request POST /candidates");
        var entity = mapper.convertValue(body, CandidateEntity.class);
        service.saveCandidate(entity);
        log.info("Finished request POST /candidates");
        return ResponseEntity.created(getLocation(entity.getId())).build();
    }

    @Override
    public ResponseEntity<Candidate> deleteCandidateById(Integer candidateId) {
        log.info("Starting request DELETE /candidates/{candidateId}");
        service.deleteCandidateById(candidateId);
        log.info("Finished request DELETE /candidates/{candidateId}");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Candidates> findAllCandidates() {
        log.info("Starting request GET /candidates");
        var entities = service.findAllCandidates();
        var candidates = new Candidates();
        entities.forEach(e -> candidates.add(mapper.convertValue(e, Candidate.class)));
        log.info("Finished request GET /candidates");
        return ResponseEntity.ok(candidates);
    }

    @Override
    public ResponseEntity<Candidate> findCandidateById(Integer candidateId) {
        log.info("Starting request GET /candidates/{candidateId}");
        var entity = service.findCandidateById(candidateId);
        var candidate = mapper.convertValue(entity, Candidate.class);
        log.info("Finished request GET /candidates/{candidateId}");
        return ResponseEntity.ok(candidate);
    }

    @Override
    public ResponseEntity<Void> updateCandidateById(Integer candidateId, @Valid CandidateRequest body) {
        log.info("Starting request PUT /candidates/{candidateId}");
        var entity = mapper.convertValue(body, CandidateEntity.class);
        entity.setId(candidateId);
        service.updateCandidateById(entity);
        log.info("Finished request PUT /candidates/{candidateId}");
        return ResponseEntity.noContent().build();
    }

}
