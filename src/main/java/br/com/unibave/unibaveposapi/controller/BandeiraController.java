package br.com.unibave.unibaveposapi.controller;

import java.io.Serializable;
import java.net.URI;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.unibave.unibaveposapi.controller.converter.MapConverter;
import br.com.unibave.unibaveposapi.entity.Bandeira;
import br.com.unibave.unibaveposapi.exception.ConverterException;
import br.com.unibave.unibaveposapi.service.BandeiraService;

@RestController
@RequestMapping("/bandeiras")
public class BandeiraController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private MapConverter mapConverter;
	
	@Autowired
	@Qualifier("bandeiraServiceProxy")
	private BandeiraService service;
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> inserir(@RequestBody Map<String, Object> bandeiraMap){
		if (bandeiraMap != null) {
			Bandeira bandeiraConvertida = mapper.convertValue(bandeiraMap, Bandeira.class);
			Bandeira bandeiraSalva = service.inserir(bandeiraConvertida);
			return ResponseEntity.created(URI.create("/bandeiras/id/" 
					+ bandeiraSalva.getId())).build();
		}
		throw new ConverterException("A bandeira enviada para inserção não existe");
	}

	@PutMapping
	@Transactional
	public ResponseEntity<?> alterar(Map<String, Object> bandeiraMap){
		if (bandeiraMap != null) {
			Bandeira bandeiraConvertida = mapper.convertValue(bandeiraMap, Bandeira.class);
			Bandeira bandeiraAtualizada = service.alterar(bandeiraConvertida);
			return ResponseEntity.ok(mapConverter.toJsonMap(bandeiraAtualizada));
		}
		throw new ConverterException("A bandeira enviada para alteração não existe");
	}
	
	@DeleteMapping("/id/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable("id") Integer id){
		return ResponseEntity.ok(mapConverter.toJsonMap(service.removerPor(id)));
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<?> buscar(@PathVariable("id") Integer id){
		return ResponseEntity.ok(mapConverter.toJsonMap(service.buscarPor(id)));
	}
	
	@GetMapping
	public ResponseEntity<?> listarTodas(){
		return ResponseEntity.ok(mapConverter.toJsonList(service.listarTodas()));
	}
	
}
