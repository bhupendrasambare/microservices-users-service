/**
 * author @bhupendrasambare
 * Date   :03/09/24
 * Time   :11:43 pm
 * Project:user service
 **/
package com.service.users.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private MultipartFile profilePicture;
    private String profilePictureUrl;
}
