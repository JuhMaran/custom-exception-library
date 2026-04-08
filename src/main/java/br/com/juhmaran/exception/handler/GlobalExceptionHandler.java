package br.com.juhmaran.exception.handler;

import br.com.juhmaran.exception.enums.ErrorCode;
import br.com.juhmaran.exception.model.ErrorResponse;
import br.com.juhmaran.exception.runtimes.*;
import br.com.juhmaran.exception.util.TraceIdUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * Handler global de exceções da aplicação.
 *
 * <p>Centraliza o tratamento de exceções lançadas pelos controllers, garantindo respostas padronizadas conforme o
 * contrato definido pelo {@link ErrorResponse}.</p>
 *
 * <p>Utiliza {@code @RestControllerAdvice} para interceptar exceções de forma transparente.</p>
 *
 * <p>Integração com:
 * <ul>
 *   <li>Bean Validation</li>
 *   <li>SLF4J (logging estruturado)</li>
 *   <li>MDC (traceId)</li>
 * </ul>
 *
 * <p>Suporta os seguintes cenários:
 * <ul>
 *   <li>Validação (400)</li>
 *   <li>Argumentos inválidos (400)</li>
 *   <li>Não autenticado (401)</li>
 *   <li>Sem permissão (403)</li>
 *   <li>Recurso não encontrado (404)</li>
 *   <li>Método não suportado (405)</li>
 *   <li>Timeout (408)</li>
 *   <li>Conflito (409)</li>
 *   <li>Entidade não processável (422)</li>
 *   <li>Erro interno (500)</li>
 * </ul>
 *
 * @author Juliane Maran
 * @since 08/04/2026
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Constrói o objeto {@link ErrorResponse} padronizado.
   *
   * @param status  código HTTP da resposta
   * @param message mensagem de erro
   * @param path    URI da requisição
   * @param code    código interno do erro
   * @return instância de {@link ErrorResponse}
   */
  private ErrorResponse buildResponse(HttpStatus status, String message, String path, ErrorCode code) {
    String traceId = TraceIdUtil.getTraceId();

    return ErrorResponse.builder()
      .timestamp(LocalDateTime.now())
      .status(status.value())
      .error(status.getReasonPhrase())
      .message(message)
      .path(path)
      .errorCode(code.name())
      .traceId(traceId)
      .build();
  }

  /**
   * Trata erros de validação de Bean Validation em DTOs.
   *
   * <p>Captura {@link MethodArgumentNotValidException} e retorna HTTP {@code 400 Bad Request}
   * com detalhes dos campos inválidos.</p>
   *
   * @param ex      exceção de validação
   * @param request requisição HTTP
   * @return resposta padronizada de erro
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
    String message = ex.getBindingResult()
      .getFieldErrors()
      .stream()
      .map(err -> err.getField() + ": " + err.getDefaultMessage())
      .collect(Collectors.joining(", "));

    log.warn("Validation error: {}", message);

    return buildResponse(HttpStatus.BAD_REQUEST, message,
      request.getRequestURI(), ErrorCode.VALIDATION_ERROR);
  }

  /**
   * Trata violações de constraints em parâmetros de método.
   *
   * <p>Captura {@link ConstraintViolationException} e retorna
   * HTTP {@code 400 Bad Request}.</p>
   *
   * @param ex      exceção de constraint
   * @param request requisição HTTP
   * @return resposta padronizada de erro
   */
  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleConstraint(ConstraintViolationException ex, HttpServletRequest request) {
    log.warn("Constraint violation: {}", ex.getMessage());

    return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(),
      request.getRequestURI(), ErrorCode.VALIDATION_ERROR);
  }

  // 400 Illegal Argument
  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
    return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(),
      request.getRequestURI(), ErrorCode.INVALID_ARGUMENT);
  }

  // 401
  @ExceptionHandler(UnauthorizedException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ErrorResponse handleUnauthorized(UnauthorizedException ex, HttpServletRequest request) {
    return buildResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(),
      request.getRequestURI(), ErrorCode.UNAUTHORIZED);
  }

  // 403
  @ExceptionHandler(ForbiddenException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ErrorResponse handleForbidden(ForbiddenException ex, HttpServletRequest request) {
    return buildResponse(HttpStatus.FORBIDDEN, ex.getMessage(),
      request.getRequestURI(), ErrorCode.FORBIDDEN);
  }

  // 404
  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponse handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
    return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(),
      request.getRequestURI(), ErrorCode.RESOURCE_NOT_FOUND);
  }

  // 405
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  public ErrorResponse handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex,
                                              HttpServletRequest request) {

    return buildResponse(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage(),
      request.getRequestURI(), ErrorCode.METHOD_NOT_ALLOWED);
  }

  // 408 / 504
  @ExceptionHandler(TimeoutException.class)
  @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
  public ErrorResponse handleTimeout(TimeoutException ex, HttpServletRequest request) {
    return buildResponse(HttpStatus.REQUEST_TIMEOUT, ex.getMessage(),
      request.getRequestURI(), ErrorCode.TIMEOUT);
  }

  // 409
  @ExceptionHandler(ConflictException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ErrorResponse handleConflict(ConflictException ex, HttpServletRequest request) {
    return buildResponse(HttpStatus.CONFLICT, ex.getMessage(),
      request.getRequestURI(), ErrorCode.CONFLICT);
  }

  // 422
  @ExceptionHandler(UnprocessableEntityException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
  public ErrorResponse handleUnprocessable(UnprocessableEntityException ex, HttpServletRequest request) {
    return buildResponse(HttpStatus.UNPROCESSABLE_CONTENT, ex.getMessage(),
      request.getRequestURI(), ErrorCode.UNPROCESSABLE_ENTITY);
  }

  /**
   * Trata exceções genéricas não mapeadas.
   *
   * <p>Retorna HTTP {@code 500 Internal Server Error}.</p>
   *
   * <p>O erro completo é registrado no log para análise.</p>
   *
   * @param ex      exceção capturada
   * @param request requisição HTTP
   * @return resposta padronizada de erro
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorResponse handleGeneric(Exception ex, HttpServletRequest request) {
    log.error("Internal error", ex);

    return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR,
      "Unexpected error occurred",
      request.getRequestURI(),
      ErrorCode.INTERNAL_ERROR);
  }

}