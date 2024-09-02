/**
 * author @bhupendrasambare
 * Date   :03/09/24
 * Time   :12:31 am
 * Project:user service
 **/
package com.service.users.service;

import com.service.users.config.Constants;
import com.service.users.config.Utility;
import com.service.users.dto.Status;
import com.service.users.dto.request.AddToCartRequest;
import com.service.users.dto.response.CartProductResponse;
import com.service.users.dto.response.Response;
import com.service.users.model.LaterCart;
import com.service.users.model.MainCart;
import com.service.users.model.Users;
import com.service.users.model.embaded.UserProductId;
import com.service.users.repository.LaterCartRepository;
import com.service.users.repository.MainCartRepository;
import com.service.users.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private MainCartRepository mainCartRepository;

    @Autowired
    private LaterCartRepository laterCartRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ProductService productService;

    public ResponseEntity<Response> addToMainCart(AddToCartRequest request) {
        Utility utility = new Utility();
        String username = utility.getCurrentUsername();
        Users users = usersRepository.findByEmail(username).orElse(null);
        if(users!=null){
            UserProductId userProductId = new UserProductId(users.getId(), request.productId());
            Optional<MainCart> existingCartItem = mainCartRepository.findById(userProductId);

            MainCart mainCartItem;
            if (existingCartItem.isPresent()) {
                mainCartItem = existingCartItem.get();
                mainCartItem.setQuantity(mainCartItem.getQuantity() + request.quantity());
            } else {
                mainCartItem = new MainCart();
                mainCartItem.setId(userProductId);
                mainCartItem.setQuantity(request.quantity());
                mainCartItem.setCreatedDate(LocalDateTime.now());
            }

            MainCart savedCartItem = mainCartRepository.save(mainCartItem);
            return ResponseEntity.status(201).body(new Response("Item added to MainCart successfully.", savedCartItem));
        }else{
            return new ResponseEntity<>
                    (new Response(Status.FAILED,
                            Constants.UNAUTHORIZED_REQUEST_CODE,
                            Constants.UNAUTHORIZED_REQUEST), HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<Response> removeFromMainCart(Long productId) {
        Utility utility = new Utility();
        String username = utility.getCurrentUsername();
        Users users = usersRepository.findByEmail(username).orElse(null);
        if(users!=null){
            UserProductId userProductId = new UserProductId(users.getId(), productId);
            Optional<MainCart> cartItemOpt = mainCartRepository.findById(userProductId);
            if (cartItemOpt.isEmpty()) {
                return ResponseEntity.status(404).body(new Response("Item not found in MainCart.", null));
            }
            mainCartRepository.delete(cartItemOpt.get());
            return ResponseEntity.ok(new Response("Item removed from MainCart successfully.", null));
        }else{
            return new ResponseEntity<>
                    (new Response(Status.FAILED,
                            Constants.UNAUTHORIZED_REQUEST_CODE,
                            Constants.UNAUTHORIZED_REQUEST), HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<Response> removeFromLaterCart(Long productId) {
        Utility utility = new Utility();
        String username = utility.getCurrentUsername();
        Users users = usersRepository.findByEmail(username).orElse(null);
        if(users!=null){
            UserProductId userProductId = new UserProductId(users.getId(), productId);
            Optional<LaterCart> cartItemOpt = laterCartRepository.findById(userProductId);
            if (cartItemOpt.isEmpty()) {
                return ResponseEntity.status(404).body(new Response("Item not found in MainCart.", null));
            }
            laterCartRepository.delete(cartItemOpt.get());
            return ResponseEntity.ok(new Response("Item removed from MainCart successfully.", null));
        }else{
            return new ResponseEntity<>
                    (new Response(Status.FAILED,
                            Constants.UNAUTHORIZED_REQUEST_CODE,
                            Constants.UNAUTHORIZED_REQUEST), HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<Response> getMainCartByUserId() {
        Utility utility = new Utility();
        String username = utility.getCurrentUsername();
        Users users = usersRepository.findByEmail(username).orElse(null);
        if(users!=null){
            List<MainCart> cartItems = mainCartRepository.findByIdUserId(users.getId());
            List<CartProductResponse> productList = new ArrayList<>();
            for(MainCart mainCart:cartItems){
                Response tempProduct = productService.getProductById(mainCart.getId().getProductId());
                if(tempProduct!=null && tempProduct.getData()!=null){
                    productList.add(new CartProductResponse(tempProduct.getData(),mainCart.getQuantity()));
                }
            }
            return ResponseEntity.ok(new Response("MainCart retrieved successfully.", productList));
        }else{
            return new ResponseEntity<>
                    (new Response(Status.FAILED,
                            Constants.UNAUTHORIZED_REQUEST_CODE,
                            Constants.UNAUTHORIZED_REQUEST), HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<Response> shiftToLaterCart(Long productId) {
        Utility utility = new Utility();
        String username = utility.getCurrentUsername();
        Users users = usersRepository.findByEmail(username).orElse(null);
        if(users!=null){
            UserProductId userProductId = new UserProductId(users.getId(), productId);
            Optional<MainCart> mainCartItemOpt = mainCartRepository.findById(userProductId);
            if (mainCartItemOpt.isEmpty()) {
                return ResponseEntity.status(404).body(new Response("Item not found in MainCart.", null));
            }

            MainCart mainCartItem = mainCartItemOpt.get();
            mainCartRepository.delete(mainCartItem);

            LaterCart laterCartItem = new LaterCart();
            laterCartItem.setId(mainCartItem.getId());
            laterCartItem.setQuantity(mainCartItem.getQuantity());
            laterCartItem.setCreatedDate(LocalDateTime.now());
            laterCartRepository.save(laterCartItem);

            return ResponseEntity.ok(new Response("Item moved from MainCart to LaterCart successfully.", laterCartItem));
        }else{
            return new ResponseEntity<>
                    (new Response(Status.FAILED,
                            Constants.UNAUTHORIZED_REQUEST_CODE,
                            Constants.UNAUTHORIZED_REQUEST), HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<Response> moveToMainCart(Long productId) {
        Utility utility = new Utility();
        String username = utility.getCurrentUsername();
        Users users = usersRepository.findByEmail(username).orElse(null);
        if(users!=null){
            UserProductId userProductId = new UserProductId(users.getId(), productId);
            Optional<LaterCart> laterCartItemOpt = laterCartRepository.findById(userProductId);
            if (laterCartItemOpt.isEmpty()) {
                return ResponseEntity.status(404).body(new Response("Item not found in LaterCart.", null));
            }

            LaterCart laterCartItem = laterCartItemOpt.get();
            laterCartRepository.delete(laterCartItem);

            MainCart mainCartItem = new MainCart();
            mainCartItem.setId(laterCartItem.getId());
            mainCartItem.setQuantity(laterCartItem.getQuantity());
            mainCartItem.setCreatedDate(LocalDateTime.now());
            mainCartRepository.save(mainCartItem);

            return ResponseEntity.ok(new Response("Item moved from LaterCart to MainCart successfully.", mainCartItem));
        }else{
            return new ResponseEntity<>
                    (new Response(Status.FAILED,
                            Constants.UNAUTHORIZED_REQUEST_CODE,
                            Constants.UNAUTHORIZED_REQUEST), HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<Response> getLaterCartByUserId() {
        Utility utility = new Utility();
        String username = utility.getCurrentUsername();
        Users users = usersRepository.findByEmail(username).orElse(null);
        if(users!=null){
            List<LaterCart> cartItems = laterCartRepository.findByIdUserId(users.getId());
            return ResponseEntity.ok(new Response("LaterCart retrieved successfully.", cartItems));
        }else{
            return new ResponseEntity<>
                    (new Response(Status.FAILED,
                            Constants.UNAUTHORIZED_REQUEST_CODE,
                            Constants.UNAUTHORIZED_REQUEST), HttpStatus.UNAUTHORIZED);
        }
    }
}
