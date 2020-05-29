package org.csu.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class Item implements Serializable {

  private static final long serialVersionUID = -2159121673445254631L;

  private String itemId;
  private String productId;
  private String listPrice;
  private String unitCost;
  private int supplier;
  private String status;

  public Item() { }

  public Item(String itemId, String productId, String listPrice, String unitCost, int supplier, String status) {
    this.itemId = itemId;
    this.productId = productId;
    this.listPrice = listPrice;
    this.unitCost = unitCost;
    this.supplier = supplier;
    this.status = status;
  }

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId.trim();
  }

  public int getSupplier() {
    return supplier;
  }

  public void setSupplier(int supplier) {
    this.supplier = supplier;
  }

  public String getListPrice() {
    return listPrice;
  }

  public void setListPrice(String listPrice) {
    this.listPrice = listPrice;
  }

  public String getUnitCost() {
    return unitCost;
  }

  public void setUnitCost(String unitCost) {
    this.unitCost = unitCost;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  private String getProductId() {
    return productId;
  }

  private void setProductId(String productId) {
    this.productId = productId;
  }

  @Override
  public String toString() {
    return getItemId() + ", " + getProductId() + ", $" + getListPrice() + ", " + getSupplier() + ", " + getStatus();
  }

}
