/**
 * author @bhupendrasambare
 * Date   :02/09/24
 * Time   :2:11 am
 * Project:user service
 **/
package com.service.users.service;

import com.service.users.config.Constants;
import com.service.users.dto.Status;
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
                .orElse(null);
        if(existingCountry!=null){
            existingCountry.setName(country.getName());
            existingCountry.setUpdatedAt(LocalDateTime.now());
            Country updatedCountry = countryRepository.save(existingCountry);
            return ResponseEntity.ok(new Response(Constants.COUNTRY_UPDATED_SUCCESSFULLY, updatedCountry));
        }else{
            return ResponseEntity.ok(new Response(Status.FAILED,Constants.COUNTRY_NOT_FOUND_CODE,Constants.COUNTRY_NOT_FOUND));
        }
    }

    public ResponseEntity<Response> getCountry(Long id) {
        Country country = countryRepository.findById(id)
                .orElse(null);
        if(country!=null){
            return ResponseEntity.ok(new Response(Constants.COUNTRY_RETRIEVED_SUCCESSFULLY, country));
        }else{
            return ResponseEntity.ok(new Response(Status.FAILED,Constants.COUNTRY_NOT_FOUND_CODE,Constants.COUNTRY_NOT_FOUND));
        }
    }

    public ResponseEntity<Response> getAllCountries() {
        List<Country> countries = countryRepository.findAll();
        return ResponseEntity.ok(new Response(Constants.COUNTRIES_RETRIEVED_SUCCESSFULLY, countries));
    }

    // State CRUD operations
    public ResponseEntity<Response> createState(StateResponse request) {
        Country country = countryRepository.findById(request.countryId()).orElse(null);
        if(country!=null){
            State state = new State();
            state.setName(request.name());
            state.setCountry(country);
            state.setCreatedAt(LocalDateTime.now());
            state.setUpdatedAt(LocalDateTime.now());
            State savedState = stateRepository.save(state);
            StateResponse stateResponse = mapToStateResponse(savedState);
            return ResponseEntity.ok(new Response(Constants.STATE_CREATED_SUCCESSFULLY, stateResponse));
        }else {
            return ResponseEntity.ok(new Response(Status.FAILED,Constants.COUNTRY_NOT_FOUND_CODE,Constants.COUNTRY_NOT_FOUND));
        }
    }

    public ResponseEntity<Response> updateState(Long id, StateResponse request) {
        State existingState = stateRepository.findById(id)
                .orElse(null);
        if(existingState!=null){
            existingState.setName(request.name());
            existingState.setUpdatedAt(LocalDateTime.now());
            if(request.countryId()!=null && request.countryId()!=0){
                countryRepository.findById(request.countryId()).ifPresent(existingState::setCountry);
            }
            State updatedState = stateRepository.save(existingState);
            StateResponse stateResponse = mapToStateResponse(updatedState);
            return ResponseEntity.ok(new Response(Constants.STATE_UPDATED_SUCCESSFULLY, stateResponse));
        }else {
            return ResponseEntity.ok(new Response(Status.FAILED,Constants.STATE_NOT_FOUND_CODE,Constants.STATE_NOT_FOUND));
        }
    }

    public ResponseEntity<Response> getState(Long id) {
        State existingState = stateRepository.findById(id)
                .orElse(null);
        if(existingState!=null){
            return ResponseEntity.ok(new Response(Constants.STATE_RETRIEVED_SUCCESSFULLY, existingState));
        }else {
            return ResponseEntity.ok(new Response(Status.FAILED,Constants.STATE_NOT_FOUND_CODE,Constants.STATE_NOT_FOUND));
        }
    }

    public ResponseEntity<Response> getAllStates(Long countryId) {
        Country country = countryRepository.findById(countryId).orElse(null);
        if(country!=null){
            List<State> states = stateRepository.findAllByCountry(countryId);
            List<StateResponse> stateResponses = states.stream()
                    .map(this::mapToStateResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new Response(Constants.STATES_RETRIEVED_SUCCESSFULLY, stateResponses));
        }else{
            return ResponseEntity.ok(new Response(Status.FAILED,Constants.COUNTRY_NOT_FOUND_CODE,Constants.COUNTRY_NOT_FOUND));
        }
    }

    public ResponseEntity<Response> getAllStates() {
        List<State> states = stateRepository.findAllByName();
        List<StateResponse> stateResponses = states.stream()
                .map(this::mapToStateResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new Response(Constants.STATES_RETRIEVED_SUCCESSFULLY, stateResponses));
    }

    // City CRUD operations
    public ResponseEntity<Response> createCity(CityResponse request) {
        State state = stateRepository.findById(request.stateId()).orElse(null);
        if(state!=null){
            City city = new City();
            city.setState(state);
            city.setName(request.name());
            city.setPinCode(request.pinCode());
            city.setCreatedAt(LocalDateTime.now());
            city.setUpdatedAt(LocalDateTime.now());
            City savedCity = cityRepository.save(city);
            CityResponse cityResponse = mapToCityResponse(savedCity);
            return ResponseEntity.ok(new Response(Constants.CITY_CREATED_SUCCESSFULLY, cityResponse));
        }else{
            return ResponseEntity.ok(new Response(Status.FAILED,Constants.STATE_NOT_FOUND_CODE,Constants.STATE_NOT_FOUND));
        }
    }

    public ResponseEntity<Response> updateCity(Long id, CityResponse request) {
        City existingCity = cityRepository.findById(id)
                .orElseThrow(null);
        if(existingCity!=null){
            existingCity.setName(request.name());
            existingCity.setPinCode(request.pinCode());
            existingCity.setUpdatedAt(LocalDateTime.now());
            if(request.stateId()!=null && request.stateId()!=0){
                stateRepository.findById(request.stateId()).ifPresent(existingCity::setState);
            }
            City updatedCity = cityRepository.save(existingCity);
            CityResponse cityResponse = mapToCityResponse(updatedCity);
            return ResponseEntity.ok(new Response(Constants.CITY_UPDATED_SUCCESSFULLY,  cityResponse));
        }else{
            return ResponseEntity.ok(new Response(Status.FAILED,Constants.CITY_NOT_FOUND_CODE,Constants.CITY_NOT_FOUND));
        }
    }

    public ResponseEntity<Response> getCity(Long id) {
        City city = cityRepository.findById(id)
                .orElse(null);
        if(city!=null){
            CityResponse cityResponse = mapToCityResponse(city);
            return ResponseEntity.ok(new Response(Constants.CITY_RETRIEVED_SUCCESSFULLY,  cityResponse));
        }else {
            return ResponseEntity.ok(new Response(Status.FAILED,Constants.CITY_NOT_FOUND_CODE,Constants.CITY_NOT_FOUND));
        }
    }

    public ResponseEntity<Response> getAllCities() {
        List<City> cities = cityRepository.findAllByName();
        List<CityResponse> cityResponses = cities.stream()
                .map(this::mapToCityResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new Response(Constants.CITIES_RETRIEVED_SUCCESSFULLY, cityResponses));
    }

    public ResponseEntity<Response> getAllCities(Long id) {
        State state = stateRepository.findById(id).orElse(null);
        if(state!=null){
            List<City> cities = cityRepository.findByStateId(id);
            List<CityResponse> cityResponses = cities.stream()
                    .map(this::mapToCityResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new Response(Constants.CITIES_RETRIEVED_SUCCESSFULLY, cityResponses));
        }else {
            return ResponseEntity.ok(new Response(Status.FAILED,Constants.STATE_NOT_FOUND_CODE,Constants.STATE_NOT_FOUND));
        }
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
