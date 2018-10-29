package com.iisereb.sensorms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.iisereb.sensorms.*")
public class SensorMSApplication {

    public static void main(String[] args) { SpringApplication.run(SensorMSApplication.class, args); }
}
