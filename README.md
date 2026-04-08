# Custom Exception Library

Biblioteca reutilizável para padronização de tratamento de exceções em aplicações Spring Boot.

## Objetivo

Centralizar e padronizar:

- Tratamento global de exceções
- Estrutura de resposta de erro
- Códigos de erro consistentes
- Observabilidade com `traceId`
- Integração com logs estruturados (MDC + SLF4J)

## Tecnologias

- Java 25
- Spring Boot 4
- Maven 3.9+
- Lombok
- Jakarta Validation

## Estrutura

```
br.com.juhmaran.exception
├── handler
├── model
├── enums
├── runtimes
└── util
```

## Modelo de Resposta de Erro

```json
{
  "timestamp": "2026-04-08T12:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "User not found with id: 2",
  "path": "/users/2",
  "errorCode": "RESOURCE_NOT_FOUND",
  "traceId": "uuid"
}
```

## Como Usar

### 1. Adicionar dependência

Se publicada:

```xml

<dependency>
    <groupId>br.com.juhmaran</groupId>
    <artifactId>exception-lib</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 2. Lançar exceções no domínio

```java
if(user ==null){
  throw new

ResourceNotFoundException("User not found");
}
```

### 3. Pronto

O `GlobalExceptionHandler` já intercepta automaticamente.

## Tipos de Erro Suportados

| HTTP | Exception                     | Descrição              |
|------|-------------------------------|------------------------|
| 400  | Validation / IllegalArgument  | Erros de entrada       |
| 401  | UnauthorizedException         | Não autenticado        |
| 403  | ForbiddenException            | Sem permissão          |
| 404  | ResourceNotFoundException     | Recurso não encontrado |
| 405  | HttpRequestMethodNotSupported | Método inválido        |
| 408  | TimeoutException              | Timeout                |
| 409  | ConflictException             | Conflito               |
| 422  | UnprocessableEntityException  | Regra de negócio       |
| 500  | Exception                     | Erro interno           |

## Observabilidade

* `traceId` gerado automaticamente por request
* Integrado com `MDC`
* Pode ser usado em logs:

```text
[traceId=abc-123] Error processing request
```

## Extensibilidade

Para criar novos erros:

```java
public class PaymentException extends BusinessException {
  public PaymentException(String message) {
    super(message);
  }
}
```

## Boas Práticas

* Nunca retornar stacktrace na API
* Sempre usar exceções específicas
* Manter `ErrorCode` estável
* Logar erros internos (500)

## Documentação

* [Exemplo de Swagger](./swagger/openapi.yaml)

## Licença

Este projeto está licenciado sob a [**Apache License 2.0**](LICENSE)

## Autora

Desenvolvido por Juliane Maran

[GitHub JuhMaran](https://github.com/JuhMaran)

