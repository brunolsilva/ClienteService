package com.cliente;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.cliente.config.ClienteApiProperty;
import com.cliente.domain.Cliente;
import com.cliente.repository.ClienteRepository;
import com.cliente.resource.ClienteResource;
import com.cliente.service.ClienteService;
import com.cliente.view.CampanhaView;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClienteResourceTest {

	@MockBean
	private RestTemplate restTemplate;
	
	@Autowired
	private ClienteResource clienteResource;

	@InjectMocks
	@Autowired
	private ClienteService clienteService;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ClienteApiProperty clienteApiProperty;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		clienteRepository.deleteAll();
		List<Cliente> clientes = Arrays.asList(
				new Cliente("José Silva", "jose@gmail.com", 1, LocalDate.now()),
				new Cliente("João Santos", "joao@gmail.com", 2, LocalDate.now())
		);

		clienteRepository.save(clientes);
		List<CampanhaView> campanhas = Arrays.asList(new CampanhaView("Campanha", 1, LocalDate.now(), LocalDate.now()));		
		String url = clienteApiProperty.getUrlCampanha() + "/time/" + 1;

		given(
				restTemplate.exchange(
						url,
						HttpMethod.GET,
						null,
						new ParameterizedTypeReference<List<CampanhaView>>(){}))
		.willReturn(ResponseEntity.ok(campanhas));
	}
	
	@Test
	public void cadastraClienteNovoTest() {
		Cliente cliente = new Cliente("Maria Silva", "maria@gmail.com", 1, LocalDate.now());
		ResponseEntity<List<CampanhaView>> retorno = clienteResource.cadastra(cliente);

		assertThat(retorno).isNotNull();
		assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(retorno.getBody()).isNotNull();
	}

	@Test
	public void cadastraClienteExistenteTest() {
		Cliente cliente = new Cliente("Maria Silva", "jose@gmail.com", 1, LocalDate.now());
		ResponseEntity<List<CampanhaView>> retorno = clienteResource.cadastra(cliente);

		assertThat(retorno).isNotNull();
		assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(retorno.getBody()).isNotNull();
	}

	@Test
	public void campanhaForaDoArFallBackTest() {
		String url = clienteApiProperty.getUrlCampanha() + "/time/" + 1;
		given(
				restTemplate.exchange(
						url,
						HttpMethod.GET,
						null,
						new ParameterizedTypeReference<List<CampanhaView>>(){}))
		.willThrow(RestClientException.class);
		
		List<CampanhaView> emptyList = clienteService.recuperarCampanhas(1);

		assertThat(emptyList).isNotNull();
		assertThat(emptyList).isEmpty();
	}
	
	@Test
	public void cadastraClienteComCampanhaTest() {
		Cliente cliente = new Cliente("Maria Silva", "aaa@gmail.com", 1, LocalDate.now());
		ResponseEntity<List<CampanhaView>> retorno = clienteResource.cadastra(cliente);
		assertThat(retorno).isNotNull();
		assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(retorno.getBody()).isNotNull();
		assertThat(retorno.getBody()).isNotEmpty();
		assertThat(retorno.getBody()).hasSize(1);
	}
}
