package dev.gerardo.microservices.ruletas.apirest.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import dev.gerardo.microservices.ruletas.apirest.models.dto.CrearApuestaColorDTO;
import dev.gerardo.microservices.ruletas.apirest.models.dto.CrearApuestaNumeroDTO;
import dev.gerardo.microservices.ruletas.apirest.models.entities.Apuesta;
import dev.gerardo.microservices.ruletas.apirest.models.entities.Jugador;
import dev.gerardo.microservices.ruletas.apirest.models.enums.EstadoApuesta;
import dev.gerardo.microservices.ruletas.apirest.models.enums.RuletaColor;
import dev.gerardo.microservices.ruletas.apirest.repositories.ApuestaRepository;
import dev.gerardo.microservices.ruletas.apirest.repositories.JugadorRepository;
import org.h2.bnf.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.gerardo.microservices.ruletas.apirest.models.entities.Ruleta;
import dev.gerardo.microservices.ruletas.apirest.repositories.RuletaRepository;

@Service
public class RuletaDAOImpl implements RuletaDAO {

	@Autowired
	private RuletaRepository repository;

	@Autowired
	private ApuestaRepository apuestaRepository;

	@Autowired
	private JugadorRepository jugadorRepository;

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
		if (ruleta.getAbierta()) {
			return false;
		}

		ruleta.setAbierta(true);
		repository.save(ruleta);
		return true;
	}

	@Override
	public Boolean cerrarRuleta(Ruleta ruleta) {
		if (ruleta.getAbierta()) {
			ruleta.setAbierta(false);
			repository.save(ruleta);
			return true;
		}

		return false;
	}

	@Override
	public Optional<Apuesta> realizarApuestaNumero(Ruleta ruleta, Jugador jugador, CrearApuestaNumeroDTO entrada) {
		Apuesta apuesta = new Apuesta();
		apuesta.setRuleta(ruleta);
		apuesta.setJugador(jugador);
		apuesta.setMonto(entrada.getMonto());

		Integer valorObtenido = girarRuleta();
		if (entrada.getNumero().equals(valorObtenido)) {
			apuesta.setEstado(EstadoApuesta.GANADA);
			jugador.setSaldo(jugador.getSaldo() + (apuesta.getMonto() / 2));
		} else {
			apuesta.setEstado(EstadoApuesta.PERDIDA);
			jugador.setSaldo(jugador.getSaldo() - apuesta.getMonto());
		}

		jugadorRepository.save(jugador);
		Apuesta result = apuestaRepository.save(apuesta);
		return Optional.of(result);
	}

	@Override
	public Optional<Apuesta> realizarApuestaColor(Ruleta ruleta, Jugador jugador, CrearApuestaColorDTO entrada) {
		Apuesta apuesta = new Apuesta();
		apuesta.setRuleta(ruleta);
		apuesta.setJugador(jugador);
		apuesta.setMonto(entrada.getMonto());

		Integer valorObtenido = girarRuleta();
		RuletaColor colorResultado = (valorObtenido % 2 == 0) ? RuletaColor.ROJO : RuletaColor.NEGRO;

		if (colorResultado.equals(entrada.getColor())) {
			apuesta.setEstado(EstadoApuesta.GANADA);
			jugador.setSaldo(jugador.getSaldo() + (apuesta.getMonto() / 2));
		} else {
			apuesta.setEstado(EstadoApuesta.PERDIDA);
			jugador.setSaldo(jugador.getSaldo() - apuesta.getMonto());
		}

		jugadorRepository.save(jugador);
		Apuesta result = apuestaRepository.save(apuesta);
		return Optional.of(result);
	}

	@Override
	public Optional<List<Ruleta>> buscarTodas() {
		List<Ruleta> result = (List<Ruleta>) repository.findAll();
		return Optional.of(result);
	}


	private Integer girarRuleta() {
		Random random = new Random();
		int limiteMaximo = 37;
		return random.nextInt(limiteMaximo);
	}

}
