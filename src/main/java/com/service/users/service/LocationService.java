/**
 * author @bhupendrasambare
 * Date   :02/09/24
 * Time   :2:11 am
 * Project:user service
 **/
package com.service.users.service;

import com.service.users.config.Constants;
import com.service.users.dto.response.Response;
import com.service.users.dto.response.recordResponse.CityResponse;
import com.service.users.dto.response.recordResponse.StateResponse;
import com.service.users.model.City;
import com.service.users.model.Country;
import com.service.users.model.State;
import com.service.users.repository.CityRepository;
import com.service.users.repository.CountryRepository;
import com.service.users.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CityRepository cityRepository;

    // Country CRUD operations
    public ResponseEntity<Response> createCountry(Country country) {
        country.setCreatedAt(LocalDateTime.now());
        country.setUpdatedAt(LocalDateTime.now());
        Country savedCountry = countryRepository.save(country);
        return ResponseEntity.ok(new Response(Constants.COUNTRY_CREATED_SUCCESSFULLY, savedCountry));
    }

    public ResponseEntity<Response> updateCountry(Long id, Country country) {
        Country existingCountry = countryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Country not found with id: " + id));
        existingCountry.setName(country.getName());
        existingCountry.setUpdatedAt(LocalDateTime.now());
        Country updatedCountry = countryRepository.save(existingCountry);
        return ResponseEntity.ok(new Response(Constants.COUNTRY_UPDATED_SUCCESSFULLY, updatedCountry));
    }

    public ResponseEntity<Response> getCountry(Long id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Country not found with id: " + id));
        return ResponseEntity.ok(new Response(Constants.COUNTRY_RETRIEVED_SUCCESSFULLY, country));
    }

    public ResponseEntity<Response> getAllCountries() {
        List<Country> countries = countryRepository.findAll();
        return ResponseEntity.ok(new Response(Constants.COUNTRIES_RETRIEVED_SUCCESSFULLY, countries));
    }

    // State CRUD operations
    public ResponseEntity<Response> createState(State state) {
        state.setCreatedAt(LocalDateTime.now());
        state.setUpdatedAt(LocalDateTime.now());
        State savedState = stateRepository.save(state);
        StateResponse stateResponse = mapToStateResponse(savedState);
        return ResponseEntity.ok(new Response(Constants.STATE_CREATED_SUCCESSFULLY, stateResponse));
    }

    public ResponseEntity<Response> updateState(Long id, State state) {
        State existingState = stateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("State not found with id: " + id));
        existingState.setName(state.getName());
        existingState.setUpdatedAt(LocalDateTime.now());
        existingState.setCountry(state.getCountry());
        State updatedState = stateRepository.save(existingState);
        StateResponse stateResponse = mapToStateResponse(updatedState);
        return ResponseEntity.ok(new Response(Constants.STATE_UPDATED_SUCCESSFULLY, stateResponse));
    }

    public ResponseEntity<Response> getState(Long id) {
        State state = stateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("State not found with id: " + id));
        StateResponse stateResponse = mapToStateResponse(state);
        return ResponseEntity.ok(new Response(Constants.STATE_RETRIEVED_SUCCESSFULLY, stateResponse));
    }

    public ResponseEntity<Response> getAllStates() {
        List<State> states = stateRepository.findAll();
        List<StateResponse> stateResponses = states.stream()
                .map(this::mapToStateResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new Response(Constants.STATES_RETRIEVED_SUCCESSFULLY, stateResponses));
    }

    // City CRUD operations
    public ResponseEntity<Response> createCity(City city) {
        city.setCreatedAt(LocalDateTime.now());
        city.setUpdatedAt(LocalDateTime.now());
        City savedCity = cityRepository.save(city);
        CityResponse cityResponse = mapToCityResponse(savedCity);
        return ResponseEntity.ok(new Response(Constants.CITY_CREATED_SUCCESSFULLY, cityResponse));
    }

    public ResponseEntity<Response> updateCity(Long id, City city) {
        City existingCity = cityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("City not found with id: " + id));
        existingCity.setName(city.getName());
        existingCity.setPinCode(city.getPinCode());
        existingCity.setUpdatedAt(LocalDateTime.now());
        existingCity.setState(city.getState());
        City updatedCity = cityRepository.save(existingCity);
        CityResponse cityResponse = mapToCityResponse(updatedCity);
        return ResponseEntity.ok(new Response(Constants.CITY_UPDATED_SUCCESSFULLY,  cityResponse));
    }

    public ResponseEntity<Response> getCity(Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("City not found with id: " + id));
        CityResponse cityResponse = mapToCityResponse(city);
        return ResponseEntity.ok(new Response(Constants.CITY_RETRIEVED_SUCCESSFULLY,  cityResponse));
    }

    public ResponseEntity<Response> getAllCities() {
        List<City> cities = cityRepository.findAll();
        List<CityResponse> cityResponses = cities.stream()
                .map(this::mapToCityResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new Response(Constants.CITIES_RETRIEVED_SUCCESSFULLY, cityResponses));
    }

    // Utility methods to map entities to DTOs
    private StateResponse mapToStateResponse(State state) {
        if(state.getCountry()!=null){
            return new StateResponse(
                    state.getId(),
                    state.getName(),
                    state.getCreatedAt(),
                    state.getUpdatedAt(),
                    state.getCountry().getId(),
                    state.getCountry().getName()
            );
        }else{
            return new StateResponse(
                    state.getId(),
                    state.getName(),
                    state.getCreatedAt(),
                    state.getUpdatedAt(),
                    null,
                    null
            );
        }
    }

    private CityResponse mapToCityResponse(City city) {
        if(city.getState()!=null){
            if(city.getState().getCountry()!=null){
                return new CityResponse(
                        city.getId(),
                        city.getName(),
                        city.getPinCode(),
                        city.getCreatedAt(),
                        city.getUpdatedAt(),
                        city.getState().getId(),
                        city.getState().getName(),
                        city.getState().getCountry().getId(),
                        city.getState().getCountry().getName()
                );
            }else{
                return new CityResponse(
                        city.getId(),
                        city.getName(),
                        city.getPinCode(),
                        city.getCreatedAt(),
                        city.getUpdatedAt(),
                        city.getState().getId(),
                        city.getState().getName(),
                        null,
                        null
                );
            }
        }else{
            return new CityResponse(
                    city.getId(),
                    city.getName(),
                    city.getPinCode(),
                    city.getCreatedAt(),
                    city.getUpdatedAt(),
                    null,
                    null,
                    null,
                    null
            );
        }
    }
}
