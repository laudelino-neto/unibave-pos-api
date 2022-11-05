package br.com.unibave.unibaveposapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.unibave.unibaveposapi.entity.Bandeira;

@Repository
public interface BandeirasRepository extends JpaRepository<Bandeira, Integer>{
	
	@Query("SELECT b "
			+ "FROM Bandeira b "
			+ "WHERE b.id = :id ")
	Bandeira buscarPor(Integer id);
	
	@Query("SELECT b "
			+ "FROM Bandeira b "
			+ "ORDER BY b.nome ")
	List<Bandeira> listarTodas();

}
