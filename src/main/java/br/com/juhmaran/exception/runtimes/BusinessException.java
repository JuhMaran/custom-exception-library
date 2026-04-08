package br.com.juhmaran.exception.runtimes;

/**
 * Exceção base para regras de negócio da aplicação.
 *
 * <p>Deve ser utilizada como classe base para exceções específicas do domínio,
 * permitindo padronização no tratamento via {@code GlobalExceptionHandler}.</p>
 *
 * <p>Recomenda-se que exceções customizadas herdem desta classe para melhor organização semântica.</p>
 *
 * @author Juliane Maran
 * @since 08/04/2026
 */
public class BusinessException extends RuntimeException {

  /**
   * @param message mensagem descritiva do erro de negócio
   */
  public BusinessException(String message) {
    super(message);
  }

}
