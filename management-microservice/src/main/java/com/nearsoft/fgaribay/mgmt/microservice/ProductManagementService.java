package com.nearsoft.fgaribay.mgmt.microservice;

import com.nearsoft.fgaribay.mgmt.ProductRepository;
import com.nearsoft.fgaribay.mgmt.exceptions.ProductDataException;
import com.nearsoft.fgaribay.mgmt.model.Product;
import com.nearsoft.fgaribay.mgmt.model.ServiceRequest;
import com.nearsoft.fgaribay.mgmt.model.ServiceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductManagementService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProductManagementService.class);

  private ProductRepository productRepository;

  public ProductManagementService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @RabbitListener(queues = "${product.queues.list-name}")
  public ServiceResponse listProducts(ServiceRequest request) {
    LOGGER.debug("Received a request to get a list of products: {}", request);

    boolean error = false;
    List<Product> products = null;
    String errorMessage = "";
    try {
      products = productRepository.getAllProducts();
    } catch (ProductDataException e) {
      LOGGER.error("Error while trying to get a list of products.", e);
      error = true;
      errorMessage = e.getMessage();
    }

    return new ServiceResponse<>("list", error, errorMessage, products);
  }

  @RabbitListener(queues = "${product.queues.creation-name}")
  public ServiceResponse createProduct(ServiceRequest<Product> request) {
    LOGGER.debug("Received a request to create product: {}", request);

    boolean error = false;
    Product product = request.getData();
    String errorMessage = "";
    try {
      productRepository.createProduct(product);
    } catch (ProductDataException e) {
      LOGGER.error("Error while trying to create a product.", e);
      error = true;
      errorMessage = e.getMessage();
    }

    return new ServiceResponse<>("create", error, errorMessage, request.getData());
  }
}
