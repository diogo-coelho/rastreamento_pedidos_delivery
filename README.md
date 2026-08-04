# Mini Rastreador de Pedidos

Desafio técnico para a construção de uma versão simplificada de rastreamento de pedidos de delivery.

## Contexto:

### Backend:

Implementar as seguintes features:

- Autenticação (fluxo simples contendo):
    - Cadastro de usuário (nome, e-mail e senha);
    - Login utilizando e-mail e senha;
    - Apenas usuários autenticados podem acessar o sistema;
    - A forma de autenticação fica a critério do candidato.

- API REST em Java + Spring Boot com endpoints para:
    - Criar um pedido (cliente, items, endereço de entrega);
    - Atualizar o status do pedido, considerando: RECEBIDO, EM_PREPARO, SAIU_PARA_ENTREGA, ENTREGUE e CANCELADO;
    - Listar todos os pedidos e buscar um pedido por ID.

- Persistência em SQLite ou similar.

### Frontend:

- Aplicação em React.js que lista os pedidos com seus status atuais e permite criar um novo pedido.

### Versionamento:

- Repositório Git com histórico de commits.

## Documentação complementar

Aplicação full stack para rastreamento de pedidos de delivery, com autenticação JWT, gerenciamento de pedidos e interface web.

## Visão geral

O projeto é dividido em dois serviços principais:

- Backend: API REST em Spring Boot.
- Frontend: aplicação Next.js com React.

Também existe um ambiente Docker Compose para subir tudo junto com um único comando.

## Tecnologias aplicadas

### Backend

- Java 21
- Spring Boot 4.0.7
- Spring Web
- Spring Security
- Spring Data JPA
- Flyway
- SQLite
- JWT (java-jwt)
- SpringDoc OpenAPI
- Maven Wrapper
- Lombok

### Frontend

- Next.js 16
- React 19
- TypeScript
- Sass
- Ant Design
- Axios
- ESLint

### Infra

- Docker Compose
- Imagem Maven Eclipse Temurin 21
- Imagem Node 22 Alpine

## Funcionalidades implementadas

### Backend

- Cadastro de usuário.
- Login e geração de token JWT.
- Logout com revogação de token.
- Criação e listagem de itens.
- Criação e listagem de endereços.
- Criação de pedido.
- Listagem de pedidos.
- Consulta de pedido por ID.
- Atualização de status do pedido.
- Migrações de banco com Flyway.
- Documentação OpenAPI/Swagger.

### Frontend

- Tela de login.
- Tela de cadastro.
- Contexto de autenticação com persistência de token em localStorage.
- Listagem de pedidos.
- Fluxo de criação de pedido com seleção de itens e endereço.
- Integração com backend via Axios e interceptor para Authorization Bearer.

## Status do pedido

Estados suportados no backend:

- RECEBIDO
- EM_PREPARO
- SAIU_PARA_ENTREGA
- ENTREGUE
- CANCELADO

## Estrutura de pastas

```text
.
|-- docker/
|   `-- docker-compose.yml
|-- order_tracking_api/
|   |-- pom.xml
|   `-- src/
`-- order_tracking_ui/
    |-- package.json
    `-- app/
```

## Pré-requisitos

Para rodar localmente sem Docker:

- Java 21
- Node.js 22+
- npm 10+

Para rodar com Docker:

- Docker Desktop com engine ativo.

## Configuração de ambiente

### Frontend

Arquivo .env em order_tracking_ui:

```env
NEXT_PUBLIC_API_URL=http://localhost:8080
```

### Backend

As propriedades padrão estão em application.properties, usando SQLite local por arquivo.

## Como subir os projetos individualmente

### 1) Backend

No Windows (PowerShell), a partir da raiz do repositório:

```powershell
cd order_tracking_api
.\mvnw.cmd spring-boot:run
```

No Linux/macOS:

```bash
cd order_tracking_api
./mvnw spring-boot:run
```

API disponível em:

- http://localhost:8080

Swagger UI:

- http://localhost:8080/swagger-ui/index.html

### 2) Frontend

Na raiz do repositório:

```powershell
cd order_tracking_ui
npm ci
npm run dev -- --webpack
```

Frontend disponível em:

- http://localhost:3000

Para acessar a tela de login, insira o email ```teste1@example.com``` e a senha ```senha123``` para acessar
a credencial do usuário previamente cadastrado no banco de dados.


## Como subir os projetos juntos com Docker Compose

Na raiz do repositório:

```powershell
docker compose -f docker/docker-compose.yml up --build
```

Para parar e remover containers/volumes da stack:

```powershell
docker compose -f docker/docker-compose.yml down -v
```

## Endpoints principais da API

### Autenticação e usuário

- POST /user/register
- POST /auth/login
- POST /auth/logout

### Itens

- GET /item
- POST /item/create

### Endereços

- GET /address
- POST /address/create/{userId}

### Pedidos

- GET /order
- GET /order/{orderId}
- POST /order/create
- PATCH /order/{orderId}/status

## Banco de dados e migrações

- Banco: SQLite.
- Migrações versionadas em order_tracking_api/src/main/resources/db/migration.
- Em execução local sem Docker, o arquivo do banco é criado no diretório do backend.
- Em execução com Docker Compose, o banco fica persistido em volume Docker.

## Troubleshooting rápido

### Docker daemon indisponível no Windows

Se aparecer erro de conexão com pipe dockerDesktopLinuxEngine, o Docker Engine não está ativo.

Checklist:

- Abrir Docker Desktop.
- Validar com docker info.
- Rodar novamente docker compose up.

### Erro Turbopack no frontend dentro do Docker

O compose já está configurado para subir o Next.js com Webpack no modo dev, reduzindo falhas conhecidas de Turbopack em volume mount no Windows.

Se necessário, limpe volumes e suba novamente:

```powershell
docker compose -f docker/docker-compose.yml down -v
docker compose -f docker/docker-compose.yml up --build
```

## Melhorias futuras sugeridas

- Adicionar testes de integração para fluxos completos de pedidos.
- Criar Dockerfile dedicado para backend e frontend (produção).
- Implementar refresh token.
- Configurar CI para lint, testes e build.



