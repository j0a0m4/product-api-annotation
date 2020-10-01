package com.pluralsight.productapiannotation.controller;

import com.pluralsight.productapiannotation.model.Product;
import com.pluralsight.productapiannotation.model.ProductEvent;
import com.pluralsight.productapiannotation.repository.ProductRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/products")
public class ProductController {
    
    private final ProductRepository repository;
    
    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }
    
    @GetMapping
    public Flux<Product> getAllProducts() {
        return repository.findAll();
    }
    
    @GetMapping("{id}")
    public Mono<ResponseEntity<Product>> getProduct(@PathVariable String id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @ResponseStatus(CREATED)
    public Mono<Product> saveProduct(@RequestBody Product product) {
        return repository.save(product);
    }
    
    @PutMapping("{id}")
    public Mono<ResponseEntity<Product>> updateProduct(@PathVariable(value = "id") String id,
                                                       @RequestBody Product product) {
        return repository.findById(id)
                .map(existingProduct -> existingProduct
                        .setName(product.getName())
                        .setPrice(product.getPrice()))
                .flatMap(repository::save)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
    @PatchMapping("{id}")
    public Mono<ResponseEntity<Product>> patchProduct(@PathVariable(value = "id") String id,
                                                      @RequestBody Product product) {
        return repository.findById(id)
                .map(existingProduct -> {
                    Mono.justOrEmpty(product.getName())
                            .subscribe(existingProduct::setName);
                    Mono.justOrEmpty(product.getPrice())
                            .subscribe(existingProduct::setPrice);
                    return existingProduct;
                })
                .flatMap(repository::save)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable(value = "id") String id) {
        return repository.findById(id)
                .flatMap(existingProduct -> repository
                        .delete(existingProduct)
                        .then(Mono.just(ResponseEntity.ok().<Void>build())))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductEvent> getProductEvents() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(val -> new ProductEvent(val, "Product Event"));
    }
}
