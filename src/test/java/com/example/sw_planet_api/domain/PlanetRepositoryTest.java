package com.example.sw_planet_api.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static com.example.common.PlanetConstants.PLANET;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DataJpaTest
public class PlanetRepositoryTest {

    @Autowired
    private PlanetRepository planetRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @AfterEach
    public void afterEach(){
        PLANET.setId(null);
    }

    @Test
    public void createPlanet_WithValidData_ReturnPlanet(){
       Planet planet = planetRepository.save(PLANET);
       Planet sut = testEntityManager.find(Planet.class, planet.getId());

        assertThat(sut).isNotNull();
        assertThat(sut.getName()).isEqualTo(PLANET.getName());
        assertThat(sut.getClimate()).isEqualTo(PLANET.getClimate());
        assertThat(sut.getTerrain()).isEqualTo(PLANET.getTerrain());
    }

    @Test
    public void createPlanet_WithInvalidData_ThrowsException(){
        Planet emptyPlanet = new Planet();
        Planet invalidPlanet =  new Planet("", "", "");
        assertThatThrownBy(() -> planetRepository.save(emptyPlanet)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> planetRepository.save(invalidPlanet)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void createPlanet_WithExistName_ThrowsExeception(){
        Planet planet = testEntityManager.persistFlushFind(PLANET);
        testEntityManager.detach(planet);
        planet.setId(null);
        assertThatThrownBy(() -> planetRepository.save(planet)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getPlanet_ByExistingId_ReturnsPlanet() throws Exception{
        Planet planet = testEntityManager.persistFlushFind(PLANET);
        Optional<Planet> planetOpt = planetRepository.findById(planet.getId());
        assertThat(planetOpt).isNotEmpty();
        assertThat(planetOpt.get()).isEqualTo(planet);
    }
    @Test
    public void getPlanet_ByUnexistingId_ReturnsNotFound() throws Exception{
        Optional<Planet> planetOpt = planetRepository.findById(1L);
        assertThat(planetOpt).isEmpty();
    }

}
