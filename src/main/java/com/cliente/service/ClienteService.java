package com.cliente.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cliente.config.ClienteApiProperty;
import com.cliente.view.CampanhaView;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteApiProperty clienteApiProperty;

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * <p>Consulta o serviço de campanhas e retorna as campanhas ativas do time do cliente</p>
	 * 
	 * <p>Se o serviço de campanhas estiver indisponível, um método de fallback campanhasDefault será acionado
	 * 
	 * @param time O time do cliente
	 * @return List<CampanhaView> As campanhas vigentes desse time
	 */
	@HystrixCommand(fallbackMethod = "campanhasDefault")
	public List<CampanhaView> recuperarCampanhas(Integer time) {
		String url = clienteApiProperty.getUrlCampanha() + "/time/" + time;
		
		ResponseEntity<List<CampanhaView>> campanhasEncontradas = 
				restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<CampanhaView>>(){});
		return campanhasEncontradas.getBody();
	}
	/**
	 * Método de fallback para quando ocorrer indisponibilidade no serviço de campanhas
	 * 
	 * @param time
	 * @return List<CampanhaView> uma lista de campanhas vazia
	 */
	public List<CampanhaView> campanhasDefault(Integer time) {
		return Collections.emptyList();
	}

}
