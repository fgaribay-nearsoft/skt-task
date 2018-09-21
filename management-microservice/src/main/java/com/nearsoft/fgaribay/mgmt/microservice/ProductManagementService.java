package com.nearsoft.fgaribay.mgmt.microservice;

import com.nearsoft.fgaribay.mgmt.ProductRepository;
import com.nearsoft.fgaribay.mgmt.exceptions.ProductDataException;
import com.nearsoft.fgaribay.mgmt.model.Product;
import com.nearsoft.fgaribay.mgmt.model.ProductRequest;
import com.nearsoft.fgaribay.mgmt.model.ProductResponse;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ProductManagementService {

  private ProductRepository productRepository;

  public ProductManagementService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @RabbitListener(queues = "${product.queues.list-name}")
  public ProductResponse listProducts(ProductRequest request) {

    boolean error = false;
    List<Product> products = null;
    String errorMessage = "";
    UUID uuid = request.getId();
    try {
      products = productRepository.getAllProducts();
    } catch (ProductDataException e) {
      error = true;
      errorMessage = e.getMessage();
    }

    return new ProductResponse("list", error, errorMessage, products, uuid);
  }

  @RabbitListener(queues = "${product.queues.creation-name}")
  public ProductResponse createProduct(ProductRequest request) {

    boolean error = false;
    Product product = request.getProducts().get(0);
    String errorMessage = "";
    UUID uuid = request.getId();
    try {
      productRepository.createProduct(product.getId(), product.getName(), product.getDescription());
    } catch (ProductDataException e) {
      error = true;
      errorMessage = e.getMessage();
    }

    return new ProductResponse("create", error, errorMessage, request.getProducts(), uuid);
  }
}
