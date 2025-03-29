package com.example.sw_planet_api.domain;

import static com.example.common.PlanetConstants.PLANET;
import static org.assertj.core.api.Assertions.assertThat;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = PlanetService.class)
public class PlanetServiceTest {

    @Autowired
    private PlanetService planetService;
        
    // operacao_estado_retorno
    @Test
    public void createPlanet_WithValidData_ReturnsPlanet(){
        // system under test sistema sob teste
        Planet sut = planetService.create(PLANET);

        // verificando igualdade
        assertThat(sut).isEqualTo(PLANET);
    }

}
