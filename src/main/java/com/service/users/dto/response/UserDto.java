/**
 * author @bhupendrasambare
 * Date   :27/06/24
 * Time   :5:01 pm
 * Project:microservices-registry
 **/
package com.service.users.dto.response;

import com.service.users.model.Users;
import lombok.Data;

@Data
public class UserDto extends Users {
    private String roleName;
}
