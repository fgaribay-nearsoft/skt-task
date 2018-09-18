package com.nearsoft.fgaribay.mgmt.model;

import javax.persistence.*;

@Entity
@Table(name = "products")
@NamedStoredProcedureQueries({
  @NamedStoredProcedureQuery(
      name = "getAllProducts",
      procedureName = "retrieve_products",
      resultClasses = ProductEntity.class),
  @NamedStoredProcedureQuery(
      name = "createProduct",
      procedureName = "create_product",
      parameters = {
        @StoredProcedureParameter(name = "p_id", mode = ParameterMode.IN, type = Long.class),
        @StoredProcedureParameter(name = "p_name", mode = ParameterMode.IN, type = String.class),
        @StoredProcedureParameter(
            name = "p_description",
            mode = ParameterMode.IN,
            type = String.class)
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

  public ProductEntity() {}

  @Override
  public long getId() {
    return id;
  }

  @Override
  public void setId(long id) {
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
