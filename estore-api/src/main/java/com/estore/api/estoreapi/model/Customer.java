package com.estore.api.estoreapi.model;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import com.estore.api.estoreapi.model.ShoppingCart;

import com.fasterxml.jackson.annotation.JsonProperty;
public class Customer extends User{
    private static final Logger LOG = Logger.getLogger(Product.class.getName());

    @JsonProperty("id") private String id;
    @JsonProperty("cart") private ShoppingCart cart;
    @JsonProperty("balance") private double balance;
    @JsonProperty("trades") private List<Trade> trades;

    /**
     * Create a customer with the given id, and shopping cart
     * @param id The id of the Customer
     *
     */
    public Customer(@JsonProperty("id") String id) {
        super(id);
        this.cart = new ShoppingCart();
        this.trades = new LinkedList<Trade>();
        this.balance = 0;
    }

    /**
     * Adds Products to the Shopping cart. Adds the full quantity
     * @param product The product to be added to the shopping cart
     */
    public Product addToCart(Product product) {
        cart.addToCart(product);
        return product;
    }

     /**
     * Adds Products to the Shopping cart. Adds the specified quantity
     * @param product The product to be added to the shopping cart
     * @param quantity The quantity to add
     */
    public Product addToCart(Product product, int quantity) {
        cart.addToCart(product, quantity);
        return product;
    }

    /**
     * Removes Items to the Shopping cart
     * @param product The product to be removed from the shopping cart
     */
    public Product removeFromCart(Product product) {
        cart.removeFromCart(product);
        return product;
    }

    
    /**
     * Removes Items to the Shopping cart
     * @param product The product to be removed from the shopping cart
     */
    public Product removeFromCart(Product product, int quantity) {
        cart.removeFromCart(product, quantity);
        return product;
    }

    /**
     * Gets Shopping Cart of Customer 
     */
    public ShoppingCart getCart() {
        return cart;
    }

    public List<Trade> getTrades() {
        return trades;
    }

    public Trade addTrade(Product product) {
        Trade trade = new Trade(product);
        this.trades.add(trade);
        return trade;
    }

    public Trade setTradeStatus(Trade trade, String status) {
        if (this.trades.contains(trade)) {
            LOG.info(this.getId());
            Trade t = this.trades.get(this.trades.indexOf(trade));
            Trade returnTrade = t.setStatus(status);
            if (status.equals("ACCEPTED")) {
                LOG.info(trade + " was accepted. Adding balance to " + this.getId() + ": " + trade.getProduct().getPrice() );
                this.setBalance(this.getBalance() + trade.getProduct().getPrice());
            }
            return returnTrade;
        }
        return null;
    }

    public void setBalance(double balance) {
        if (balance > 0) {
            this.balance = balance;
        }
        else this.balance = 0;
    }

    public double getBalance() {
        return this.balance;
    }

    /**
     * Updates an item in the shopping cart
     * @param product
    */
    public Product modify(Product product) {
        return cart.modifyItem(product);
    }
}
