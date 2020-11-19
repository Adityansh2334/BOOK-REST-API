package org.library.com;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class ComApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ComApplication.class, args);
        System.out.println("Hello Adi");
    }


}
