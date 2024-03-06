package com.estore.api.estoreapi.model;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.Admin;
import com.estore.api.estoreapi.persistence.InventoryDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class AdminTest {
    @Mock
    InventoryDAO inventoryDAO;
    Admin admin;
    Product testProduct1;
    Product testProduct2;

    @BeforeEach
    public void setUpCustomer() throws IOException{
        admin = new Admin(inventoryDAO, "Admin"); 
        testProduct1=new Product("Switch",300,10);
        testProduct2=new Product("3DS",60,10);
    }


    @Test
    public void testModifyProduct() throws IOException{
        inventoryDAO.createProduct(testProduct1);
        admin.modifyProduct(Switch, 279.99, 20);
        assertEquals(279.99, inventoryDAO.getProduct("Switch").getPrice());
        assertEquals(20, inventoryDAO.getProduct("Switch").getQuantity());
    
    }
}
