package com.estore.api.estoreapi.controller;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.model.Customer;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.ShoppingCart;
import com.estore.api.estoreapi.model.Trade;
import com.estore.api.estoreapi.persistence.CustomerDAO;

 /**
 * Handles the REST API requests for the Customer resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 */
@RestController
@RequestMapping("/home")
public class CustomerController  {
    
    private static final Logger LOG = Logger.getLogger(InventoryContoller.class.getName());
    private CustomerDAO customerDAO;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param customerDAO The {@link CustomerDAO Customer Data Access Object} to perform CRUD
     *                operations
     *                <br>
     *                This dependency is injected by the Spring Framework
     */
    public CustomerController(CustomerDAO customerDAO){
        this.customerDAO = customerDAO;
    }

     /**
     * Responds to the GET request for a {@linkplain ShoppingCart shoppingCart} of a {@linkplain Customer customer} with if
     * 
     * @param id The id used to locate the {@link Customer customer}'s shoppingCart
     *
     * @return ResponseEntity with {@link ShoppingCart shoppingCart} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/shoppingcart")
    public ResponseEntity<ShoppingCart> getShoppingCart(@RequestParam String id) {
        LOG.info("GET /shoppingcart?id=" + id);
        try {
            ShoppingCart cart = customerDAO.getShoppingCart(id);
            if (cart != null)
                return new ResponseEntity<ShoppingCart>(cart,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Adds a {@linkplain Product product} to the {@linkplain ShoppingCart shoppingCart} of a {@linkplain Customer customer} with id
     * 
     * @param id - id of the customer
     * @param product - product to add
     * 
     * @return HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping(value="/add", params="id")
    public ResponseEntity<Product> addItem(@RequestParam String id, @RequestBody Product product) {
        LOG.info("POST /add?id=" + id + "|" + product);

        try {
            Product status = customerDAO.addItem(id, product);
            if (status != null) {
                return new ResponseEntity<Product>(status, HttpStatus.OK);
            } else { 
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   /**
     * Adds a {@linkplain Product product} to the {@linkplain ShoppingCart shoppingCart} of a {@linkplain Customer customer} with id
     * 
     * @param id - id of the customer
     * @param product - product to add
     * @param quantity of the product to add
     * 
     * @return HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping(value="/add", params={"id", "quantity"})
    public ResponseEntity<Product> addItem(@RequestParam String id, @RequestParam int quantity, @RequestBody Product product) {
        LOG.info("POST /add?id=" + id + "&quantity=" + quantity + "|" + product);

        try {
            Product status = customerDAO.addItem(id, product, quantity);
            if (status != null) {
                return new ResponseEntity<Product>(status, HttpStatus.OK);
            } else { 
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Removes a {@linkplain Product product} from the {@linkplain ShoppingCart shoppingCart} of a {@linkplain Customer customer} with id
     * 
     * @param id - id of the customer
     * @param product - product to remove
     * 
     * @return HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping(value="/remove", params={"id", "quantity"})
    public ResponseEntity<Product> removeItem(@RequestParam String id, @RequestParam int quantity, @RequestBody Product product) {
        LOG.info("POST /remove?id=" + id + ", +" + quantity + " |" + product);

        try {
            Product status = customerDAO.removeItem(id, product, quantity);
            if (status != null) {
                return new ResponseEntity<Product>(status, HttpStatus.OK);
            } else { 
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

     /**
     * Removes a {@linkplain Product product} from the {@linkplain ShoppingCart shoppingCart} of a {@linkplain Customer customer} with id
     * 
     * @param id - id of the customer
     * @param product - product to remove
     * 
     * @return HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("/remove")
    public ResponseEntity<Product> removeItem(@RequestParam String id, @RequestBody Product product) {
        LOG.info("POST /remove?id=" + id + "|" + product);

        try {
            Product status = customerDAO.removeItem(id, product);
            if (status != null) {
                return new ResponseEntity<Product>(status, HttpStatus.OK);
            } else { 
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates a {@linkplain Product product} in the {@linkplain ShoppingCart shoppingCart} of a {@linkplain Customer customer} with id
     * 
     * @param id - id of the customer
     * @param product - product to update
     * 
     * @return HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("/modify")
    public ResponseEntity<Product> modifyItem(@RequestParam String id, @RequestBody Product product) {
        LOG.info("PUT /modify?id=" + id + "|" + product);
        try {
            Product status = customerDAO.modifyItem(id, product);
            if (status != null) {
                return new ResponseEntity<Product>(status, HttpStatus.OK);
            } else {
                return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

     /**
     * Responds to the GET request for a {@linkplain Customer customer} for the given id
     * 
     * @param id The name used to locate the {@link Customer customer}
     *
     * @return ResponseEntity with {@link Customer customer} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/login{id}")
    public ResponseEntity<Customer> getCustomer(@RequestParam String id) {
        LOG.info("GET /login/?name=" + id);
        try {
            Customer customer = customerDAO.getCustomer(id);
            if (customer != null)
                return new ResponseEntity<Customer>(customer,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Customer customer} with the provided Customer object
     * 
     * @param CustomerFileDAO - The {@link Customer customer} to create
     * 
     * @return HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Customer customer} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("/register")
    public ResponseEntity<Customer> createCustomer(@RequestParam String id) {
        LOG.info("POST /register?id=" + id);

        try {
            Customer status = customerDAO.createCustomer(id);
            if (status != null) {
                return new ResponseEntity<Customer>(status, HttpStatus.CREATED);
            } else { 
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Customer customer} with the given id
     * 
     * @param id The id of the {@link Customer customer} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable String id) {
        LOG.info("DELETE " + id);

        try {
            Customer status = customerDAO.deleteCustomer(id);
            if (status != null) {
                return new ResponseEntity<Customer>(status,HttpStatus.OK);
            } else { 
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for a list of {@linkplain Trade trade} for the customer of the given id
     * 
     * @param id The name used to locate the {@link Customer customer}
     *
     * @return ResponseEntity with an array of {@link Trade trade} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/trades{id}")
    public ResponseEntity<Map<String, Trade[]>> getTrades(@RequestParam String id) throws IOException {
        LOG.info("GET /trades/?id=" + id);
        try {
            Map<String, Trade[]> trades = customerDAO.getTrades(id);
            if (trades != null)
                return new ResponseEntity<Map<String, Trade[]>>(trades,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for a map of {@linkplain Customer customer} : {@linkplain Trade trade} for all customers
     * 
     * @return ResponseEntity with a map of {@linkplain Customer customer} : {@linkplain Trade trade} for all customers and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/trades/all")
    public ResponseEntity<Map<String, Trade[]>> getTrades() throws IOException {
        LOG.info("GET /trades/");
        try {
            Map<String, Trade[]> trades = customerDAO.getTrades();
            if (trades != null)
                return new ResponseEntity<Map<String, Trade[]>>(trades,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Adds a {@linkplain Trade trade} to the {@linkplain Customer customer} with id
     * 
     * @param id - id of the customer
     * @param product - product to add 
     * 
     * @return HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping(value="/trades/add", params={"id"})
    public ResponseEntity<Trade> addTrade(@RequestParam String id, @RequestBody Product product) throws IOException {
        LOG.info("POST /trades/add?id=" + id + " | " + product);
        try {
            Trade trade = customerDAO.addTrade(id, product);
            return new ResponseEntity<Trade>(trade,HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Sets a {@linkplain Trade trade} status of the {@linkplain Customer customer} with id
     * 
     * @param id - id of the customer
     * @param trade - trade to add
     * @param status - status to set. "denied", "accepted", any other string is pending
     * 
     * @return HTTP status of OK<br>
     * ResponseEntity with HTTP status of NOT_FOUND if the trade was not found
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("/trades/status/string")
    public ResponseEntity<Trade> setTradeStatus(@RequestParam String id, @RequestBody Trade trade, @RequestParam String status) throws IOException {
        LOG.info("PUT /trades/status?id=" + id + "&status=" + status + " | " + trade);
        try {
            Trade returnTrade = customerDAO.setTradeStatus(id, trade, status);
            if (returnTrade != null) {
                return new ResponseEntity<Trade>(returnTrade,HttpStatus.OK); 
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
