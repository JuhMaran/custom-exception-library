package br.com.juhmaran.exception.enums;

/**
 * Enumeração de códigos de erro padronizados da aplicação.
 *
 * <p>Os códigos definidos aqui são utilizados para identificar de forma consistente os tipos de erro retornados
 * pela API, independentemente do status HTTP.</p>
 *
 * <p>Esses códigos são especialmente úteis para:</p>
 * <ul>
 *   <li>Clientes que precisam tratar erros programaticamente</li>
 *   <li>Logs estruturados</li>
 *   <li>Monitoramento e observabilidade</li>
 * </ul>
 *
 * <p>Recomendado manter estabilidade nesses valores para evitar quebra de contratos com consumidores externos.</p>
 *
 * @author Juliane Maran
 * @since 08/04/2026
 */
public enum ErrorCode {
  RESOURCE_NOT_FOUND,
  VALIDATION_ERROR,
  INVALID_ARGUMENT,
  METHOD_NOT_ALLOWED,
  TIMEOUT,
  CONFLICT,
  UNPROCESSABLE_ENTITY,
  INTERNAL_ERROR,
  UNAUTHORIZED,
  FORBIDDEN
}
