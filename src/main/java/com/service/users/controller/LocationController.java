/**
 * author @bhupendrasambare
 * Date   :02/09/24
 * Time   :2:10 am
 * Project:user service
 **/
package com.service.users.controller;

import com.service.users.dto.response.Response;
import com.service.users.dto.response.recordResponse.CityResponse;
import com.service.users.dto.response.recordResponse.StateResponse;
import com.service.users.model.City;
import com.service.users.model.Country;
import com.service.users.model.State;
import com.service.users.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/location")
@Tag(name = "Location Controller", description = "APIs for Managing Countries, States, and Cities")
public class LocationController {

    @Autowired
    private LocationService locationService;

    // Country APIs
    @Operation(summary = "Create a new country", description = "Creates a new country and returns the created country.")
    @PostMapping("/countries")
    public ResponseEntity<Response> createCountry(@Valid @RequestBody Country country) {
        return locationService.createCountry(country);
    }

    @Operation(summary = "Update a country", description = "Updates an existing country and returns the updated country.")
    @PutMapping("/countries/{id}")
    public ResponseEntity<Response> updateCountry(@PathVariable Long id, @Valid @RequestBody Country country) {
        return locationService.updateCountry(id, country);
    }

    // State APIs
    @Operation(summary = "Create a new state", description = "Creates a new state and returns the created state.")
    @PostMapping("/states")
    public ResponseEntity<Response> createState(@Valid @RequestBody StateResponse state) {
        return locationService.createState(state);
    }

    @Operation(summary = "Update a state", description = "Updates an existing state and returns the updated state.")
    @PutMapping("/states/{id}")
    public ResponseEntity<Response> updateState(@PathVariable Long id, @Valid @RequestBody StateResponse state) {
        return locationService.updateState(id, state);
    }

    // City APIs
    @Operation(summary = "Create a new city", description = "Creates a new city and returns the created city.")
    @PostMapping("/cities")
    public ResponseEntity<Response> createCity(@Valid @RequestBody CityResponse city) {
        return locationService.createCity(city);
    }

    @Operation(summary = "Update a city", description = "Updates an existing city and returns the updated city.")
    @PutMapping("/cities/{id}")
    public ResponseEntity<Response> updateCity(@PathVariable Long id, @Valid @RequestBody CityResponse city) {
        return locationService.updateCity(id, city);
    }
}
