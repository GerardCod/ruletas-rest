package dev.gerardo.microservices.ruletas.apirest.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dev.gerardo.microservices.ruletas.apirest.models.entities.Ruleta;

@Repository
public interface RuletaRepository extends CrudRepository<Ruleta, Integer> {

}
