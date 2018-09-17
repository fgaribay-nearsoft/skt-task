package com.nearsoft.fgaribay.mgmt;

import java.util.List;

public interface ProductRepositoryCustom {
  List getAllProducts();
  void createProduct(Long p_id, String p_name, String p_description);
}
