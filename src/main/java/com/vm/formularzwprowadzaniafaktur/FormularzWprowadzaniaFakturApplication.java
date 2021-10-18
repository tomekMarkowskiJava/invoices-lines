package com.vm.formularzwprowadzaniafaktur;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@ServletComponentScan
@EntityScan
public class FormularzWprowadzaniaFakturApplication {

    public static void main(String[] args) {
        SpringApplication.run(FormularzWprowadzaniaFakturApplication.class, args);
    }
}
