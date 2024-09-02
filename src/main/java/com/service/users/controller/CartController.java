/**
 * author @bhupendrasambare
 * Date   :03/09/24
 * Time   :12:27 am
 * Project:user service
 **/
package com.service.users.controller;

import com.service.users.dto.request.AddToCartRequest;
import com.service.users.dto.response.Response;
import com.service.users.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/cart")
@Tag(name = "Cart Controller", description = "APIs for Managing MainCart and LaterCart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Operation(summary = "Add item to MainCart", description = "Adds an item to the user's MainCart.")
    @PostMapping("/main")
    public ResponseEntity<Response> addToMainCart(@RequestBody AddToCartRequest request) {
        return cartService.addToMainCart(request);
    }

    @Operation(summary = "Remove item from MainCart", description = "Removes an item from the user's MainCart.")
    @DeleteMapping("/main/{id}")
    public ResponseEntity<Response> removeFromMainCart(@PathVariable(name = "id") Long productId) {
        return cartService.removeFromMainCart(productId);
    }

    @Operation(summary = "Remove item from LaterCart", description = "Removes an item from the user's LaterCart.")
    @DeleteMapping("/later/{id}")
    public ResponseEntity<Response> removeFromLaterCart(@PathVariable(name = "id") Long productId) {
        return cartService.removeFromLaterCart(productId);
    }

    @Operation(summary = "Get MainCart by User ID", description = "Retrieves all items in the user's MainCart.")
    @GetMapping("/main")
    public ResponseEntity<Response> getMainCartByUserId() {
        return cartService.getMainCartByUserId();
    }

    @Operation(summary = "Shift item from MainCart to LaterCart", description = "Moves an item from the user's MainCart to LaterCart.")
    @PostMapping("/main-to-later/{productId}")
    public ResponseEntity<Response> shiftToLaterCart(@PathVariable Long productId) {
        return cartService.shiftToLaterCart(productId);
    }

    @Operation(summary = "Move item from LaterCart to MainCart", description = "Moves an item from the user's LaterCart to MainCart.")
    @PostMapping("/later-to-main/{productId}")
    public ResponseEntity<Response> moveToMainCart(@PathVariable Long productId) {
        return cartService.moveToMainCart(productId);
    }

    @Operation(summary = "Get LaterCart by User ID", description = "Retrieves all items in the user's LaterCart.")
    @GetMapping("/later")
    public ResponseEntity<Response> getLaterCartByUserId() {
        return cartService.getLaterCartByUserId();
    }
}
