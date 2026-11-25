package org.example.bdccmundiaspringmvc;

import org.example.bdccmundiaspringmvc.entities.Product;
import org.example.bdccmundiaspringmvc.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.security.autoconfigure.web.servlet.ServletWebSecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class,
        //UserDetailsServiceAutoConfiguration.class,
        //ServletWebSecurityAutoConfiguration.class})
@SpringBootApplication
public class BdccMundiaSpringMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(BdccMundiaSpringMvcApplication.class, args);
    }


        @Bean
    public CommandLineRunner start(ProductRepository productRepository){
            return args -> {
                productRepository.save(Product.builder()
                                .name("Computer ")
                                .price(5400)
                                .quantity(12)

                        .build());
                productRepository.save(Product.builder()
                        .name("Printer ")
                        .price(1200)
                        .quantity(11)

                        .build());
                productRepository.save(Product.builder()
                        .name("Smart Phone ")
                        .price(12000)
                        .quantity(33)

                        .build());
                productRepository.findAll().forEach(p->{
                    System.out.println(p.toString());
                });
            };

        }
}
