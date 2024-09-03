/**
 * author @bhupendrasambare
 * Date   :03/09/24
 * Time   :11:45 pm
 * Project:user service
 **/
package com.service.users.service;

import com.service.users.config.Constants;
import com.service.users.config.Utility;
import com.service.users.controller.FileController;
import com.service.users.dto.Status;
import com.service.users.dto.request.UserUpdateRequest;
import com.service.users.dto.response.Response;
import com.service.users.model.Users;
import com.service.users.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private FileController fileController;

    // Fetch user by ID
    public ResponseEntity<Response> getUserById() {
        Utility utility = new Utility();
        Optional<Users> user = usersRepository.findByEmail(utility.getCurrentUsername());
        return user.map(users -> ResponseEntity.status(201).body(new Response(Constants.PROFILE_FOUND_SUCCESSFULLY, users))).orElseGet(() -> new ResponseEntity<>
                (new Response(Status.FAILED,
                        Constants.UNAUTHORIZED_REQUEST_CODE,
                        Constants.UNAUTHORIZED_REQUEST), HttpStatus.UNAUTHORIZED));
    }

    // Update user details
    public ResponseEntity<Response> updateUser(UserUpdateRequest request) {
        Utility utility = new Utility();
        Optional<Users> userOptional = usersRepository.findByEmail(utility.getCurrentUsername());
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            if(request.getProfilePicture()!=null){
                try{
                    String profilePictureUrl = fileController.uploadFile(request.getProfilePicture()).getBody();
                    request.setProfilePictureUrl(profilePictureUrl);
                }catch (Exception e){
                    e.printStackTrace();
                    log.error(" while uploading File ERROR:{}",e.getMessage());
                }
            }
            user.updateFields(request);
            user = usersRepository.save(user);
            return ResponseEntity.status(201).body(new Response(Constants.PROFILE_UPDATED_SUCCESSFULLY, user));
        } else {
            return new ResponseEntity<>
                    (new Response(Status.FAILED,
                            Constants.UNAUTHORIZED_REQUEST_CODE,
                            Constants.UNAUTHORIZED_REQUEST), HttpStatus.UNAUTHORIZED);
        }
    }
}
