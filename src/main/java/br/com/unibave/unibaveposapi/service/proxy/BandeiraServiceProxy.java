package br.com.unibave.unibaveposapi.service.proxy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.unibave.unibaveposapi.entity.Bandeira;
import br.com.unibave.unibaveposapi.service.BandeiraService;

@Service
@Qualifier("bandeiraServiceProxy")
public class BandeiraServiceProxy implements BandeiraService {
	
	@Autowired
	@Qualifier("bandeiraServiceImpl")
	private BandeiraService service;

	@Override
	public Bandeira inserir(Bandeira bandeira) {
		return service.inserir(bandeira);
	}

	@Override
	public Bandeira alterar(Bandeira bandeira) {	
		return service.alterar(bandeira);
	}

	@Override
	public Bandeira removerPor(Integer id) {
		return service.removerPor(id);
	}

	@Override
	public Bandeira buscarPor(Integer id) {
		return service.buscarPor(id);
	}

	@Override
	public List<Bandeira> listarTodas() {
		return service.listarTodas();
	}

}
