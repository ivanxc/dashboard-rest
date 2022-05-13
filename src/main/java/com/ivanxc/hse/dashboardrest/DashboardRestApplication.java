package com.ivanxc.hse.dashboardrest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Dashboard API", version = "1.0.0", description = "Dashboard web service"))
public class DashboardRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(DashboardRestApplication.class, args);
    }

}
