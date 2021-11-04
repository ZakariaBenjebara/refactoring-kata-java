package com.sipios.refactoring;

import com.sipios.refactoring.domain.order.ProductRebate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.function.Supplier;

@SpringBootApplication
public class RefactoringApplication {

    public static void main(String[] args) {
        SpringApplication.run(RefactoringApplication.class, args);
    }

    @Configuration
    class AppConfig {

        @Bean
        Supplier<LocalDate> currentDateSupplier() {
            return () -> LocalDate.ofInstant(Instant.now(),
                ZoneId.of("Europe/Paris"));
        }

        List<ProductRebate> rebates() {
            return List.of(
                new ProductRebate("JACKET", 0.9),
                new ProductRebate("DRESS", 0.8)
            );
        }
    }

}
