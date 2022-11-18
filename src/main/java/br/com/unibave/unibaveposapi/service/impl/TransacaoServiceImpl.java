package br.com.unibave.unibaveposapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.unibave.unibaveposapi.entity.Transacao;
import br.com.unibave.unibaveposapi.exception.RegistroNaoEncontradoException;
import br.com.unibave.unibaveposapi.repository.TransacoesRepository;
import br.com.unibave.unibaveposapi.service.TransacaoService;

@Service
@Qualifier("transacaoServiceImpl")
public class TransacaoServiceImpl implements TransacaoService {

	@Autowired
	private TransacoesRepository repository;
	
	@Override
	public Transacao salvar(Transacao transacao) {
		return repository.save(transacao);
	}

	@Override
	public Transacao buscarPor(Integer id) {
		Transacao transacaoEncontrada = repository.buscarPor(id);
		if (transacaoEncontrada != null) {
			return transacaoEncontrada;
		}
		throw new RegistroNaoEncontradoException("O id não existe para busca");
	}

	@Override
	public List<Transacao> listarPor(Integer idDaBandeira, Pageable paginacao) {
		return repository.listarPor(idDaBandeira, paginacao);
	}

}
