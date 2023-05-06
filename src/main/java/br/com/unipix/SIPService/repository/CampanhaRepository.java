package br.com.unipix.SIPService.repository;

import org.springframework.stereotype.Repository;

import br.com.unipix.SIPService.model.Campanha;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

@Repository
public interface CampanhaRepository extends MongoRepository<Campanha, String>{

    @Query("{ 'idCampanhaSql' : ?0}")
    List<Campanha> findCampanhaByIdCampanha(Long idCampanha);
}
