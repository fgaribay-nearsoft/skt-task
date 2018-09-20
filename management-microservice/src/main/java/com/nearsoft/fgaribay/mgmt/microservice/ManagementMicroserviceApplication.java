package com.nearsoft.fgaribay.mgmt.microservice;

import com.nearsoft.fgaribay.mgmt.ProductRepository;
import com.nearsoft.fgaribay.mgmt.ProductRepositoryImpl;
import com.nearsoft.fgaribay.mgmt.config.BrokerConfig;
import com.nearsoft.fgaribay.mgmt.model.ProductEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackageClasses = {ProductEntity.class})
@EnableJpaRepositories(basePackageClasses = {ProductRepository.class, ProductRepositoryImpl.class})
@PropertySource("classpath:common.properties")
@Import(BrokerConfig.class)
public class ManagementMicroserviceApplication {

  public static void main(String[] args) {
    SpringApplication.run(ManagementMicroserviceApplication.class, args);
  }
}
