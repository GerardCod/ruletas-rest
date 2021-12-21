package dev.gerardo.microservices.ruletas.apirest.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dev.gerardo.microservices.ruletas.apirest.models.entities.Jugador;

@Repository
public interface JugadorRepository extends CrudRepository<Jugador, Integer> {

}
