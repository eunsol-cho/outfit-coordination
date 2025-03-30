package org.esjo.outfitcoordination;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OutfitCoordinationApplication {

    public static void main(String[] args) {
        SpringApplication.run(OutfitCoordinationApplication.class, args);
    }

}
