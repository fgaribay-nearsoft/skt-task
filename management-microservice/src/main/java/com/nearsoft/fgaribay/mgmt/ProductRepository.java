package com.nearsoft.fgaribay.mgmt;

import com.nearsoft.fgaribay.mgmt.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long>, ProductRepositoryCustom {}
