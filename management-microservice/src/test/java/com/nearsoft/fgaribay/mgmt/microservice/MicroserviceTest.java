package com.nearsoft.fgaribay.mgmt.microservice;

import com.nearsoft.fgaribay.mgmt.ProductRepository;
import com.nearsoft.fgaribay.mgmt.exceptions.ProductDataException;
import com.nearsoft.fgaribay.mgmt.model.Product;
import com.nearsoft.fgaribay.mgmt.model.ServiceRequest;
import com.nearsoft.fgaribay.mgmt.model.ServiceResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MicroserviceTest {

  @MockBean
  @Qualifier("productRepository")
  ProductRepository productRepository;

  @Autowired ProductManagementService service;

  @Before
  public void setup() {}

  @Test
  public void contextLoads() {}

  @Test
  public void testListProductsSuccessful() {
    List<Product> products = new ArrayList<>();
    products.add(new Product(10L, "Mouse", "A Mouse", new BigDecimal(10)));
    Mockito.when(productRepository.getAllProducts()).thenReturn(products);

    ServiceRequest<List<Product>> request = new ServiceRequest<>("list", null);
    ServiceResponse<List<Product>> response = service.listProducts(request);
    List<Product> receivedProducts = response.getData();
    Assert.assertEquals("Mouse", receivedProducts.get(0).getName());
  }

  @Test
  public void testListProductsFailed() {
    Mockito.when(productRepository.getAllProducts()).thenThrow(ProductDataException.class);

    ServiceRequest request = new ServiceRequest<>("list", null);
    ServiceResponse response = service.listProducts(request);
    Assert.assertTrue(response.isError());
  }

  @Test
  public void testCreateProductSuccessful() {
    Product product = new Product(10L, "Mouse", "A Mouse", new BigDecimal(10));
    Mockito.doNothing().when(productRepository);

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
    Mockito.doThrow(ProductDataException.class)
        .when(productRepository)
        .createProduct(Mockito.any(Product.class));

    ServiceRequest request = new ServiceRequest<>("create", product);
    ServiceResponse response = service.createProduct(request);
    Assert.assertEquals(request.getId(), response.getId());
    Assert.assertTrue(response.isError());
  }
}
