package com.estore.api.estoreapi.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.io.Console;
import java.util.LinkedList;

public class ShoppingCart {
    @JsonProperty("products") private List<Product> products;
    @JsonProperty("price") private double price;
    @JsonProperty("itemCount") private int itemCount;

    /**
     * Create a ShoppingCart with the empty list of products.
     */
    public ShoppingCart() {
        this.products = new LinkedList<Product>();
        this.price = 0;
        this.itemCount = 0;
    }

    /**
     * Adds Products to the Shopping cart
     * Adds the max amount
     * @param item Product to be added to the shopping cart
     */
    public void addToCart(Product item) {
        if (products.contains(item)) {
            int index = products.indexOf(item);
            products.get(index).setQuantity(products.get(index).getQuantity() + item.getQuantity());
        } else {
            products.add(new Product(item.getName(), item.getPrice(), item.getQuantity()));
        }
        price += item.getPrice() * item.getQuantity();
        itemCount += item.getQuantity();
    }

     /**
     * Adds Products to the Shopping cart
     * Since item is the item in the inventory, we specify how many of it we want to add
     * @param item The product to be added to the shopping cart
     * @param quantity How many items to add
     */
    public void addToCart(Product item, int quantity) {
        if (products.contains(item)) {
            int index = products.indexOf(item);
            products.get(index).setQuantity(products.get(index).getQuantity() + quantity);
        } else {
            products.add(new Product(item.getName(), item.getPrice(), quantity));
        }
        price += item.getPrice() * quantity;
        itemCount += quantity;
    }

    /**
     * Removes Items to the Shopping cart
     * Removes max quantity of item
     * @param item The product to be removed from  the shopping cart
     */
    public void removeFromCart(Product item) {
        if (products.contains(item)) {
            price -= item.getPrice() * item.getQuantity();
            itemCount -= item.getQuantity();
            if (itemCount <= 0) itemCount = 0;
            if (price <= 0) price = 0;
            products.remove(item);
        }
        //else throw new IllegalArgumentException("Product was not in cart.");
    }

    /**
     * Removes Items to the Shopping cart
     * Removes quantity of item
     * @param item The product to be removed from  the shopping cart
     */
    public void removeFromCart(Product item, int quantity) {
        if (products.contains(item)) {
            price -= item.getPrice() * quantity;
            itemCount -= quantity;

            if (itemCount <= 0) itemCount = 0;
            if (price <= 0) price = 0;
            
            int index = products.indexOf(item);
            products.get(index).setQuantity(products.get(index).getQuantity() - quantity);

            if (products.get(products.indexOf(item)).getQuantity() <= 0 ) {
                products.remove(item);
            }
        }
        //else throw new IllegalArgumentException("Product was not in cart.");
        
    }

    /**
     * Gets the total price of the products in the shoppingCart
     * @return the price of the shoppingCart
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets all the products in the shoppingCart
     * @return the products of the shoppingCart
     */
    public List<Product> getProducts() {
        if (products.isEmpty()) {
            this.itemCount = 0;
            this.price = 0;
        }
        return products;
    }

    /**
     * Gets total number of products in the shoppingCart
     * @return the itemCount of the shoppingCart
     */
    public int getItemCount() {
        return itemCount;
    }

    /**
     * Updates an item
     */
    public Product modifyItem(Product product) {
        if (products.contains(product)) {
            int index = products.indexOf(product);
            products.get(index).setQuantity(product.getQuantity());

            itemCount = product.getQuantity();
            price = product.getPrice() * product.getQuantity();
            if (itemCount <= 0) itemCount = 0;
            if (price <= 0) price = 0;

            return product;
        }
        return null;
    }
    
}
