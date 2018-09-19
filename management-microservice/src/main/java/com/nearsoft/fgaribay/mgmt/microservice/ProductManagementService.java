package com.nearsoft.fgaribay.mgmt.microservice;

import com.nearsoft.fgaribay.mgmt.ProductRepository;
import com.nearsoft.fgaribay.mgmt.model.Product;
import com.nearsoft.fgaribay.mgmt.model.ProductRequest;
import com.nearsoft.fgaribay.mgmt.model.ProductResponse;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ProductManagementService {

  private ProductRepository productRepository;

  @Autowired
  public ProductManagementService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @RabbitListener(queues = "${product.queues.list-name}")
  public ProductResponse listProducts(ProductRequest request) {

    List<Product> products = new ArrayList<>(productRepository.getAllProducts());
    boolean error = false;
    String errorMessage = "";
    UUID uuid = request.getId();

    return new ProductResponse("list", error, errorMessage, products, uuid);
  }

  @RabbitListener(queues = "${product.queues.creation-name}")
  public ProductResponse createProduct(ProductRequest request) {

    boolean error = false;
    String errorMessage = "";
    UUID uuid = request.getId();
    Product product = request.getProducts().get(0);
    productRepository.createProduct(product.getId(), product.getName(), product.getDescription());

    return new ProductResponse("create", error, errorMessage, request.getProducts(), uuid);
  }
}
