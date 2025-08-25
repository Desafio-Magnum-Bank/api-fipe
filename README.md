# Tabela FIPE API

Este projeto é uma aplicação baseada em Quarkus para gerenciamento e processamento de dados da tabela FIPE. Ele é composto por dois componentes principais:
- **Aplicação API**: Gerencia endpoints REST, autenticação e interações com o banco de dados, cache e fila de mensagens. Executa na porta 8081.
- **Aplicação de Processamento**: Processa mensagens da fila RabbitMQ. Executa na porta 8080.

O projeto utiliza o Quarkus, um framework Java supersônico e subatômico. Para saber mais sobre o Quarkus, visite [https://quarkus.io/](https://quarkus.io/).

## Pré-requisitos

- Java 17 ou superior
- Maven 3.8+
- Docker e Docker Compose (para os serviços de infraestrutura: PostgreSQL, Redis, RabbitMQ)
- Git (para clonar o repositório)

## Configurando a Infraestrutura

Antes de executar as aplicações, inicie os serviços necessários (PostgreSQL, Redis e RabbitMQ) usando o Docker Compose. O arquivo `docker-compose.yaml` está na raiz do projeto.

1. Navegue até o diretório raiz do projeto.
2. Execute o comando a seguir para iniciar os serviços:

   ```
   docker-compose up -d
   ```

   Isso iniciará:
  - PostgreSQL na porta 5432 (banco de dados: `fipe_db`, usuário: `user`, senha: `password`).
  - Redis na porta 6379.
  - RabbitMQ nas portas 5672 (AMQP) e 15672 (interface de gerenciamento, usuário: `fipe_user`, senha: `fipe_pass`).

   O contêiner do PostgreSQL importará automaticamente o schema e os dados de `./src/main/resources/import.sql`.

3. Verifique se os serviços estão em execução:

   ```
   docker ps
   ```

   Você verá contêineres chamados `postgres_fipe`, `redis_fipe` e `rabbit_fipe`.

## Executando as Aplicações

### Executando em Modo de Desenvolvimento

1. Certifique-se de que a infraestrutura está em execução (veja acima).
2. Para a **Aplicação API** (porta 8081):
  - Navegue até o diretório do módulo API (se separado; caso contrário, raiz).
  - Execute:
    ```
    ./mvnw quarkus:dev -Dquarkus.http.port=8081
    ```
3. Para a **Aplicação de Processamento** (porta 8080):
  - Navegue até o diretório do módulo de processamento (se separado; caso contrário, raiz).
  - Execute:
    ```
    ./mvnw quarkus:dev -Dquarkus.http.port=8080
    ```

## Autenticação e Login

A API requer autenticação. Um usuário admin padrão está pré-configurado no banco de dados pelo script de importação.

- **Usuário**: `admin`
- **Senha**: `123`

Para fazer login:
1. Use uma ferramenta como Postman ou a coleção fornecida (veja abaixo).
2. Envie uma requisição POST para o endpoint de login (ex.: `/auth/login`) com as credenciais no corpo da requisição (formato JSON: `{"username": "admin", "password": "123"}`).
3. A resposta incluirá um token JWT para requisições autenticadas subsequentes.

## Acessando a Documentação da API (Swagger)

A API utiliza SmallRye OpenAPI com Swagger UI para documentação.

- Quando a aplicação API estiver em execução, acesse o Swagger em: [http://localhost:8081/swagger-ui/#/](http://localhost:8081/swagger-ui/#/)
- Explore endpoints, esquemas e teste requisições diretamente na interface (autenticação pode ser necessária para endpoints protegidos).

## Usando a Coleção do Postman

Um arquivo de coleção do Postman está disponível na raiz do projeto (ex.: `fipe-api.postman_collection.har`).

1. Importe a coleção no Postman.
2. Configure variáveis de ambiente, se necessário (ex.: URL base: `http://localhost:8081`).
3. Use as requisições para testar endpoints, incluindo login e gatilhos de processamento de dados.

## Guias Relacionados

- REST ([guia](https://quarkus.io/guides/rest)): Implementação REST Jakarta com Vert.x.
- Mensageria - Conector RabbitMQ ([guia](https://quarkus.io/guides/rabbitmq)): Conexão com RabbitMQ usando Mensageria Reativa.
- SmallRye OpenAPI ([guia](https://quarkus.io/guides/openapi-swaggerui)): Documentação de APIs com OpenAPI e Swagger UI.
- SmallRye JWT ([guia](https://quarkus.io/guides/security-jwt)): Proteja aplicações com JSON Web Tokens.
- Cache Redis ([guia](https://quarkus.io/guides/cache-redis-reference)): Use Redis como backend de cache.
- Driver JDBC - PostgreSQL ([guia](https://quarkus.io/guides/datasource)): Conexão com PostgreSQL via JDBC.
