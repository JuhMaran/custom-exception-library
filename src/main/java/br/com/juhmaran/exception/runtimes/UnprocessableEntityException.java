package br.com.juhmaran.exception.runtimes;

/**
 * Exceção lançada quando a requisição é semanticamente inválida,
 * apesar de estar sintaticamente correta.
 *
 * <p>Mapeada para HTTP {@code 422 Unprocessable Content}.</p>
 *
 * <p>Exemplo:
 * <ul>
 *   <li>Regras de negócio não atendidas</li>
 * </ul>
 *
 * @author Juliane Maran
 * @since 08/04/2026
 */
public class UnprocessableEntityException extends BusinessException {

  /**
   * @param message descrição do erro de processamento
   */
  public UnprocessableEntityException(String message) {
    super(message);
  }

}
