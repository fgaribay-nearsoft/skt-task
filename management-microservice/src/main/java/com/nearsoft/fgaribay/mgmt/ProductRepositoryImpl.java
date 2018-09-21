package com.nearsoft.fgaribay.mgmt;

import com.nearsoft.fgaribay.mgmt.exceptions.ProductDataException;
import com.nearsoft.fgaribay.mgmt.model.Product;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {
  @PersistenceContext private EntityManager em;

  @Override
  public List<Product> getAllProducts() {
    StoredProcedureQuery retrieveProductsQuery =
        em.createNamedStoredProcedureQuery("getAllProducts");
    Integer errorCode = (Integer) retrieveProductsQuery.getOutputParameterValue("o_error_code");
    String errorMessage = (String) retrieveProductsQuery.getOutputParameterValue("o_error_msg");
    if (errorCode != 0) {
      throw new ProductDataException(
          "Unable to retrieve products. Error from database was \""
              + errorMessage
              + "\" Check database logs for more information.");
    }
    return retrieveProductsQuery.getResultList();
  }

  @Override
  public void createProduct(Long id, String name, String description) {
    StoredProcedureQuery createProductQuery = em.createNamedStoredProcedureQuery("createProduct");
    createProductQuery.setParameter("p_id", id);
    createProductQuery.setParameter("p_name", name);
    createProductQuery.setParameter("p_description", description);
    Integer errorCode = (Integer) createProductQuery.getOutputParameterValue("o_error_code");
    String errorMessage = (String) createProductQuery.getOutputParameterValue("o_error_msg");
    if (errorCode != 0) {
      throw new ProductDataException(
          "Unable to create product. Error from database was \""
              + errorMessage
              + "\" Check database logs for more information.");
    }
    createProductQuery.execute();
  }
}
