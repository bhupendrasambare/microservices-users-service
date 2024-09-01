/**
 * author @bhupendrasambare
 * Date   :02/09/24
 * Time   :1:50 am
 * Project:user service
 **/
package com.service.users.service;

import com.service.users.config.Constants;
import com.service.users.config.Utility;
import com.service.users.dto.Status;
import com.service.users.dto.request.AddressRequest;
import com.service.users.dto.response.Response;
import com.service.users.model.Address;
import com.service.users.model.City;
import com.service.users.model.Users;
import com.service.users.repository.AddressRepository;
import com.service.users.repository.CityRepository;
import com.service.users.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private UsersRepository usersRepository;

    public ResponseEntity<Response> createAddress(AddressRequest addressRequest) {
        Address address = new Address();
        Utility utility = new Utility();
        if(utility.getCurrentUsername() !=null ){
            Users users = usersRepository.findByEmail(utility.getCurrentUsername()).orElse(null);
            if(users!=null && users.getId() != addressRequest.userId() ){

                if (addressRequest.cityId() != null) {
                    City city = cityRepository.findById(addressRequest.cityId())
                            .orElseThrow(() -> new IllegalArgumentException("Invalid cityId: " + addressRequest.cityId()));
                    address.setCityId(city);
                    address.setCity(city.getName());
                    address.setState(city.getState().getName());
                    address.setCountry(city.getState().getCountry().getName());
                    address.setPostalCode(city.getPinCode());
                } else {
                    address.setCity(addressRequest.city());
                    address.setState(addressRequest.state());
                    address.setCountry(addressRequest.country());
                    address.setPostalCode(addressRequest.postalCode());
                }

                address.setLandmark(addressRequest.landmark());
                address.setStreet(addressRequest.street());
                address.setUserId(addressRequest.userId());
                address.setCreatedAt(LocalDateTime.now());
                address.setUpdatedAt(LocalDateTime.now());

                address = addressRepository.save(address);
                return ResponseEntity.status(201).body(new Response(Constants.ADDRESS_CREATED_SUCCESSFULLY, address));
            }else{
                return new ResponseEntity<>
                        (new Response(Status.FAILED,
                                Constants.UNAUTHORIZED_REQUEST_CODE,
                                Constants.UNAUTHORIZED_REQUEST), HttpStatus.UNAUTHORIZED);
            }
        }else{
            return new ResponseEntity<>
                    (new Response(Status.FAILED,
                            Constants.UNAUTHORIZED_REQUEST_CODE,
                            Constants.UNAUTHORIZED_REQUEST), HttpStatus.UNAUTHORIZED);
        }
    }
}
