package br.com.juhmaran.exception.runtimes;

/**
 * Exceção lançada quando o usuário não possui permissão para acessar determinado recurso.
 *
 * <p>Mapeada para HTTP {@code 403 Forbidden}.</p>
 *
 * @author Juliane Maran
 * @since 08/04/2026
 */
public class ForbiddenException extends BusinessException {

  /**
   * @param message descrição da restrição de acesso
   */
  public ForbiddenException(String message) {
    super(message);
  }

}
