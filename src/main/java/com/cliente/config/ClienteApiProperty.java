package com.cliente.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Classe de configuração para ser utilizada no application.properties
 *
 */
@ConfigurationProperties("cliente")
public class ClienteApiProperty {
	
	private String urlCampanha = "http://localhost:8000/campanha";

	public String getUrlCampanha() {
		return urlCampanha;
	}

	public void setUrlCampanha(String urlCampanha) {
		this.urlCampanha = urlCampanha;
	}
}
