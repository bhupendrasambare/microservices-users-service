/**
 * author @bhupendrasambare
 * Date   :03/09/24
 * Time   :2:29 am
 * Project:user service
 **/
package com.service.users.feignClient;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == HttpStatus.NOT_FOUND.value()) {
            return null;
        }
        return defaultErrorDecoder.decode(methodKey, response);
    }
}
