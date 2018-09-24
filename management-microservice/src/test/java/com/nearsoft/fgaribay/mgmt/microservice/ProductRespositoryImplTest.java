package com.nearsoft.fgaribay.mgmt.microservice;

import com.nearsoft.fgaribay.mgmt.ProductRepository;
import com.nearsoft.fgaribay.mgmt.exceptions.ProductDataException;
import com.nearsoft.fgaribay.mgmt.model.Product;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
@Category(DataTest.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRespositoryImplTest {

  @Autowired ProductRepository productRepository;

  @Test
  public void contextLoads() {}

  @Test
  public void testCreateValidProduct() {
    Product product = new Product(10L, "Mouse", "A mouse", new BigDecimal(10));
    productRepository.createProduct(product);
  }

  @Test(expected = ProductDataException.class)
  public void testCreateProductDuplicateId() {
    Product product = new Product(11L, "Mouse", "A mouse", new BigDecimal(10));
    productRepository.createProduct(product);
    productRepository.createProduct(product);
  }

  @Test(expected = ProductDataException.class)
  public void testCreateProductPriceTooHigh() {
    Product product = new Product(9L, "Mouse", "A mouse", new BigDecimal(10000));
    productRepository.createProduct(product);
  }
}
