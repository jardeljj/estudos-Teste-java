package com.example.sw_planet_api;

import com.example.sw_planet_api.domain.Planet;
import org.hibernate.annotations.processing.SQL;
import org.junit.jupiter.api.Test;
import static com.example.sw_planet_api.common.PlanetConstants.PLANET;
import static com.example.sw_planet_api.common.PlanetConstants.TATOOINE;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;


@ActiveProfiles("it")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/import_planets.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = {"/remove_planets.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PlanetIT {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void creatPlanet_ReturnCreate(){
        ResponseEntity<Planet>sut = restTemplate.postForEntity("/planets", PLANET, Planet.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(sut.getBody().getId()).isNotNull();
        assertThat(sut.getBody().getName()).isEqualTo(PLANET.getName());
        assertThat(sut.getBody().getClimate()).isEqualTo(PLANET.getClimate());
        assertThat(sut.getBody().getTerrain()).isEqualTo(PLANET.getTerrain());
    }

    @Test
    public void getPlanet_ReturnsPlanet(){
        ResponseEntity<Planet> sut =  restTemplate.getForEntity("/planets/1",Planet.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sut.getBody()).isEqualTo(TATOOINE);
    }

    @Test
    public void getPlanetByName_ReturnsPlanet(){
        ResponseEntity<Planet> sut = restTemplate.getForEntity("/planets/name/" + TATOOINE.getName(),Planet.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sut.getBody()).isEqualTo(TATOOINE);
    }

    @Test
    public void listPlanet_ReturnsAllPlanets(){
        ResponseEntity<Planet []> sut = restTemplate.getForEntity("/planets", Planet[].class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sut.getBody()[0]).isEqualTo(TATOOINE);
        assertThat(sut.getBody()).hasSize(3);

    }

    @Test
    public void listPlanets_ByClimate_ReturnsPlanets(){
        ResponseEntity<Planet []> sut = restTemplate.getForEntity("/planets?climate=" + TATOOINE.getClimate(), Planet[].class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sut.getBody()[0]).isEqualTo(TATOOINE);
        assertThat(sut.getBody()).hasSize(1);

    }

    @Test
    public void listPlanets_ByTerrain_ReturnsPlanets(){

        ResponseEntity<Planet []> sut = restTemplate.getForEntity("/planets?terrain=" + TATOOINE.getTerrain(), Planet[].class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sut.getBody()[0]).isEqualTo(TATOOINE);
        assertThat(sut.getBody()).hasSize(1);
    }

    @Test
    public void removePlanet_returnsNoContent(){
        ResponseEntity<Void>sut = restTemplate.exchange("/planets/1", HttpMethod.DELETE, null,
                Void.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }


}
