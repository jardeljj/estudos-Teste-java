package com.example.sw_planet_api.domain;

import static com.example.common.PlanetConstants.PLANET;
import static com.example.common.PlanetConstants.INVALID_PLANET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@ExtendWith(MockitoExtension.class)
// @SpringBootTest(classes = PlanetService.class)
public class PlanetServiceTest {

    // @Autowired
    @InjectMocks
    private PlanetService planetService;

    // @MockitoBean
    @Mock
    private PlanetRepository planetRepository;

    // operacao_estado_retorno
    @Test
    public void createPlanet_WithValidData_ReturnsPlanet() {
        // AAA
        // Arrange
        when(planetRepository.save(PLANET)).thenReturn(PLANET);

        // Act
        // system under test sistema sob teste
        Planet sut = planetService.create(PLANET);

        // Assert
        // verificando igualdade
        assertThat(sut).isEqualTo(PLANET);
    }

    @Test
    public void createPlanet_WithValidData_ThrowsException() {

        when(planetRepository.save(INVALID_PLANET)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> planetService.create(INVALID_PLANET)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getPlanet_ByExistingId_ReturnsPlanet() {
        when(planetRepository.findById(anyLong())).thenReturn(Optional.of(PLANET));

        Optional<Planet> sut = planetService.get(2L);

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(PLANET);

    }

    @Test
    public void getPlanet_ByUnexistingId_ReturnsEmpty() {
        when(planetRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Planet> sut = planetService.get(1L);

        assertThat(sut).isEmpty();
    }

}
