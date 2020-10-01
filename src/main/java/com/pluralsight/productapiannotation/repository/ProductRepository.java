package com.pluralsight.productapiannotation.repository;

import com.pluralsight.productapiannotation.model.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductRepository
        extends ReactiveMongoRepository<Product, String> {
}
