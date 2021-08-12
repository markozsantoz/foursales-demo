package br.com.foursales.demo.repository;

import br.com.foursales.demo.entity.CandidateEntity;
import br.com.foursales.demo.entity.CreditCardEntity;
import org.springframework.data.repository.CrudRepository;

public interface CreditCardRepository extends CrudRepository<CreditCardEntity, Integer> {
}
