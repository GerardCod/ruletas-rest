package dev.gerardo.microservices.ruletas.apirest.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dev.gerardo.microservices.ruletas.apirest.models.entities.Apuesta;

@Repository
public interface ApuestaRepository extends CrudRepository<Apuesta, Integer> {

}
