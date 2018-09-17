package com.nearsoft.fgaribay.mgmt;

import com.nearsoft.fgaribay.mgmt.model.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManagementApplicationTests {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private BrokerConnector connector;

    private MockMvc mockMvc;

    @Test
    public void contextLoads() {

    }

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void getList() throws Exception {
        this.mockMvc.perform(get("/products/list")).andExpect(status().isOk());
    }

    @Test
    public void getProductSubmissionPage() throws Exception {
        this.mockMvc.perform(get("/products/create")).andExpect(status().isOk());
    }

    @Test
    public void testProductRetrieval() {
        ArrayList<Product> products = connector.retrieveProductList();
        Assert.assertEquals(products.get(0).getName(), "Keyboard");
    }

    @Test
    public void testProductCreation() {

    }

}
