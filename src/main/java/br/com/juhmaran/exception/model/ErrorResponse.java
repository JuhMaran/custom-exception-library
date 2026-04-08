package br.com.juhmaran.exception.model;

import br.com.juhmaran.exception.handler.GlobalExceptionHandler;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * Representa o payload padrão de resposta de erro da aplicação.
 *
 * <p> Este DTO é utilizado pelo {@link GlobalExceptionHandler} para garantir consistência nas respostas HTTP de erro retornadas pelas APIs. </p>
 *
 * <p> Inclui informações relevantes para debugging, rastreabilidade (traceId) e entendimento do erro pelo consumidor da API. </p>
 *
 * <p> Este record é imutável e utiliza {@code Lombok @Builder} para facilitar sua construção. </p>
 *
 * <h3>Exemplo de resposta:</h3>
 * <pre>{@code
 * {
 *   "timestamp": "2026-04-08T12:00:00",
 *   "status": 404,
 *   "error": "Not Found",
 *   "message": "User not found with id: 2",
 *   "path": "/users/2",
 *   "errorCode": "RESOURCE_NOT_FOUND",
 *   "traceId": "uuid"
 * }
 * }</pre>
 *
 * @param timestamp data/hora em que o erro ocorreu
 * @param status    código HTTP da resposta
 * @param error     descrição padrão do status HTTP
 * @param message   mensagem detalhada do erro
 * @param path      URI da requisição que originou o erro
 * @param errorCode código interno padronizado do erro
 * @param traceId   identificador único para rastreabilidade (observabilidade)
 * @author Juliane Maran
 * @since 08/04/2026
 */
@Builder
public record ErrorResponse(
  LocalDateTime timestamp,
  int status,
  String error,
  String message,
  String path,
  String errorCode,
  String traceId
) {
}
