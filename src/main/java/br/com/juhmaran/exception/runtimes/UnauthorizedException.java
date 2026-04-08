package br.com.juhmaran.exception.runtimes;

/**
 * Exceção lançada quando o usuário não está autenticado.
 *
 * <p>Mapeada para HTTP {@code 401 Unauthorized}.</p>
 *
 * @author Juliane Maran
 * @since 08/04/2026
 */
public class UnauthorizedException extends BusinessException {

  /**
   * @param message descrição da falha de autenticação
   */
  public UnauthorizedException(String message) {
    super(message);
  }

}
