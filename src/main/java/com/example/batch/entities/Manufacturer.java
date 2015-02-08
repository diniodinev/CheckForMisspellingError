package com.example.batch.entities;

import org.hibernate.annotations.BatchSize;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.persistence.*;
import java.util.List;

@Entity
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MANUFACTURER_ID", nullable = false)
    @Nonnull
    private long manufacturerId;

    @Column(name = "NAME", nullable = false)
    @Nonnull
    private String name;

    @Column(name = "PRODUCTS", nullable = true)
    @CheckForNull
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "PRODUCTS")
//    @BatchSize(size = 3)
    public List<Product> products;

    public Manufacturer() {
    }

    public Manufacturer(@Nonnull String name) {
        this.name = name;
    }

    @Nonnull
    public long getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(@Nonnull long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    public void setName(@Nonnull String name) {
        this.name = name;
    }
}
