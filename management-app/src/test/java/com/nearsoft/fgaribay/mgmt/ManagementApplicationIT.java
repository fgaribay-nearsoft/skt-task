package com.nearsoft.fgaribay.mgmt;

import com.nearsoft.fgaribay.mgmt.exceptions.ProductDataException;
import com.nearsoft.fgaribay.mgmt.exceptions.UnresponsiveBrokerException;
import com.nearsoft.fgaribay.mgmt.exceptions.UnresponsiveMicroserviceException;
import com.nearsoft.fgaribay.mgmt.model.Product;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManagementApplicationIT {

  @Autowired private WebApplicationContext wac;

  @Autowired private BrokerConnector connector;

  private MockMvc mockMvc;

  @Test
  public void contextLoads() {}

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

  @Test
  public void getList() throws Exception {
    this.mockMvc.perform(get("/products/list")).andExpect(status().isOk());
  }

  @Test(expected = UnresponsiveMicroserviceException.class)
  @Category(UnresponsiveMicroserviceTest.class)
  public void exceptionOnProductRetrievalWithMicroserviceDown()
      throws UnresponsiveMicroserviceException, ProductDataException, UnresponsiveBrokerException {
    connector.retrieveProductList();
  }

  @Test(expected = UnresponsiveMicroserviceException.class)
  @Category(UnresponsiveMicroserviceTest.class)
  public void exceptionOnProductCreationWithMicroserviceDown()
      throws UnresponsiveMicroserviceException, UnresponsiveBrokerException, ProductDataException {
    Product product = new Product(1, "Mouse", "A Mouse", new BigDecimal(10));
    connector.createProduct(product);
  }

  @Test(expected = ProductDataException.class)
  public void exceptionOnCreationWithDuplicateProductId()
      throws UnresponsiveMicroserviceException, UnresponsiveBrokerException, ProductDataException {
    Product product = new Product(1, "Mouse", "A Mouse", new BigDecimal(10));
    connector.createProduct(product);
    connector.createProduct(product);
  }

  @Test
  public void testValidForm() throws Exception {
    this.mockMvc
        .perform(
            post("/products/create")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(
                    EntityUtils.toString(
                        new UrlEncodedFormEntity(
                            Arrays.asList(
                                new BasicNameValuePair("id", "111"),
                                new BasicNameValuePair("name", "Mouse"),
                                new BasicNameValuePair("description", "A mouse"),
                                new BasicNameValuePair("price", "10"))))))
        .andExpect(status().isOk());
  }

  @Test
  public void testInvalidId() throws Exception {
    this.mockMvc
        .perform(
            post("/products/create")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(
                    EntityUtils.toString(
                        new UrlEncodedFormEntity(
                            Arrays.asList(
                                new BasicNameValuePair("id", null),
                                new BasicNameValuePair("name", "Mouse"),
                                new BasicNameValuePair("description", "A mouse"),
                                new BasicNameValuePair("price", "10"))))))
        .andExpect(status().isOk());
  }
}
