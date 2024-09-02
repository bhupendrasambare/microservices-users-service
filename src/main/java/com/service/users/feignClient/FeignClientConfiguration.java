/**
 * author @bhupendrasambare
 * Date   :03/09/24
 * Time   :2:28 am
 * Project:user service
 **/
package com.service.users.feignClient;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfiguration {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }
}
