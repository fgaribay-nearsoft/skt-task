package com.nearsoft.fgaribay.mgmt.microservice;

import com.nearsoft.fgaribay.mgmt.model.Product;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
@Category(DataTest.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StoredProcedureTest {

  @PersistenceContext private EntityManager em;

  @Test
  public void contextLoads() {}

  @Test
  public void testListProducts() {
    StoredProcedureQuery query = em.createNamedStoredProcedureQuery("getAllProducts");
    int errorCode = (int) query.getOutputParameterValue("o_error_code");
    List<Product> products = query.getResultList();
    Assert.assertEquals(0L, errorCode);
    Assert.assertEquals("Keyboard", products.get(0).getName());
  }

  @Test
  public void testCreateProduct() {
    StoredProcedureQuery query = em.createNamedStoredProcedureQuery("createProduct");
    query.setParameter("p_id", 12L);
    query.setParameter("p_name", "Mouse");
    query.setParameter("p_description", "A Mouse");
    query.setParameter("p_price", new BigDecimal(10));
    query.execute();
    int errorCode = (int) query.getOutputParameterValue("o_error_code");
    Assert.assertEquals(0L, errorCode);
  }

  @Test
  public void testCreateDuplicateProduct() {
    StoredProcedureQuery query = em.createNamedStoredProcedureQuery("createProduct");
    query.setParameter("p_id", 13L);
    query.setParameter("p_name", "Mouse");
    query.setParameter("p_description", "A Mouse");
    query.setParameter("p_price", new BigDecimal(10));
    query.execute();

    StoredProcedureQuery query2 = em.createNamedStoredProcedureQuery("createProduct");
    query2.setParameter("p_id", 13L);
    query2.setParameter("p_name", "Mouse");
    query2.setParameter("p_description", "A Mouse");
    query2.setParameter("p_price", new BigDecimal(10));
    query2.execute();

    int errorCode = (int) query2.getOutputParameterValue("o_error_code");
    Assert.assertNotEquals(0L, errorCode);
  }
}
