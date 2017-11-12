package com.cliente.view;

import java.time.LocalDate;

public class CampanhaView {

	private String nome;
	private Integer time;
	private LocalDate dataInicioVigencia;
	private LocalDate dataFimVigencia;
	
	public CampanhaView() {
		/**
		 * Construtor vazio
		 */
	}
	
	public CampanhaView(String nome, Integer time, LocalDate dataInicioVigencia, LocalDate dataFimVigencia) {
		this.nome = nome;
		this.time = time;
		this.dataInicioVigencia = dataInicioVigencia;
		this.dataFimVigencia = dataFimVigencia;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public LocalDate getDataInicioVigencia() {
		return dataInicioVigencia;
	}

	public void setDataInicioVigencia(LocalDate dataInicioVigencia) {
		this.dataInicioVigencia = dataInicioVigencia;
	}

	public LocalDate getDataFimVigencia() {
		return dataFimVigencia;
	}

	public void setDataFimVigencia(LocalDate dataFimVigencia) {
		this.dataFimVigencia = dataFimVigencia;
	}

	
}
