package com.example.sw_planet_api.web.Controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.sw_planet_api.domain.Planet;
import com.example.sw_planet_api.domain.PlanetService;

@RestController
@RequestMapping("/planets")
public class PlanetController {
    
    @Autowired
    private PlanetService planetService;


    @PostMapping
    public ResponseEntity<Planet> create(@RequestBody @Valid Planet planet){
        Planet planetCreate = planetService.create(planet);
        return ResponseEntity.status(HttpStatus.CREATED).body(planetCreate);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Planet> get(@PathVariable("id") Long id){
        return planetService.get(id).map(planet -> ResponseEntity.ok(planet))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Planet> getByName(@PathVariable("name") String name){
        return planetService.getByName(name).map(planet -> ResponseEntity.ok(planet))
        .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping
    public ResponseEntity<List<Planet>> listaPlaneta(@RequestParam(required = false) String terrain, @RequestParam(required = false) String climate){
        List<Planet> planets = planetService.listPlanets(terrain, climate);
        return ResponseEntity.ok(planets);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove (@PathVariable("id") Long id){
        planetService.remove(id);
        return ResponseEntity.noContent().build();
    }

}
