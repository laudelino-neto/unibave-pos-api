package br.com.unibave.unibaveposapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.unibave.unibaveposapi.entity.Bandeira;
import br.com.unibave.unibaveposapi.exception.RegistroNaoEncontradoException;
import br.com.unibave.unibaveposapi.repository.BandeirasRepository;
import br.com.unibave.unibaveposapi.service.BandeiraService;

@Service
@Qualifier("bandeiraServiceImpl")
public class BandeiraServiceImpl implements BandeiraService {

	@Autowired
	private BandeirasRepository repository;
	
	@Override
	public Bandeira inserir(Bandeira bandeira) {
		return repository.save(bandeira);
	}

	@Override
	public Bandeira alterar(Bandeira bandeira) {
		this.checarExistenciaNaBaseDo(bandeira.getId());
		return repository.save(bandeira);
	}
	
	private boolean checarExistenciaNaBaseDo(Integer idDaBandeira) {
		boolean isExiste = repository.existsById(idDaBandeira);
		if (!isExiste) {
			throw new RegistroNaoEncontradoException("Não foi encontrada "
					+ "bandeira com o id informado");
		}
		return isExiste;
	}

	@Override
	public Bandeira removerPor(Integer id) {
		this.checarExistenciaNaBaseDo(id);
		Bandeira bandeira = repository.buscarPor(id);
		this.repository.deleteById(id);
		return bandeira;
	}

	public Bandeira buscarPor(Integer id) {
		Bandeira bandeira = repository.buscarPor(id);
		if (bandeira != null) {
			return bandeira;
		}
		throw new RegistroNaoEncontradoException("Não existe bandeira com o id informado");
	}
	
	@Override
	public List<Bandeira> listarTodas() {
		return repository.listarTodas();
	}

}
