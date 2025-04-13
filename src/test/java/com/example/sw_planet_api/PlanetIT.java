package com.example.sw_planet_api;

import com.example.sw_planet_api.domain.Planet;
import org.hibernate.annotations.processing.SQL;
import org.junit.jupiter.api.Test;
import static com.example.sw_planet_api.common.PlanetConstants.PLANET;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;


@ActiveProfiles("it")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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

}
