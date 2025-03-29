package com.example.sw_planet_api.web.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sw_planet_api.domain.Planet;
import com.example.sw_planet_api.domain.PlanetService;

@RestController
@RequestMapping("/planets")
public class PlanetController {
    
    @Autowired
    private PlanetService planetService;


    @PostMapping
    public ResponseEntity<Planet> create(@RequestBody Planet planet){
        Planet planetCreate = planetService.create(planet);
        return ResponseEntity.status(HttpStatus.CREATED).body(planetCreate);
    }

    

}
