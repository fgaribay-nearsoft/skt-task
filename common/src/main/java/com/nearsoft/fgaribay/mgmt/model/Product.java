package com.nearsoft.fgaribay.mgmt.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

public class Product implements Serializable {
  @NotNull(message = "A product ID is required.")
  private Long id;

  @NotEmpty(message = "A product name is required.")
  private String name;

  @Range(min = 1, max = 1000, message = "The price must be between 1 and 1000.")
  private BigDecimal price;

  private String description;

  public Product() {}

  public Product(long id, String name, String description, BigDecimal price) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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
