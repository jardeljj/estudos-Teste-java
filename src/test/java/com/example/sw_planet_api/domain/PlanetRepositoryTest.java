package com.example.sw_planet_api.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;

import net.bytebuddy.asm.Advice.Argument;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static com.example.sw_planet_api.common.PlanetConstants.PLANET;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


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

    @ParameterizedTest
    @MethodSource("providesInvalidPlanets")
    public void createPlanet_WithInvalidData_ThrowsException(Planet planet){
        
        assertThatThrownBy(() -> planetRepository.save(planet)).isInstanceOf(RuntimeException.class);
    }

    private static Stream<Arguments> providesInvalidPlanets(){
        return Stream.of(
            Arguments.of(new Planet(null, "climate", "terrain")),
            Arguments.of(new Planet("name", null, "terrain")),
            Arguments.of(new Planet("name", "climate", null)),
            Arguments.of(new Planet(null, null, "terrain")),
            Arguments.of(new Planet(null, "climate", null)),
            Arguments.of(new Planet("name", null, null)),
            Arguments.of(new Planet(null, null, null)),
            Arguments.of(new Planet("", "climate", "terrain")),
            Arguments.of(new Planet("name", "", "terrain")),
            Arguments.of(new Planet("name", "climate", "")),
            Arguments.of(new Planet("", "", "terrain")),
            Arguments.of(new Planet("", "climate", "")),
            Arguments.of(new Planet("name", "", "")),
            Arguments.of(new Planet("", "", "")));
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

    @Test
    public void getPlanet_ByExistingName_ReturnsPlanet() throws Exception{

        Planet planet = testEntityManager.persistFlushFind(PLANET);
        Optional<Planet> planetOpt = planetRepository.findByName(planet.getName());
        assertThat(planetOpt).isNotEmpty();
        assertThat(planetOpt.get()).isEqualTo(planet);
    }

    @Test
    public void getPlanet_ByUnExistingName_ReturnsNotFound() throws Exception{
        Optional<Planet> planetOpt = planetRepository.findByName("name");
        assertThat(planetOpt).isEmpty();
    }

   /* @Test
    public void listPlanets_ReturnsFilteredPlanets() throws Exception{
        Example<Planet> queryWithoutFilters = QueryBuilder.makeQuery(new Planet());
        Example<Planet> queryWithFilters = QueryBuilder.makeQuery(new Planet(PLANET.getClimate(), PLANET.getTerrain()));

        List<Planet> responseWithoutFilters = planetRepository.findAll(queryWithoutFilters);
        List<Planet> responseWithFilters = planetRepository.findAll(queryWithFilters);

        assertThat(responseWithoutFilters).isNotEmpty();
        assertThat(responseWithoutFilters).hasSize(3);
        assertThat(responseWithFilters).isNotEmpty();
        assertThat(responseWithFilters).hasSize(1);
        assertThat(responseWithFilters.get(0)).isEqualTo("marte");
    }*/

    @Test
    public void listPlanets_ReturnsNoPlanets() throws Exception{
        Example<Planet> query = QueryBuilder.makeQuery(new Planet());

        List<Planet> response = planetRepository.findAll(query);

        assertThat(response).isEmpty();
    }

    @Test
    public void removePlanet_WithExistingId_RemovesPlanetFromDatabase() {
        Planet planet = testEntityManager.persistFlushFind(PLANET);

        planetRepository.deleteById(planet.getId());

        Planet removedPlanet = testEntityManager.find(Planet.class, planet.getId());
        assertThat(removedPlanet).isNull();
    }

   // @Test
    //public void removePlanet_WithUnexistingId_ThrowsException() {
      //assertThatThrownBy(() -> planetRepository.deleteById(1L)).isInstanceOf(EmptyResultDataAccessException.class);
   // }

}
