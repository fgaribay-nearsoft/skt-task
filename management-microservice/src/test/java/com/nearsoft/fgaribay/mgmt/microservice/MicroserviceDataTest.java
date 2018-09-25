package com.nearsoft.fgaribay.mgmt.microservice;

import com.nearsoft.fgaribay.mgmt.model.Product;
import com.nearsoft.fgaribay.mgmt.model.ServiceRequest;
import com.nearsoft.fgaribay.mgmt.model.ServiceResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
@Category(DataTest.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MicroserviceDataTest {

  @Autowired ProductManagementService service;

  @Before
  public void setup() {}

  @Test
  public void contextLoads() {}

  @Test
  public void testListProductsSuccessful() {
    ServiceRequest<List<Product>> request = new ServiceRequest<>("list", null);
    ServiceResponse<List<Product>> response = service.listProducts(request);
    List<Product> receivedProducts = response.getData();
    Assert.assertEquals("Keyboard", receivedProducts.get(0).getName());
  }

  @Test
  public void testCreateProductSuccessful() {
    Product product = new Product(10L, "Mouse", "A Mouse", new BigDecimal(10));

    ServiceRequest request = new ServiceRequest<>("create", product);
    ServiceResponse<Product> response = service.createProduct(request);
    Product receivedProduct = response.getData();
    Assert.assertEquals("Mouse", receivedProduct.getName());
    Assert.assertEquals(request.getId(), response.getId());
    Assert.assertFalse(response.getErrorMessage(), response.isError());
  }

  @Test
  public void testCreateProductFailed() {
    Product product = new Product(10L, "Mouse", "A Mouse", new BigDecimal(100000));

    ServiceRequest request = new ServiceRequest<>("create", product);
    ServiceResponse response = service.createProduct(request);
    Assert.assertEquals(request.getId(), response.getId());
    Assert.assertTrue(response.isError());
  }

  @Test
  public void testInvalidOperation() {
    ServiceRequest request = new ServiceRequest<>("invalid-operation", null);
    ServiceResponse response = service.listProducts(request);
  }
}
