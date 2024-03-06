package com.estore.api.estoreapi.model;
import java.io.IOException;

import com.estore.api.estoreapi.persistence.InventoryDAO;

public class Admin extends User{
    private final static String adminId = "admin";
    private InventoryDAO inventoryDAO;

    public Admin(InventoryDAO inventoryDAO, String id) {
        super(adminId);
        this.inventoryDAO = inventoryDAO;
    }
    @Override
    public String getId() {
        return Admin.adminId;
    }

    @Override
    public boolean login(String idString){
        return idString.equals(adminId);        
    }

    public Product createProduct(String name, double price, int quantity) throws IOException{
        Product product = new Product(name, price,quantity);
        return inventoryDAO.createProduct(product);
    }
    
    public Product updateProduct(String name, double price, int quantity) throws IOException{
        Product product = inventoryDAO.getProduct(name);
        if (product != null) {
            product.setPrice(price);
            product.setQuantity(quantity);
            return inventoryDAO.updateProduct(product);
        }
        return null;
    }

    public Product removeProduct(String name) throws IOException{
        return inventoryDAO.deleteProduct(name);
    }
}