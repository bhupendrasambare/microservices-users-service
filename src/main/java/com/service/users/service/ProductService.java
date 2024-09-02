/**
 * author @bhupendrasambare
 * Date   :03/09/24
 * Time   :12:47 am
 * Project:user service
 **/
package com.service.users.service;

import com.service.users.dto.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements ProductClient{

    @Autowired
    private ProductClient productClient;

    public Response getProductById(Long productId) {
        Response productResponse = productClient.getProductById(productId);
        if (productResponse == null) {
            return null;
        }
        return productResponse;
    }
}
