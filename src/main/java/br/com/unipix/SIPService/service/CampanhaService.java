package br.com.unipix.SIPService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.unipix.SIPService.model.Campanha;
import br.com.unipix.SIPService.repository.CampanhaRepository;

@Service
public class CampanhaService {
    @Autowired
    CampanhaRepository campanhaRepository;

    public List<Campanha> buscarCampanha(Long campanhaId){
        return this.campanhaRepository.findCampanhaByIdCampanha(campanhaId);
    }
}
