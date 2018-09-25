package com.nearsoft.fgaribay.mgmt;

import com.nearsoft.fgaribay.mgmt.exceptions.UnresponsiveMicroserviceException;
import com.nearsoft.fgaribay.mgmt.model.Product;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManagementApplicationBrokenCommunicationIT {

  @Autowired private WebApplicationContext wac;

  @Autowired private BrokerConnector connector;

  private MockMvc mockMvc;

  @Test
  public void contextLoads() {}

  @Test(expected = UnresponsiveMicroserviceException.class)
  @Category(UnresponsiveMicroserviceTest.class)
  public void exceptionOnProductRetrievalWithMicroserviceDown() {
    connector.retrieveProductList();
  }

  @Test(expected = UnresponsiveMicroserviceException.class)
  @Category(UnresponsiveMicroserviceTest.class)
  public void exceptionOnProductCreationWithMicroserviceDown() {
    Product product = new Product(1, "Mouse", "A Mouse", new BigDecimal(10));
    connector.createProduct(product);
  }
}
