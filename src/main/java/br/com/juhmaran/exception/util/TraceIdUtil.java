package br.com.juhmaran.exception.util;

import lombok.experimental.UtilityClass;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * Utilitário para gerenciamento de Trace ID utilizando MDC (Mapped Diagnostic Context).
 *
 * <p>Responsável por gerar, recuperar e limpar o identificador único de rastreabilidade utilizado
 * em logs e respostas da API.</p>
 *
 * <p>Permite correlação entre logs distribuídos.</p>
 *
 * @author Juliane Maran
 * @since 08/04/2026
 */
@UtilityClass
public class TraceIdUtil {

  public static final String TRACE_ID = "traceId";

  /**
   * Gera um novo Trace ID e o adiciona ao MDC.
   *
   * @return traceId gerado
   */
  public static String generateTraceId() {
    String traceId = UUID.randomUUID().toString();
    MDC.put(TRACE_ID, traceId);
    return traceId;
  }

  /**
   * Recupera o Trace ID atual do MDC.
   *
   * @return traceId atual ou null se não existir
   */
  public static String getTraceId() {
    return MDC.get(TRACE_ID);
  }

  /**
   * Remove o Trace ID do MDC.
   */
  public static void clear() {
    MDC.remove(TRACE_ID);
  }

}
