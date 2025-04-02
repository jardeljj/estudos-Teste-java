package com.example.sw_planet_api.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetRepository extends CrudRepository<Planet, Long>, QueryByExampleExecutor<Planet>{

    
    Optional<Planet> findByName(String name);

    <S extends Planet> List<S> findAll(Example<S> example);

}
