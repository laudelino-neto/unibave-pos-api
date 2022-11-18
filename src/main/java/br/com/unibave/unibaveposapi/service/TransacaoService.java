package br.com.unibave.unibaveposapi.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import br.com.unibave.unibaveposapi.entity.Transacao;

@Validated
public interface TransacaoService {

	Transacao salvar(
			@Valid
			@NotNull(message = "A transação não pode ser nula")
			Transacao transacao);
	
	Transacao buscarPor(
			@Positive(message = "O id da transação para busca deve ser maior que zero")
			Integer id);
	
	List<Transacao> listarPor(
			@Positive(message = "O id bandeira deve ser maior que zero")
			Integer idDaBandeira, 
			Pageable paginacao);
	
}
