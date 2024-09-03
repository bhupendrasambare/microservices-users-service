/**
 * author @bhupendrasambare
 * Date   :02/09/24
 * Time   :1:49 am
 * Project:user service
 **/
package com.service.users.controller;

import com.service.users.dto.request.AddressRequest;
import com.service.users.dto.response.Response;
import com.service.users.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/address")
@Tag(name = "Address Controller", description = "APIs for Managing User Addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Operation(summary = "Create a new address", description = "Creates a new address for a user and returns the created address.")
    @PostMapping("/add")
    public ResponseEntity<Response> createAddress(@Valid @RequestBody AddressRequest addressRequest) {
        return addressService.createAddress(addressRequest);
    }

    @Operation(summary = "Get Users address", description = "Get address of user based on login token")
    @GetMapping
    public ResponseEntity<Response> getAddress() {
        return addressService.getAddress();
    }
}
