package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Customer;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.ShoppingCart;
import com.estore.api.estoreapi.model.Trade;
import com.estore.api.estoreapi.model.User;

/**
 * Implements the functionality for JSON file-based peristance for Customers
 */
@Component
@Primary
public class CustomerFileDAO implements CustomerDAO  {
    
    private static final Logger LOG = Logger.getLogger(CustomerFileDAO.class.getName());
    Map<String,Customer> customers;   // Provides a local cache of the customer objects
    private ObjectMapper objectMapper;  // Provides conversion between Customer objects and JSON text format written to the file
    private String filename;    // Filename to read from and write to

    /**
     * Creates a Customer File Data Access Object
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object
     * @throws IOException when file cannot be accessed or read from
     */
    public CustomerFileDAO(@Value("${users.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    @Override
    public ShoppingCart getShoppingCart(String id) throws IOException {
        synchronized(customers) {
            ShoppingCart cart = customers.get(id).getCart();
            save();
            return cart;
        }
    }

    @Override
    public Product addItem(String id, Product product) throws IOException {
        synchronized(customers) {
            Product returnProduct = customers.get(id).addToCart(product);
            save();
            return returnProduct;
        }
    }

    @Override
    public Product addItem(String id, Product product, int quantity) throws IOException {
        synchronized(customers) {
            Product returnProduct = customers.get(id).addToCart(product, quantity);
            save();
            return returnProduct;
        }
    }

    @Override
    public Product removeItem(String id, Product product) throws IOException {
        synchronized(customers) {
            Product returnProduct = customers.get(id).removeFromCart(product);
            save();
            return returnProduct;
        }
    }

    @Override
    public Product removeItem(String id, Product product, int quantity) throws IOException {
        synchronized(customers) {
            Product returnProduct = customers.get(id).removeFromCart(product, quantity);
            save();
            return returnProduct;
        }
    }

    /**
     * Generates an array of all the customers from the tree map for any
     * customer that contains the text specified by containsText
     * If containsText is null, the array contains all the Customers
     * in the tree map
     *
     * @return  The array of all the customers, may be empty
     */
    private Customer[] getCustomersArray() { // if containsText == null, no filter
        ArrayList<Customer> customerArrayList = new ArrayList<>();
        for ( Customer customer : customers.values() ) {
            customerArrayList.add(customer);
        }
        Customer[] customerArr = new Customer[customerArrayList.size()];
        customerArrayList.toArray(customerArr);
        return customerArr;
    }

    /**
     * Saves the Customers from the map into the file as an array of JSON objects
     *
     * @return true if the Customer were written successfully
     *
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Customer[] customerArray = getCustomersArray();
        objectMapper.writeValue(new File(filename),customerArray);
        return true;
    }

    /**
     * Loads Customers from the JSON file into the map
     * Also sets next id to one more than the greatest id found in the file
     *
     * @return true if the file was read successfully
     *
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        customers = new TreeMap<>();
        //nextId = 0;

        // Deserializes the JSON objects from the file into an array of Customers
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Customer[] customerArray = objectMapper.readValue(new File(filename),Customer[].class);

        // Add each Customer to the tree map and keep track of the greatest id
        for (Customer customer : customerArray) {
            customers.put(customer.getId(),customer);
        }
        // Make the next id one greater than the maximum from the file
        //++nextId;
        return true;
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Customer getCustomer(String name) {
        synchronized(customers) {return customers.get(name);}
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Customer createCustomer(String id) throws IOException {
        synchronized(customers) {
            Customer customer = new Customer(id);
            if (customers.containsKey(id)) {
                return null;
            }
            customers.put(customer.getId(),customer);
            save(); // may throw an IOException
            return customer;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Customer deleteCustomer(String name) throws IOException {
        synchronized(customers) {
            if (customers.containsKey(name)) {
                Customer deleted = customers.get(name);
                customers.remove(name);
                save();
                return deleted;                    
            }  
        return null;
        }
    }

    private Trade[] getTradeArray(String id) throws IOException {
        synchronized(customers) {
            List<Trade> trades = customers.get(id).getTrades();
            Trade[] tradesArray = new Trade[trades.size()];
            return trades.toArray(tradesArray);
        }
    }

    @Override
    public Map<String, Trade[]> getTrades(String id) throws IOException {
        synchronized(customers) {
            Map<String, Trade[]> trades = new HashMap<>();
            Trade[] tradesarray = getTradeArray(id);
            trades.put(id, tradesarray);
            return trades;
        }
    }

    @Override
    public Trade addTrade(String id, Product product) throws IOException {
        synchronized(customers) {
            Trade trade = customers.get(id).addTrade(product);
            save();
            return trade;
        }
    }

    @Override
    public Trade setTradeStatus(String id, Trade trade, String status) throws IOException {
        synchronized(customers) {
            Trade returnTrade = customers.get(id).setTradeStatus(trade, status);
            save();
            return returnTrade;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Product modifyItem(String id, Product product) throws IOException {
        synchronized(customers) {
            Product returnProduct = customers.get(id).modify(product);
            save(); // may throw an IOException
            return returnProduct;
        }
    }

    @Override
    public Map<String, Trade[]> getTrades() throws IOException {
        Map<String, Trade[]> trades = new HashMap<>();
        for (Customer c : this.getCustomersArray()) {
            Trade[] tradesarray = getTradeArray(c.getId());
            trades.put(c.getId(), tradesarray);
        }
        return trades;
    }

}