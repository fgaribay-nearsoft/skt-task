package com.nearsoft.fgaribay.mgmt;

import java.util.List;

public interface ProductRepositoryCustom {
  List getAllProducts();

  void createProduct(Long id, String name, String description);
}
