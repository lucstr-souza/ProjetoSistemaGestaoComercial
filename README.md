# SGC - Sistema de Gestão Comercial
> Sistema de gestão para loja de produtos artesanais

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow)

## Descrição
Sistema de Gestão Comercial desenvolvido para uma loja de produtos artesanais,
permitindo o controle de clientes, produtos, estoque e vendas, com autenticação
de usuários por perfil (ADMIN e FUNCIONARIO) e geração de relatórios gerenciais.

## Grupo
| Alunas | 
|------|
| Isabella Sena 
| Luísa Souza |
| Maria Eduarda Almeida |

**Professor:** Felippe Pires Ferreira  
**Disciplina:** Desenvolvimento de Sistemas

## Tecnologias
- Java 21
- Spring Boot 3
- Spring Data JPA
- Spring Security + JWT
- MySQL 8
- Maven
- Lombok
- Swagger/OpenAPI (documentação da API)

## Arquitetura
O projeto segue arquitetura em camadas:

- **Apresentação** — Interface Swing ou Web
- **Controller** — Endpoints REST (@RestController)
- **Service** — Regras de negócio (@Service)
- **Domain** — Entidades JPA e enums
- **Repository** — Acesso ao banco via Spring Data JPA
- **Banco de Dados** — MySQL

**Design Patterns utilizados:** Repository, DTO, Singleton (Spring IoC)

## Estrutura do Projeto
```
backend/
├── src/main/java/br/com/sgc/
│   ├── config/          # JWT, Security
│   ├── controller/      # Endpoints REST
│   ├── service/         # Regras de negócio
│   ├── domain/
│   │   ├── model/       # Entidades JPA
│   │   ├── repository/  # Interfaces de acesso ao banco
│   │   └── enums/       # PerfilUsuario
│   ├── dto/             # Data Transfer Objects
│   └── exception/       # Exceções personalizadas
├── src/main/resources/
│   └── application.yml
└── pom.xml
```
## Como executar

### Pré-requisitos
- Java 21+
- MySQL 8 rodando localmente
- Maven

### 1. Criar o banco de dados
```sql
CREATE DATABASE IF NOT EXISTS sgc_db;
```

### 2. Configurar credenciais
Edite o arquivo `src/main/resources/application.yaml`:
```yaml
spring:
  datasource:
    username: root
    password: SUA_SENHA
```

### 3. Rodar a aplicação
```bash
./mvnw spring-boot:run
```
A aplicação sobe na porta `8080`. As tabelas são criadas automaticamente pelo Hibernate.

### 4. Inserir usuário admin inicial
```sql
USE sgc_db;
INSERT INTO us_usuarios (us_username, us_senha, us_perfil)
VALUES ('admin', '$2a$12$2YCBiKf4R8MB0UHqKrz8AuDgj4F8qSjFMXv9yFHGp6aA4WlAFD9lO', 'ADMIN');
```

### 5. Acessar a documentação da API
Após subir a aplicação, acesse o Swagger no navegador:
```
http://localhost:8080/swagger-ui.html
```

## Autenticação

Todas as rotas exceto `/auth/login` exigem token JWT no header:

## Endpoints

| Método | Endpoint | Acesso | Descrição |
|--------|----------|--------|-----------|
| POST | `/auth/login` | Público | Gera token JWT |
| GET | `/clientes` | Autenticado | Lista clientes |
| GET | `/clientes/{id}` | Autenticado | Busca cliente |
| POST | `/clientes` | Autenticado | Cadastra cliente |
| PUT | `/clientes/{id}` | Autenticado | Atualiza cliente |
| DELETE | `/clientes/{id}` | Autenticado | Remove cliente |
| GET | `/produtos` | Autenticado | Lista produtos |
| GET | `/produtos/{id}` | Autenticado | Busca produto |
| POST | `/produtos` | Autenticado | Cadastra produto |
| PUT | `/produtos/{id}` | Autenticado | Atualiza produto |
| DELETE | `/produtos/{id}` | Autenticado | Remove produto |
| POST | `/vendas` | Autenticado | Registra venda |
| GET | `/vendas/{id}` | Autenticado | Busca venda |
| GET | `/vendas/cliente/{id}` | Autenticado | Vendas do cliente |
| GET | `/usuarios` | Autenticado | Lista usuários |
| POST | `/usuarios` | Autenticado | Cadastra usuário |
| PUT | `/usuarios/{id}` | Autenticado | Atualiza usuário |
| DELETE | `/usuarios/{id}` | Autenticado | Remove usuário |
| GET | `/relatorios/vendas` | Autenticado | Vendas por período |
| GET | `/relatorios/cliente/{id}` | Autenticado | Histórico do cliente |

## Repositório

[github.com/lucstr-souza/ProjetoSistemaGestaoComercial](https://github.com/lucstr-souza/ProjetoSistemaGestaoComercial)
