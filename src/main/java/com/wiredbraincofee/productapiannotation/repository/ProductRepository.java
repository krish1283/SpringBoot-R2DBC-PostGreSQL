package com.wiredbraincofee.productapiannotation.repository;

import com.wiredbraincofee.productapiannotation.model.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
public interface ProductRepository extends ReactiveCrudRepository<Product,String> {

}
