package br.com.juhmaran.exception.runtimes;

/**
 * Exceção lançada quando um recurso não é encontrado.
 *
 * <p>Mapeada para HTTP {@code 404 Not Found}.</p>
 *
 * <p>Exemplo:
 * <ul>
 *   <li>Busca por ID inexistente</li>
 * </ul>
 *
 * @author Juliane Maran
 * @since 08/04/2026
 */
public class ResourceNotFoundException extends BusinessException {

  /**
   * @param message descrição do recurso não encontrado
   */
  public ResourceNotFoundException(String message) {
    super(message);
  }

}
