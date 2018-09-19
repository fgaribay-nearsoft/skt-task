package com.nearsoft.fgaribay.mgmt.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

public class Product implements Serializable {
  @Range(min = 1, max = 65535)
  private long id;

  @NotEmpty private String name;
  @NotEmpty private String description;

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
