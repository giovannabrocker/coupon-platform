# Coupon Platform

## Visão Geral
Plataforma backend baseada em microsserviços para gerenciamento de cupons e autenticação de usuários, com um API Gateway centralizando o acesso.

O projeto está em evolução, com integração completa entre autenticação (JWT), gateway e frontend ainda em andamento.

---

## Arquitetura

A aplicação é composta por:

- **identity-service**  
  Responsável por autenticação, autorização e gerenciamento de usuários.

- **coupon-service**  
  Responsável pela criação, listagem e exclusão lógica de cupons.

- **gateway (Spring Cloud Gateway)**  
  Ponto único de entrada que roteia requisições para os serviços internos.

- **frontend (React)**  
  Interface do usuário (em desenvolvimento).

- **MySQL (Docker)**  
  Banco de dados utilizado pelos serviços.

---

## Tecnologias

### Backend
- Java 21
- Spring Boot
- Spring Security (JWT)
- Spring Cloud Gateway
- Spring Data JPA

### Frontend
- React + Vite
- MUI
- Yup

### Infraestrutura
- Docker
- Docker Compose
- MySQL

---

## Fluxo da Aplicação

1. O cliente realiza requisições via Gateway (`:8080`)
2. O Gateway roteia:
   - `/auth/**` → identity-service
   - `/api/users/**` → identity-service
   - `/api/coupons/**` → coupon-service
3. Cada serviço processa sua responsabilidade de forma independente

> ⚠️ Atualmente o gateway atua apenas como roteador. A validação de JWT nele ainda será implementada.

---

## Endpoints Principais

### Identity Service
- `POST /auth/login`
- `POST /auth/register`
- `GET /api/users`
- `DELETE /api/users/{id}`

### Coupon Service
- `GET /api/coupons`
- `POST /api/coupons`
- `DELETE /api/coupons/{id}`

---

## Regras de Negócio (Coupon)

- Código:
  - Sanitizado (remove caracteres especiais e aplica uppercase)
  - 6 caracteres alfanuméricos
- Desconto mínimo: `0.5`
- Data de expiração obrigatória e futura
- Exclusão lógica (soft delete)

---

## Segurança

Implementado:
- Autenticação com JWT no identity-service
- Controle de acesso por perfil (USER / ADMIN)

Em andamento:
- Validação de JWT no gateway
- Proteção de rotas entre serviços

---

## Como Executar

### Pré-requisitos
- Docker
- Docker Compose

### 1. Criar `.env`

```env
DB_ROOT_PASSWORD=senha
DB_DATABASE_NAME=projeto
DB_USER=user
DB_PASSWORD=senha
DB_PORT_LOCAL=3307
DB_PORT_DOCKER=3306