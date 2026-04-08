package br.com.juhmaran.exception.runtimes;

/**
 * Exceção lançada quando ocorre conflito entre recursos.
 *
 * <p>Mapeada para HTTP {@code 409 Conflict}.</p>
 *
 * <p>Exemplo de uso:</p>
 * <ul>
 *   <li>Cadastro duplicado</li>
 *   <li>Violação de unicidade</li>
 * </ul>
 *
 * @author Juliane Maran
 * @since 08/04/2026
 */
public class ConflictException extends BusinessException {

  /**
   * @param message descrição do conflito
   */
  public ConflictException(String message) {
    super(message);
  }

}
