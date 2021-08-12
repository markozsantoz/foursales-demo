package br.com.foursales.demo.repository;

import br.com.foursales.demo.entity.CandidateEntity;
import org.springframework.data.repository.CrudRepository;

public interface CandidateRepository extends CrudRepository<CandidateEntity, Integer> {
}
