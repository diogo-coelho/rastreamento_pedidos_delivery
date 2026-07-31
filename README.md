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

