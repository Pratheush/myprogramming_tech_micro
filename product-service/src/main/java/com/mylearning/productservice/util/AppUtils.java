package com.mylearning.productservice.util;

import com.mylearning.productservice.dto.ProductRequest;
import com.mylearning.productservice.model.Product;
import org.springframework.beans.BeanUtils;

public class AppUtils {

    public static Product dto2modelpro(ProductRequest prodrequest){
        Product product = new Product();
        BeanUtils.copyProperties(prodrequest,product);
        return product;
    }

//    public static Product model2dto(ProductRequest prodrequest){
//        Product product = new Product();
//        BeanUtils.copyProperties(prodrequest,product);
//        return product;
//    }
}
