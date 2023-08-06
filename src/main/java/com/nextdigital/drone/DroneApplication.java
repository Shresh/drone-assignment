package com.nextdigital.drone;

import com.nextdigital.drone.util.InitialCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@EntityScan(basePackages = "com.nextdigital.drone.model")
public class DroneApplication extends SpringBootServletInitializer implements ApplicationRunner {

    @Autowired
    private InitialCreator initialCreator;

    public static void main(String[] args) {
        SpringApplication.run(DroneApplication.class, args);
    }


    public void run(ApplicationArguments args) {
        initialCreator.initialSetup();
    }

}
