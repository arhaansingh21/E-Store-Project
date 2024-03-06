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
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.persistence.InventoryFileDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class AdminTest {
    InventoryFileDAO inventoryFileDAO;
    Product[] testProducts;
    ObjectMapper mockObjectMapper;
    Admin admin;
    Product product; //Tested and safe

    @BeforeEach
    public void setUpAdmin() throws IOException {
        product = new Product("test", 1, 1);
        testProducts = new Product[1];
        testProducts[0] = product;
        mockObjectMapper = mock(ObjectMapper.class);
        when(mockObjectMapper.readValue(new File("blank.txt"),Product[].class)).thenReturn(testProducts);
        inventoryFileDAO = new InventoryFileDAO("blank.txt",mockObjectMapper);
        admin = new Admin(inventoryFileDAO, "admin");
    }
    @Test
    public void testLogin(){
        boolean temp = admin.login("admin");
        assertEquals(true, temp);
    }
    @Test
    public void createProduct() throws IOException {
        Product p = admin.createProduct("Mortal Kombat", 30.00, 5);
        Product pActual = new Product("Mortal Kombat", 30.00, 5);
        assertEquals(pActual.getName(),p.getName());
        assertEquals(pActual.getPrice(),p.getPrice());
        assertEquals(pActual.getQuantity(),p.getQuantity());
    }

   @Test
    public void testUpdateProductNull() throws IOException{
        Product p = assertDoesNotThrow(() -> admin.updateProduct("Cuphead",20,15),"Unexpected exception thrown");
        assertNull(p);
    }

    @Test
    public void testUpdateProductNotNull() throws IOException{
        admin.updateProduct("test",100,100);
        assertEquals(100, testProducts[0].getQuantity());
        assertEquals(100, testProducts[0].getPrice());

        admin.updateProduct("test",5,100);
    }

    @Test
    public void testRemoveProduct() throws IOException {
        String name = "Cuphead";
        Product p = admin.removeProduct(name);
        assertNull(p);
    }
    @Test
    public void testGetId() {
        assertEquals("admin", admin.getId());
    }
}