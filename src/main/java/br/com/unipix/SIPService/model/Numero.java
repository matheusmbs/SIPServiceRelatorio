package br.com.unipix.SIPService.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Numero {
    @Id
    private String numero;
    private String statusChamada;
    private String tipoStatusChamada;
    private String tipoProcessamento;
    private LocalDateTime dataLigacao;
}
