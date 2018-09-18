package com.nearsoft.fgaribay.mgmt;

import javax.persistence.EntityManager;
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
  public void createProduct(Long id, String name, String description) {
    StoredProcedureQuery createProductQuery = em.createNamedStoredProcedureQuery("createProduct");
    createProductQuery.setParameter("p_id", id);
    createProductQuery.setParameter("p_name", name);
    createProductQuery.setParameter("p_description", description);
    createProductQuery.execute();
  }
}
