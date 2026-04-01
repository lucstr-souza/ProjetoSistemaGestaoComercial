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
