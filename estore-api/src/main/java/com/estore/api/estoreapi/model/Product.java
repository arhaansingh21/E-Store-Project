package com.estore.api.estoreapi.model;
import java.util.Objects;
import java.util.logging.Logger;

import org.springframework.context.annotation.Profile;

import com.fasterxml.jackson.annotation.JsonProperty;
public class Product {
    private static final Logger LOG = Logger.getLogger(Product.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Product [name=%s, price=%f, quantity=%d]";
    @JsonProperty("name") private String name;
    @JsonProperty("price") private double price;
    @JsonProperty("quantity") private int quantity;
    
    /**
     * Create a product with the given name, price and quantity
     * @param name The name of the product
     * @param price The price of the product. Cannot be < 0
     * @param quantity The quantity of the product. Cannot be < 0
     * 
     */
    public Product(@JsonProperty("name") String name, @JsonProperty("price") double price, @JsonProperty("quantity") int quantity) {
        this.name = name;
        
        if (price < 0) {
            this.price = 0;
        } 
        else {this.price=price;}

        if (quantity < 0) {
            this.quantity = 0;
        } 
        else {this.quantity=quantity;}
    }

    /**
     * Gets the name of the product
     * @return The name of the product
     */
    public String getName(){return name;}

    /**
     * Sets the name of the product
     * @param name The name of the product
     */
    public void setName(String name){this.name = name;}

    /**
     * Gets the price of the product
     * @return the price of the product
     */
    public double getPrice(){return price;}

    /**
     * Sets the price of the product
     * @param price The price of the product, must be >= 0
     */
    public void setPrice(double price) {
        if (price < 0) {
            this.price = 0;
        } 
        else {this.price=price;}
    }

    /**
     * Gets the quantity of the product
     * @return the quantity of the product
     */
    public int getQuantity(){return quantity;}

    /**
     * Sets the quantity of the product
     * @param quantity The quantity of the product, must be >= 0
     */
    public void setQuantity(int quantity) {
        if (quantity < 0) {
            this.quantity = 0;
        } 
        else {this.quantity=quantity;}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, name, price, quantity);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Product) {
            Product productObj = (Product) obj;
            if ((productObj.getName().equalsIgnoreCase(this.name)) && (Double.compare(productObj.getPrice(), this.price) == 0)) {
                return true;
            } 
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }
}
