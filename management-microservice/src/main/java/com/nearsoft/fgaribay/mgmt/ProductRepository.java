package com.nearsoft.fgaribay.mgmt;

import com.nearsoft.fgaribay.mgmt.model.Product;
import com.nearsoft.fgaribay.mgmt.model.ProductEntity;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ProductRepository extends Repository<ProductEntity, Long> {
  List<Product> getAllProducts();

  void createProduct(Long id, String name, String description);
}
