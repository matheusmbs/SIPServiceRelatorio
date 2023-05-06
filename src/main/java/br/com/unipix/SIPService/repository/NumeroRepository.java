package br.com.unipix.SIPService.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.unipix.SIPService.model.Numero;

@Repository
public interface NumeroRepository  extends MongoRepository<Numero, String> {
    @Query(value = "{ 'numero': { $in: ?0 }}")
    public List<Numero> findByNumeros(List<String> numeros);
}
