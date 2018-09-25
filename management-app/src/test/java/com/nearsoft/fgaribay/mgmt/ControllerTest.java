package com.nearsoft.fgaribay.mgmt;

import com.nearsoft.fgaribay.mgmt.exceptions.ProductDataException;
import com.nearsoft.fgaribay.mgmt.exceptions.UnresponsiveBrokerException;
import com.nearsoft.fgaribay.mgmt.exceptions.UnresponsiveMicroserviceException;
import com.nearsoft.fgaribay.mgmt.model.Product;
import com.nearsoft.fgaribay.mgmt.model.ServiceRequest;
import com.nearsoft.fgaribay.mgmt.model.ServiceResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ControllerTest {

  @Autowired private WebApplicationContext wac;

  @Autowired private BrokerConnector connector;

  @MockBean private RabbitTemplate rabbitTemplate;

  private MockMvc mockMvc;

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

  @Test
  public void contextLoads() {}

  @Test
  public void getProductSubmissionPage() throws Exception {
    this.mockMvc.perform(get("/products/create")).andExpect(status().isOk());
  }

  @Test(expected = ProductDataException.class)
  public void exceptionOnCreationWithDuplicateProductId()
      throws UnresponsiveMicroserviceException, UnresponsiveBrokerException, ProductDataException {
    ServiceResponse response = new ServiceResponse("create", true, "Duplicate product.", null);
    Mockito.when(
            rabbitTemplate.convertSendAndReceive(
                Mockito.anyString(), Mockito.anyString(), Mockito.any(ServiceRequest.class)))
        .thenReturn(response);

    Product product = new Product(110, "Mouse", "A Mouse", new BigDecimal(10));
    connector.createProduct(product);
    connector.createProduct(product);
  }

  @Test
  public void testValidForm() throws Exception {
    Product product = new Product(111, "Mouse", "A Mouse", new BigDecimal(10));
    List<Product> productList = new ArrayList<>();
    productList.add(product);

    ServiceResponse response = new ServiceResponse("create", false, null, productList);
    Mockito.when(
            rabbitTemplate.convertSendAndReceive(
                Mockito.anyString(), Mockito.anyString(), Mockito.any(ServiceRequest.class)))
        .thenReturn(response);

    this.mockMvc
        .perform(
            post("/products/create")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(
                    EntityUtils.toString(
                        new UrlEncodedFormEntity(
                            Arrays.asList(
                                new BasicNameValuePair("id", product.getId().toString()),
                                new BasicNameValuePair("name", product.getName()),
                                new BasicNameValuePair("description", product.getDescription()),
                                new BasicNameValuePair("price", product.getPrice().toString()))))))
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
