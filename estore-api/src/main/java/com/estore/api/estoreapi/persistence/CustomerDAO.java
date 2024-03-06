package com.estore.api.estoreapi.persistence;

import com.estore.api.estoreapi.model.Customer;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.ShoppingCart;
import com.estore.api.estoreapi.model.Trade;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CustomerDAO {

    /**
     * gets a {@linkplain ShoppingCart shoppingCart} of a {@linkplain Customer customer}
     * 
     * @param id {@linkplain String id} of the customer to retreive the {@linkplain ShoppingCart shoppingCart} from
     * @return ShoppingCart {@link ShoppingCart shoppingCart} that was retrieved if successful, null otherwise
     * @throws IOException if an issue with underlying storage
     */
    ShoppingCart getShoppingCart(String id) throws IOException;

    /**
     * Adds a {@linkplain Product product} to the {@linkplain ShoppingCart shoppingCart} of a {@linkplain Customer customer} with id
     * 
     * @param id The id of the customer to add to
     * @param product The product to add
     * 
     * @return {@link Product product} that was added if successful
     * <br>
     * null otherwise
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Product addItem(String id, Product product) throws IOException;

    /**
     * Adds a {@linkplain Product product} to the {@linkplain ShoppingCart shoppingCart} of a {@linkplain Customer customer} with id
     * 
     * @param id The id of the customer to add to
     * @param product The product to add
     * @param quantity Quantity to add
     * 
     * @return {@link Product product} that was added if successful
     * <br>
     * null otherwise
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Product addItem(String id, Product product, int quantity) throws IOException;

    /**
     * Removes quantity a {@linkplain Product product} from the {@linkplain ShoppingCart shoppingCart} of a {@linkplain Customer customer} with id
     * <br>
     * Removes quantity of product if multiple are in the cart
     * @param id The id of the customer to remove from
     * @param product The product to removed
     * @param quantity Amount to remove
     * 
     * @return {@link Product product} that was removed if successful
     * <br>
     * null otherwise
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Product removeItem(String id, Product product, int quantity) throws IOException;

    /**
     * Removes a {@linkplain Product product} from the {@linkplain ShoppingCart shoppingCart} of a {@linkplain Customer customer} with id
     * <br>
     * Removes a single quantity of product if multiple are in the cart
     * @param id The id of the customer to remove from
     * @param product The product to remove
     * 
     * @return {@link Product product} that was removed if successful
     * <br>
     * null otherwise
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Product removeItem(String id, Product product) throws IOException;
    
    /**
     * Creates and saves a {@linkplain Customer customer}
     * 
     * @param customer {@linkplain Customer customer} object to be created and saved
     * @return customer {@link Customer customer} customer that was created if successful, null otherwise
     * @throws IOException if an issue with underlying storage
     */
    Customer createCustomer(String id) throws IOException;

    /**
     * Deletes a {@linkplain Customer customer} with the given id
     * 
     * @param id The id of the {@link Customer customer}
     * 
     * @return true if the {@link Customer customer} was deleted
     * <br>
     * false if customer with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Customer deleteCustomer(String id) throws IOException;

    /**
     * Retrieves a {@linkplain Customer customer} with the given id
     * 
     * @param if The name of the {@link Customer customer} to get
     * 
     * @return a {@link Customer customer} object with the matching id
     * <br>
     * null if no {@link Customer customer} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Customer getCustomer(String id) throws IOException;

    Map<String, Trade[]> getTrades(String id) throws IOException;

    Map<String, Trade[]> getTrades() throws IOException;

    Trade addTrade(String id, Product product) throws IOException;

    Trade setTradeStatus(String id, Trade trade, String status) throws IOException;
     /**
     * Updates a {@linkplain Product product} in the {@linkplain ShoppingCart shoppingCart} of a {@linkplain Customer customer} with id
     * <br>
     * @param id The id of the customer
     * @param product The product to update
     * 
     * @return {@link Product product} that was updated if successful
     * <br>
     * null otherwise
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Product modifyItem(String id, Product product) throws IOException;
}