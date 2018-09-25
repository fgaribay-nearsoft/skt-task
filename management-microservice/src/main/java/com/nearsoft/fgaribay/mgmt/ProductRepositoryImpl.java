package com.nearsoft.fgaribay.mgmt;

import com.nearsoft.fgaribay.mgmt.exceptions.ProductDataException;
import com.nearsoft.fgaribay.mgmt.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRepositoryImpl implements ProductRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProductRepositoryImpl.class);

  @Autowired Validator validator;
  @PersistenceContext private EntityManager em;

  @Override
  public List<Product> getAllProducts() throws ProductDataException {
    LOGGER.debug("Calling database stored procedure to retrieve a all the products.");

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
  public void createProduct(@Valid Product product) throws ProductDataException {
    LOGGER.debug("Calling database stored procedure to create a product: {}", product);

    Map<String, String> map = new HashMap<>();
    MapBindingResult errors = new MapBindingResult(map, Product.class.getName());

    validator.validate(product, errors);
    if (errors.getErrorCount() > 0) {
      LOGGER.error(
          "Failed to create product because it violates validation constraints. Use logger level DEBUG for details.");

      StringBuilder sb = new StringBuilder();
      sb.append("The product is not valid. The following errors were found: ");
      for (ObjectError error : errors.getAllErrors()) {
        sb.append(error.getDefaultMessage());
      }

      LOGGER.debug("The errors creating the product are: {}", sb.toString());
      throw new ProductDataException(sb.toString());
    }

    StoredProcedureQuery createProductQuery = em.createNamedStoredProcedureQuery("createProduct");
    createProductQuery.setParameter("p_id", product.getId());
    createProductQuery.setParameter("p_name", product.getName());
    createProductQuery.setParameter("p_description", product.getDescription());
    createProductQuery.setParameter("p_price", product.getPrice());
    Integer errorCode = (Integer) createProductQuery.getOutputParameterValue("o_error_code");
    String errorMessage = (String) createProductQuery.getOutputParameterValue("o_error_msg");

    if (errorCode != 0) {
      LOGGER.error("Failed to create product: {}", errorMessage);
      throw new ProductDataException(
          "Unable to create product. Error from database was \""
              + errorMessage
              + "\" Check database logs for more information.");
    }

    createProductQuery.execute();
  }
}
