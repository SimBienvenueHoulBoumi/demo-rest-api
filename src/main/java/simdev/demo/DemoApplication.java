package simdev.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Point d'entrée principal de l'application Spring Boot.
 */
@SpringBootApplication
public class DemoApplication {

    /**
     * Méthode principale qui démarre l'application Spring Boot.
     *
     * @param args les arguments de la ligne de commande
     */
    public static void main(final String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
