package br.com.juhmaran.exception.runtimes;

/**
 * Exceção lançada quando uma operação excede o tempo limite permitido.
 *
 * <p>Mapeada para HTTP {@code 408 Request Timeout} ou {@code 504 Gateway Timeout}.</p>
 *
 * <p>Exemplo:
 * <ul>
 *   <li>Timeout em chamadas externas</li>
 * </ul>
 *
 * @author Juliane Maran
 * @since 08/04/2026
 */
public class TimeoutException extends BusinessException {

  /**
   * @param message descrição do timeout ocorrido
   */
  public TimeoutException(String message) {
    super(message);
  }

}
