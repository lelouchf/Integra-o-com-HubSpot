# Projeto Integração HubSpot CRM

Este projeto em **Java 17** com **Spring Boot** realiza a integração completa com o CRM **HubSpot**, implementando:

- Fluxo OAuth 2.0 para autenticação
- Criação de contatos no HubSpot
- Recebimento de eventos via Webhook (`contact.creation`)

## 📦 Requisitos

- Java 17+
- Maven 3+
- Conta HubSpot Developer
- Conta no [ngrok](https://ngrok.com/) (para testes de webhooks)
- IDE (ex: IntelliJ, Eclipse ou VSCode)

---

## 🚀 Como Executar

### 1. Clone o projeto

```bash
git clone https://github.com/seu-usuario/seu-repo.git
cd seu-repo
```

### 2. Configure suas variáveis de ambiente

No arquivo `application.properties`, adicione:

```properties
hubspot.client.id=SEU_CLIENT_ID
hubspot.client.secret=SEU_CLIENT_SECRET
hubspot.redirect.uri=http://localhost:8080/oauth/callback
hubspot.scopes=contacts
```

Essas informações você encontra no painel do seu App no HubSpot.

---

### 3. Rodar o projeto

Na raiz do projeto:

```bash
mvn spring-boot:run
```

A aplicação iniciará em `http://localhost:8080`.

---

### 4. Expor o localhost para o HubSpot (Webhook)

Instale e inicie o ngrok:

```bash
ngrok http 8080
```

Copie a URL gerada (ex: `https://abcd1234.ngrok-free.app`) e configure no HubSpot na seção de Webhooks.

---

### 5. Fluxo OAuth (Primeira conexão)

1. Acesse `http://localhost:8080/oauth/authorize`
2. Faça o login/autorizacao com sua conta do HubSpot
3. O token de acesso será salvo em memória.

---

### 6. Criar um contato no HubSpot

Faça uma chamada `POST` para:

```bash
POST http://localhost:8080/hubspot/contacts
```

Body (JSON):

```json
{
  "firstName": "Cauã",
  "lastName": "Fonseca",
  "email": "caua@example.com"
}
```

---

### 7. Webhook - Receber `contact.creation`

Ao criar um novo contato manualmente no HubSpot, o webhook enviará um POST para:

```
https://<ngrok-url>/webhook/contact-creation
```

A aplicação logará o payload recebido no console.

---

## 🧐 Decisões Técnicas

- **Spring Boot**: Facilita a criação de aplicações standalone com dependências integradas.
- **RestTemplate**: Usado para fazer chamadas HTTP simples à API HubSpot.
- **ngrok**: Ferramenta prática para expor aplicações locais para a internet, essencial para testes de webhook sem deploy.
- **TokenStore em Memória**: Para simplicidade no teste, o access token foi salvo em memória. Em produção, o ideal seria usar Redis, Banco de Dados ou um Cache distribuído.
- **OAuth 2.0 Authorization Code**: Seguindo a recomendação da HubSpot para garantir segurança.

---

## 🛠️ Possíveis Melhorias Futuras

- Implementar **refresh automático** do access token usando o `refresh_token`.
- Salvar tokens de forma segura usando um banco de dados.
- Implementar **tratamento de erros** mais robusto (ex: expirou o token, erros 429 - rate limit).
- Criar testes unitários para os serviços de OAuth e contato.
- Usar o **WebClient** (Reactor) no lugar do RestTemplate para chamadas assíncronas.
- Implementar cache para contatos criados.
- Melhor controle de versionamento de API.

---

## 📚 Documentação da API

| Método | Endpoint                    | Descrição                                   |
| ------ | --------------------------- | ------------------------------------------- |
| GET    | `/oauth/authorize`          | Redireciona para login no HubSpot           |
| GET    | `/oauth/callback`           | Recebe o `code` do OAuth e troca por tokens |
| POST   | `/hubspot/contacts`         | Cria um novo contato no HubSpot             |
| POST   | `/webhook/contact-creation` | Recebe notificações de criação de contato   |

---

## ✨ Autor

Cauã da Fonseca[LinkedIn]([https://linkedin.com/in/seu-linkedin](https://www.linkedin.com/in/caua-da-fonseca-dev/)) — [GitHub](https://github.com/lelouchzin)

---

> **Obs:** Este projeto foi desenvolvido como estudo prático de integração OAuth 2.0 com HubSpot, incluindo conceitos de segurança, APIs RESTful e webhooks.
