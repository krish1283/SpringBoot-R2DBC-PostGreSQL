package com.wiredbraincofee.productapiannotation;

import com.wiredbraincofee.productapiannotation.model.Product;
import com.wiredbraincofee.productapiannotation.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import reactor.core.publisher.Flux;

@SpringBootApplication
@EnableR2dbcRepositories
public class ProductReDBCApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductReDBCApplication.class, args);
    }

    @Bean
    CommandLineRunner init(ProductRepository repository){
        return args -> {
            Flux<Product> productFlux =
                    Flux.just(
                            new Product("1","Big Latte",2.99),
                            new Product("2","Capuchino",2.49),
                            new Product("3","Tea",1.49)
                    ).flatMap(p->repository.save(p.setAsNew()));

            productFlux
                  .thenMany(repository.findAll())
            .subscribe(System.out::println);
        };
    }

}
