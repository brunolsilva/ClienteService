package com.cliente.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cliente implements Serializable {

	@Id
	@GeneratedValue
	private long id;

	@NotBlank(message = "Informar o nome")
	private String nome;

	@NotBlank(message = "Informar o e-mail")
	@Email(message = "E-mail inválido")
	@Column(name = "email", unique = true)
	private String email;
	
	@NotNull(message = "Informar o time")
	private Integer time;
	
	@NotNull(message = "Informar o data de nascimento")
	private LocalDate dataNascimento;
	
	public Cliente() {
		/**
		 * Construtor vazio
		 */
	}
	
	public Cliente(String nome, String email, Integer time, LocalDate dataNascimento) {
		this.nome = nome;
		this.email = email;
		this.time = time;
		this.dataNascimento = dataNascimento;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}
	
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nome=" + nome + ", email=" + email + ", time=" + time + ", dataNascimento="
				+ dataNascimento + "]";
	}
}
