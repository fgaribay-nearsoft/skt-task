package com.nearsoft.fgaribay.mgmt;

import com.nearsoft.fgaribay.mgmt.exceptions.ProductDataException;
import com.nearsoft.fgaribay.mgmt.model.Product;
import com.nearsoft.fgaribay.mgmt.model.ProductEntity;
import org.springframework.data.repository.Repository;

import javax.validation.Valid;
import java.util.List;

public interface ProductRepository extends Repository<ProductEntity, Long> {
  List<Product> getAllProducts() throws ProductDataException;

  void createProduct(@Valid Product product) throws ProductDataException;
}
