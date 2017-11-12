package com.cliente.resource;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cliente.domain.Cliente;
import com.cliente.repository.ClienteRepository;
import com.cliente.service.ClienteService;
import com.cliente.view.CampanhaView;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/cliente")
public class ClienteResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClienteRepository.class);

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private ClienteRepository clienteRepository;

	@ApiOperation(value = "Cadastra cliente", notes = "Cadastra o cliente e retorna a lista de campanhas do seu time")
	@PostMapping
	public ResponseEntity<List<CampanhaView>> cadastra(@Valid @RequestBody Cliente cliente) {
		List<CampanhaView> campanhas = clienteService.recuperarCampanhas(cliente.getTime());

		try {
			clienteRepository.save(cliente);
		} catch (DataIntegrityViolationException e) {
			LOGGER.info("Cliente já cadastrado com o e-mail {}", cliente.getEmail());
			return ResponseEntity.status(HttpStatus.OK).body(campanhas);
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(campanhas);
	}
}
