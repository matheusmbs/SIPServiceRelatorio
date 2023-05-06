package br.com.unipix.SIPService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.unipix.SIPService.model.Numero;
import br.com.unipix.SIPService.repository.NumeroRepository;

@Service
public class NumeroService {

    @Autowired
    NumeroRepository numeroRepository;
    
    public List<Numero> buscarNumeros(List<String> numeros){
        return this.numeroRepository.findByNumeros(numeros);
    }
}