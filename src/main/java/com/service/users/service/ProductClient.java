/**
 * author @bhupendrasambare
 * Date   :03/09/24
 * Time   :12:42 am
 * Project:user service
 **/
package com.service.users.service;

import com.service.users.dto.response.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "${custom.server-ip}:9002/product/public")
public interface ProductClient {

    @GetMapping("/product/{id}")
    Response getProductById(@PathVariable("id") Long id);
}
