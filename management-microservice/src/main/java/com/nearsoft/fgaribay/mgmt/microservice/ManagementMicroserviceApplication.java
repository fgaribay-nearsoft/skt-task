package com.nearsoft.fgaribay.mgmt.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.nearsoft.fgaribay.mgmt.model"})
@EnableJpaRepositories(basePackages = {"com.nearsoft.fgaribay.mgmt"})
public class ManagementMicroserviceApplication {

  public static void main(String[] args) {
    SpringApplication.run(ManagementMicroserviceApplication.class, args);
  }
}
