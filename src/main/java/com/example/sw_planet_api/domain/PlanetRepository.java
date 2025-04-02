package com.example.sw_planet_api.domain;

import java.util.Optional;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetRepository extends CrudRepository<Planet, Long>{

    
    Optional<Planet> findByName(String name);

}
