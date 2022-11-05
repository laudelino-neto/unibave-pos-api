package br.com.unibave.unibaveposapi.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.unibave.unibaveposapi.entity.Transacao;

@Repository
public interface TransacoesRepository extends JpaRepository<Transacao, Integer>{
	
	@Query("SELECT t "
			+ "FROM Transacao t "
			+ "JOIN FETCH t.bandeira b "
			+ "WHERE b.id = :idDaBandeira "
			+ "ORDER BY b.nome ")
	public List<Transacao> listarPor(Integer idDaBandeira, Pageable paginacao);
	
	@Query("SELECT t "
			+ "FROM Transacao t "
			+ "JOIN FETCH t.bandeira "
			+ "WHERE t.id = :id ")
	public Transacao buscarPor(Integer id);

}
