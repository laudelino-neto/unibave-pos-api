package br.com.unibave.unibaveposapi.exception;

public class AtributoObrigatorioException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public AtributoObrigatorioException(String mensagem) {
		super(mensagem);
	}
	
}
