package com.example.batch.entities;

import javax.annotation.Nonnull;
import javax.persistence.*;

@Entity
public class Product {

    @Id
    @Column(name = "PRODUCT_ID", nullable = false)
    @Nonnull
    private long productID;

    @Column(name = "PRODUCT_NAME", nullable = false)
    @Nonnull
    private String name;

    public Product() {
    }

    public Product(@Nonnull long productID, @Nonnull String name) {
        this.productID = productID;
        this.name = name;
    }

    @Nonnull
    public long getProductID() {
        return productID;
    }

    public void setProductID(@Nonnull long productID) {
        this.productID = productID;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    public void setName(@Nonnull String name) {
        this.name = name;
    }
}
