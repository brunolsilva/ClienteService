# Cliente Service

Aplicação REST utilizando Java 8, Spring Boot como framework base para a construção da aplicação, Spring Data JPA para acesso aos dados armazenados no banco de dados, banco de dados H2 em memória, Spring Cloud Hystrix como implementação do pattern Circuit Breaker (quando o serviço de campanhas está DOWN, um fallback é chamado), Swagger para documentação da API e Maven como ferramenta de build e gerenciamento de dependências. Implementação do cadastro de sócio torcedor solicitado.

## Para rodar a aplicação
Após clonar este repositório, ir até até o diretório criado e rodar o seguinte comando <code>mvn spring-boot:run</code>

## Endereço da aplicação
A aplicação foi configurada pra executar no seguinte endereço: http://localhost:9090

## Documentação
Como solução para documentar API foi utilizado o Swagger e disponibilizada no seguinte endereço: http://localhost:9090/swagger-ui.html

## Exemplos de chamadas

Cadastrar cliente:

**POST /cliente**
```
{
	"email": "joao@gmail.com",
	"nome": "João",
	"time": 1,
	"dataNascimento": "1980-10-10"
}
```

Resposta:
```
[
    {
        "id": 1,
        "nome": "Black friday",
        "time": 1,
        "dataInicioVigencia": "2017-01-01",
        "dataFimVigencia": "2018-01-04"
    }
]
```

Se o serviço de Campanha estiver indisponível, o cadastro é efetuado normalmente, retornando uma lista de campanhas vazia para o cliente.