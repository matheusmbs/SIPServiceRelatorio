package br.com.unipix.SIPService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.unipix.SIPService.model.StatusSipSMS;
import br.com.unipix.SIPService.repository.StatusSipSMSRepository;

@Service
public class StatusSipService {
    @Autowired
    private StatusSipSMSRepository statusSipSMSRepository;

    public List<StatusSipSMS> findAll() {
        return this.statusSipSMSRepository.findAll();
    }
}
