package com.nearsoft.fgaribay.mgmt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@NamedStoredProcedureQueries({
  @NamedStoredProcedureQuery(
      name = "getAllProducts",
      procedureName = "retrieve_products",
      parameters = {
        @StoredProcedureParameter(name = "o_error_code", type = Integer.class, mode = ParameterMode.OUT),
        @StoredProcedureParameter(name = "o_error_msg", type = String.class, mode = ParameterMode.OUT)
      },
      resultClasses = ProductEntity.class),
  @NamedStoredProcedureQuery(
      name = "createProduct",
      procedureName = "create_product",
      parameters = {
        @StoredProcedureParameter(name = "p_id", type = Long.class),
        @StoredProcedureParameter(name = "p_name", type = String.class),
        @StoredProcedureParameter(name = "p_description", type = String.class),
        @StoredProcedureParameter(name = "p_price", type = BigDecimal.class),
        @StoredProcedureParameter(name = "o_error_code", type = Integer.class, mode = ParameterMode.OUT),
        @StoredProcedureParameter(name = "o_error_msg", type = String.class, mode = ParameterMode.OUT)
      })
})
public class ProductEntity extends Product {

  @Id
  @Column(name = "id")
  private long id;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "price")
  private BigDecimal price;

  public ProductEntity() {}

  @Override
  public BigDecimal getPrice() {
    return price;
  }

  @Override
  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public void setDescription(String description) {
    this.description = description;
  }
}
