package org.csu.app.domain;

import java.io.Serializable;

public class Product implements Serializable {
    private String productId;
    private String catId;
    private String productName;

    public Product() {}

    public Product(String productId, String catId, String productName) {
        this.productId = productId;
        this.catId = catId;
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
