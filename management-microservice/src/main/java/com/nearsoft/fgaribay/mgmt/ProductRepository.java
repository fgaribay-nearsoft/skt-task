package com.nearsoft.fgaribay.mgmt;

import com.nearsoft.fgaribay.mgmt.model.ProductEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository
    extends CrudRepository<ProductEntity, Long>, ProductRepositoryCustom {}
