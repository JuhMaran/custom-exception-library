package br.com.juhmaran.exception.util;

import jakarta.servlet.*;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Filtro responsável por inicializar e limpar o Trace ID para cada requisição HTTP.
 *
 * <p>Garante que todas as requisições possuam um identificador único para rastreabilidade.</p>
 *
 * <p>O Trace ID é armazenado no MDC e pode ser utilizado em logs e respostas de erro.</p>
 *
 * <p>Registrado automaticamente como componente Spring.</p>
 *
 * @author Juliane Maran
 * @since 08/04/2026
 */
@Component
public class TraceFilter implements Filter {

  /**
   * Intercepta a requisição HTTP para configurar o Trace ID.
   *
   * <p>Fluxo:
   * <ol>
   *   <li>Gera Trace ID</li>
   *   <li>Continua a cadeia de filtros</li>
   *   <li>Remove o Trace ID ao final</li>
   * </ol>
   *
   * @param request  requisição
   * @param response resposta
   * @param chain    cadeia de filtros
   * @throws IOException      erro de IO
   * @throws ServletException erro de servlet
   */
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    try {
      TraceIdUtil.generateTraceId();
      chain.doFilter(request, response);
    } finally {
      MDC.clear();
    }
  }

}
