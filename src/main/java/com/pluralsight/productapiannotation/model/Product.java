package com.pluralsight.productapiannotation.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public class Product {
    
    @Id
    private String id;
    private String name;
    private Double price;
    
    public Product(String id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
    
    public String getId() {
        return id;
    }
    
    public Product setId(String id) {
        this.id = id;
        return this;
    }
    
    public String getName() {
        return name;
    }
    
    public Product setName(String name) {
        this.name = name;
        return this;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public Product setPrice(Double price) {
        this.price = price;
        return this;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
               Objects.equals(name, product.name) &&
               Objects.equals(price, product.price);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }
    
    @Override
    public String toString() {
        return "Product{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               ", price=" + price +
               '}';
    }
}
