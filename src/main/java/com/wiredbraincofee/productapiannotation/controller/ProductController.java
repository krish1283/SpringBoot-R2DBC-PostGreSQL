package com.wiredbraincofee.productapiannotation.controller;

import com.wiredbraincofee.productapiannotation.model.Product;
import com.wiredbraincofee.productapiannotation.model.ProductServerSideEvent;
import com.wiredbraincofee.productapiannotation.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public Flux<Product> getAllProducts(){
        //Note: there is no subscribe in this case and spring will call it internally for you
        return productRepository.findAll();
    }
    @GetMapping("{id}")
    public Mono<ResponseEntity<Product>> getAllProducts(@PathVariable(value = "id") String id){
                return productRepository.findById(id)
                        .map(product->ResponseEntity.ok(product))
                        .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> saveProduct(@RequestBody Product product){
        return productRepository.save(product);
    }
    @PutMapping("{id}")
    public Mono<ResponseEntity<Product>> updateProduct(@PathVariable(value = "id") String id,
                                                       @RequestBody Product product){
        return productRepository.findById(id)
                .flatMap(existingProduct->
                {
                    existingProduct.setName(product.getName());
                    existingProduct.setPrice(product.getPrice());
                    return productRepository.save(existingProduct);
                })
                .map(updateProduct->ResponseEntity.ok(updateProduct))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable(value = "id") String id){
        return productRepository.findById(id)
                .flatMap(existingProduct -> productRepository.delete(existingProduct)
                ).then(Mono.just(ResponseEntity.ok().<Void>build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public Mono<Void> deleteAllProducts(){
        return productRepository.deleteAll();
    }

//    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<ProductServerSideEvent> getEvents(){
//         return Flux.interval(Duration.ofSeconds(1))
//            .map(value->new ProductServerSideEvent(value,"Product Server Event"));
//    }
}
