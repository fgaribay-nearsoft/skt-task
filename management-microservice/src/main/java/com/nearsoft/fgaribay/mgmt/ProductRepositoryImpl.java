package com.nearsoft.fgaribay.mgmt;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepositoryCustom {
  @PersistenceContext private EntityManager em;

  @Override
  public List getAllProducts() {
    StoredProcedureQuery retrieveProductsQuery =
        em.createNamedStoredProcedureQuery("getAllProducts");
    return retrieveProductsQuery.getResultList();
  }

  @Override
  public void createProduct(Long p_id, String p_name, String p_description) {
    StoredProcedureQuery createProductQuery =
            em.createNamedStoredProcedureQuery("createProduct");
    createProductQuery.setParameter("p_id", p_id);
    createProductQuery.setParameter("p_name", p_name);
    createProductQuery.setParameter("p_description", p_description);
    createProductQuery.execute();
  }
}
