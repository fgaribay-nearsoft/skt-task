package com.nearsoft.fgaribay.mgmt.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "products")
@Data
@NamedStoredProcedureQueries({
  @NamedStoredProcedureQuery(
      name = "getAllProducts",
      procedureName = "retrieve_products",
      resultClasses = Product.class),
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
public class Product implements Serializable {
  @Id
  @Column(name = "id")
  private long id;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  public Product() {}

  public Product(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public Product(long id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
