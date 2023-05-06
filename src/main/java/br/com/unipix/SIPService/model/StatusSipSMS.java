package br.com.unipix.SIPService.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "tb_status_sip_sms")
@Data
public class StatusSipSMS {
    @Id
    @Column(name = "sip")
    private Integer sip;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "periodoValidacao")
    private Integer periodoValidacao;

    @Column(name = "tipoPeriodoValidacao")
    private String tipoPeriodoValidacao;

    @Column(name = "status")
    private String status; 
}