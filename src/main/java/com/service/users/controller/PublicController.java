/**
 * author @bhupendrasambare
 * Date   :02/09/24
 * Time   :2:13 am
 * Project:user service
 **/
package com.service.users.controller;

import com.service.users.dto.response.Response;
import com.service.users.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/public")
@Tag(name = "Public Controller", description = "APIs for Public support like Countries, States, and Cities, etc.")
public class PublicController {

    @Autowired
    private LocationService locationService;


    @Operation(summary = "Get a country by ID", description = "Retrieves a country by its ID.")
    @GetMapping("/countries/{id}")
    public ResponseEntity<Response> getCountry(@PathVariable Long id) {
        return locationService.getCountry(id);
    }

    @Operation(summary = "Get all countries", description = "Retrieves all countries.")
    @GetMapping("/countries")
    public ResponseEntity<Response> getAllCountries() {
        return locationService.getAllCountries();
    }

    @Operation(summary = "Get a state by ID", description = "Retrieves a state by its ID.")
    @GetMapping("/states/{id}")
    public ResponseEntity<Response> getState(@PathVariable Long id) {
        return locationService.getState(id);
    }

    @Operation(summary = "Get all states", description = "Retrieves all states.")
    @GetMapping("/states")
    public ResponseEntity<Response> getAllStates() {
        return locationService.getAllStates();
    }

    @Operation(summary = "Get a city by ID", description = "Retrieves a city by its ID.")
    @GetMapping("/cities/{id}")
    public ResponseEntity<Response> getCity(@PathVariable Long id) {
        return locationService.getCity(id);
    }

    @Operation(summary = "Get all cities", description = "Retrieves all cities.")
    @GetMapping("/cities")
    public ResponseEntity<Response> getAllCities() {
        return locationService.getAllCities();
    }
}
