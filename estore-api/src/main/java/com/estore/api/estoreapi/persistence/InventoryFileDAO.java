package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Product;

/**
 * Implements the functionality for JSON file-based peristance for Products
 */
@Component
public class InventoryFileDAO implements InventoryDAO {
    private static final Logger LOG = Logger.getLogger(InventoryFileDAO.class.getName());
    Map<String,Product> products;   // Provides a local cache of the product objects
    private ObjectMapper objectMapper;  // Provides conversion between Product objects and JSON text format written to the file
    //private static int nextId;  // The next Id to assign to a new Product
    private String filename;    // Filename to read from and write to

    /**
     * Creates a Product File Data Access Object
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object
     * @throws IOException when file cannot be accessed or read from
     */
    public InventoryFileDAO(@Value("${products.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    /**
     * Generates the next id for a new Product
     *
     * @return The next id
     */
    /**
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }
    /**

    /**
     * Generates an array of all the products from the tree map
     *
     * @return  The array of all the products
     */
    private Product[] getProductsArray() {
        return getProductsArray(null);
    }

    /**
     * Generates an array of all the products from the tree map for any
     * product that contains the text specified by containsText
     * If containsText is null, the array contains all the Products
     * in the tree map
     *
     * @return  The array of all the products, may be empty
     */
    private Product[] getProductsArray(String containsText) { // if containsText == null, no filter
        ArrayList<Product> productArrayList = new ArrayList<>();
        for ( Product product : products.values() ) {
            if( containsText == null || product.getName().toLowerCase().contains(containsText))
            productArrayList.add(product);
        }
        Product[] productArr = new Product[productArrayList.size()];
        productArrayList.toArray(productArr);
        return productArr;
    }

    /**
     * Saves the Products from the map into the file as an array of JSON objects
     *
     * @return true if the Product were written successfully
     *
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Product[] productArray = getProductsArray();
        objectMapper.writeValue(new File(filename),productArray);
        return true;
    }

    /**
     * Loads Products from the JSON file into the map
     * Also sets next id to one more than the greatest id found in the file
     *
     * @return true if the file was read successfully
     *
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        products = new TreeMap<>();
        //nextId = 0;

        // Deserializes the JSON objects from the file into an array of Products
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Product[] productArray = objectMapper.readValue(new File(filename),Product[].class);

        // Add each Product to the tree map and keep track of the greatest id
        for (Product product : productArray) {
            products.put(product.getName(),product);
        }
        // Make the next id one greater than the maximum from the file
        //++nextId;
        return true;
    }


    @Override
    public Product[] getProducts() throws IOException {
        synchronized(products) {
            return getProductsArray();
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Product[] findProducts(String containsText) throws IOException{
        return getProductsArray(containsText.toLowerCase());
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Product getProduct(String name) {
        return products.get(name);
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Product createProduct(Product product) throws IOException {
        synchronized(products) {
            if (products.containsKey(product.getName())) {
                return null;
            }
            products.put(product.getName(),product);
            save(); // may throw an IOException
            return product;
        }
    }


    /**
     ** {@inheritDoc}
     */
    @Override
    public Product updateProduct(Product product) throws IOException {
        synchronized(products) {
            if (!products.containsKey(product.getName())) // if product does not exist
                return null;

            products.put(product.getName(),product);
            save(); // may throw an IOException
            return product;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Product deleteProduct(String name) throws IOException {
        synchronized(products) {
            if (products.containsKey(name)) {
                Product deleted = products.get(name);
                products.remove(name);
                save();
                return deleted;                    
            }  
        return null;
    }
    }
}