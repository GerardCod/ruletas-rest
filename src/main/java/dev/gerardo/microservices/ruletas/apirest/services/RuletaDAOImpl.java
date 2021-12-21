package dev.gerardo.microservices.ruletas.apirest.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.gerardo.microservices.ruletas.apirest.models.entities.Ruleta;
import dev.gerardo.microservices.ruletas.apirest.repositories.RuletaRepository;

@Service
public class RuletaDAOImpl implements RuletaDAO {

	@Autowired
	private RuletaRepository repository;
	
	@Override
	public Integer crearRuleta(Ruleta ruleta) {
		
		Ruleta result = repository.save(ruleta);
		
		return result.getId();
	}

	@Override
	public Optional<Ruleta> buscarRuletaPorId(Integer id) {
		return repository.findById(id);
	}

	@Override
	public Boolean abrirRuleta(Ruleta ruleta) {
		ruleta.setAbierta(true);
		Ruleta result = repository.save(ruleta);
		return result != null;
	}

}
