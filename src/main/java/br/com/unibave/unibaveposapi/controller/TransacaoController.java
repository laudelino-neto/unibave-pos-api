package br.com.unibave.unibaveposapi.controller;

import java.io.Serializable;
import java.net.URI;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.unibave.unibaveposapi.controller.converter.MapConverter;
import br.com.unibave.unibaveposapi.entity.Transacao;
import br.com.unibave.unibaveposapi.exception.ConverterException;
import br.com.unibave.unibaveposapi.service.TransacaoService;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private MapConverter mapConverter;
	
	@Autowired
	@Qualifier("transacaoServiceProxy")
	private TransacaoService service;
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> inserir(@RequestBody Map<String, Object> transacaoMap){
		if (transacaoMap != null) {
			Transacao transacaoConvertida = mapper.convertValue(transacaoMap, Transacao.class);
			Transacao transacaoSalva = service.salvar(transacaoConvertida);
			return ResponseEntity.created(URI.create("/transacoes/id/" 
					+ transacaoSalva.getId())).build();
		}
		throw new ConverterException("A transação enviada para inserção não existe");
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<?> buscar(@PathVariable("id") Integer id){
		return ResponseEntity.ok(mapConverter.toJsonMap(service.buscarPor(id)));
	}
	
	@GetMapping("/id-bandeira/{id-bandeira}")
	public ResponseEntity<?> listar(@PathVariable("id-bandeira") Integer idDaBandeira,
			@PageableDefault(page = 0, size = 10) Pageable paginacao){
		return ResponseEntity.ok(mapConverter.toJsonList(
				service.listarPor(idDaBandeira, paginacao)));
	}
	
}
