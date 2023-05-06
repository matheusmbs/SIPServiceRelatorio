package br.com.unipix.SIPService.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.unipix.SIPService.model.StatusSipSMS;


@Repository
public interface StatusSipSMSRepository extends JpaRepository<StatusSipSMS, Integer> {
    
}
