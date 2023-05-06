package br.com.unipix.SIPService;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.unipix.SIPService.model.Campanha;
import br.com.unipix.SIPService.model.Numero;
import br.com.unipix.SIPService.model.StatusSipSMS;
import br.com.unipix.SIPService.service.ArquivoService;
import br.com.unipix.SIPService.service.CampanhaService;
import br.com.unipix.SIPService.service.NumeroService;
import br.com.unipix.SIPService.service.StatusSipService;

@SpringBootApplication
public class SipServiceApplication implements CommandLineRunner {

	@Autowired
	NumeroService numeroService;

	@Autowired
	CampanhaService campanhaService;

	@Autowired
	StatusSipService statusSipService;

	public static void main(String[] args) {
		SpringApplication.run(SipServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Iniciando execução");
		List<String> numeros = ArquivoService.lerArquivo("numeros.txt");
		List<String> cdrs = ArquivoService.lerArquivo("cdr.txt");

		List<Numero> numerosCDR = this.numeroService.buscarNumeros(numeros);

		List<Campanha> campanhas = this.campanhaService.buscarCampanha(Long.valueOf(8514479));

		List<StatusSipSMS> statusSipSMSsList = this.statusSipService.findAll();

		ArquivoService.montarArquivoResultado(
				"Número,Status SMS,Local CDR,Houve Discagem,Status SIP,Data inicial ligação,Data final ligação,Duração,Status SIP para SMS");

		for (String numero : numeros) {
			Campanha campanha = campanhas.stream().filter(c -> c.getNumero().equals(numero)).findFirst().orElse(null);
			Numero numeroCDR = numerosCDR.stream().filter(n -> n.getNumero().equals(numero)).findFirst().orElse(null);
			String cdr = null;
			String text = numero + "," + campanha.getStatus() + ",";

			if (numeroCDR != null) {
				if (numeroCDR.getTipoProcessamento().equals("CDR")) {
					text += "CDR Plataformas,Não," + numeroCDR.getStatusChamada() + "," + numeroCDR.getDataLigacao()
							+ ",-,-," + this.validarStatusSMS(numeroCDR, statusSipSMSsList);
				}

				if (numeroCDR.getTipoProcessamento().equals("DISCAGEM_SMS")) {

					cdr = cdrs.stream().filter(c -> c.contains(numero) && c.contains(numeroCDR.getStatusChamada()))
							.reduce((first, second) -> second).orElse(null);

					if (cdr != null) {
						String[] p = cdr.split(",");
						text += "CDR Mizu,Sim," + numeroCDR.getStatusChamada() + "," + p[2] + "," + p[3]
								+ "," + p[4] + "," + this.validarStatusSMS(numeroCDR, statusSipSMSsList);
					} else {
						text += "CDR Mizu,Não," + numeroCDR.getStatusChamada() + "," + numeroCDR.getDataLigacao() + ",-,-,"
								+ this.validarStatusSMS(numeroCDR, statusSipSMSsList);
					}

				}
			} else {
				text += "-,-,-,-,-,-,-";
			}

			ArquivoService.montarArquivoResultado(text);

		}
		System.out.println("Terminando execução");
	}

	public String validarStatusSMS(Numero numero, List<StatusSipSMS> statusSipSMSsList) {
		if (numero.getStatusChamada() != null) {
			StatusSipSMS statusSipSMS = statusSipSMSsList.stream()
					.filter(s -> numero.getStatusChamada().equals(s.getSip().toString())).findFirst().orElse(null);

			if (statusSipSMS != null) {
				if (statusSipSMS.getStatus().equals("Enviar SMS")) {
					return statusSipSMS.getStatus();
				} else {
					LocalDateTime dateStatus = subtrairData(statusSipSMS.getPeriodoValidacao(),
							statusSipSMS.getTipoPeriodoValidacao());
					LocalDateTime dateLigacao = numero.getDataLigacao();
					if (dateStatus.isAfter(dateLigacao)) {
						return statusSipSMS.getStatus();
					}
				}
			}
		}
		return "-";
	}

	public LocalDateTime subtrairData(Integer subtracao, String tipoSubtracao) {
		LocalDateTime localDateTime = LocalDateTime.now();
		if (tipoSubtracao.equals("MINUTO")) {
			localDateTime.minusMinutes(subtracao);
		} else if (tipoSubtracao.equals("HORA")) {
			localDateTime.minusHours(subtracao);
		} else if (tipoSubtracao.equals("DIA")) {
			localDateTime.minusDays(subtracao);
		} else if (tipoSubtracao.equals("SEMANA")) {
			localDateTime.minusWeeks(subtracao);
		} else if (tipoSubtracao.equals("MES")) {
			localDateTime.minusMonths(subtracao);
		} else if (tipoSubtracao.equals("ANO")) {
			localDateTime.minusYears(subtracao);
		}
		return localDateTime;
	}

}
