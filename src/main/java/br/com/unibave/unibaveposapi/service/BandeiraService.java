package br.com.unibave.unibaveposapi.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.validation.annotation.Validated;

import br.com.unibave.unibaveposapi.entity.Bandeira;

@Validated
public interface BandeiraService {

	Bandeira inserir(
			@Valid 
			@NotNull(message = "A bandeira não pode ser nula")
			Bandeira bandeira);
	
	Bandeira alterar(
			@Valid 
			@NotNull(message ="A bandeira não pode ser nula")
			Bandeira bandeira);
	
	Bandeira removerPor(
			@Positive(message = "O id para remoção não pode ser menor que zero")
			Integer id);
	
	Bandeira buscarPor(
			@Positive(message = "O id para busca não pode ser menor que zero")
			Integer id);
	
	List<Bandeira> listarTodas();
	
}
