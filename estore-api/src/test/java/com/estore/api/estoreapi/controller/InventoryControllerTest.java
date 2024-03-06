package com.estore.api.estoreapi.controller;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.persistence.InventoryDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.Tag;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

/**
* Test for the InventoryController class
*/
@ExtendWith(MockitoExtension.class)
@Tag("Controller-tier")
public class InventoryControllerTest {

    @InjectMocks
    InventoryContoller mockInventoryController;

    @Mock
    InventoryDAO inventoryDAO;

    ResponseEntity<Product> testEntityProduct;
    ResponseEntity<Product[]> testEntityProducts;

    /**
    * Testing getProduct given the product exists 
    */
    @Test
    public void testGetProduct() throws IOException{
        Product testProduct = new Product("SuperMario",30.00,5); 
        when(inventoryDAO.getProduct(testProduct.getName())).thenReturn(testProduct);

        testEntityProduct = mockInventoryController.getProduct(testProduct.getName());
        
        assertEquals(testEntityProduct.getStatusCodeValue(), 200);
    }

    @Test
    public void testGetProducts() throws IOException{
        Product[] testProducts = {new Product("SuperMario",30.00,5)}; 
        when(inventoryDAO.getProducts()).thenReturn(testProducts);

        testEntityProducts = mockInventoryController.getProducts();
        
        assertEquals(testEntityProducts.getStatusCodeValue(), 200);
    }

    @Test
    public void testGetProductsIO() throws IOException{
        when(inventoryDAO.getProducts()).thenThrow(IOException.class);
        
        testEntityProducts = mockInventoryController.getProducts();
        
        assertEquals(testEntityProducts.getStatusCodeValue(), 500);
    }

    /**
    * Testing getProduct given the product does not exist 
    */
    @Test
    public void testGetProductNotFound() throws IOException{
        when(inventoryDAO.getProduct(anyString())).thenReturn(null);

        ResponseEntity<Product> testEntityProduct = mockInventoryController.getProduct(anyString());
        
        assertEquals(testEntityProduct.getStatusCodeValue(), 404);
    }

    /**
    * Testing getProducts catch block for IOException 
    */
    @Test
    public void testGetProductIO() throws IOException{
        when(inventoryDAO.getProduct(anyString())).thenThrow(IOException.class);
        
        testEntityProduct = mockInventoryController.getProduct(anyString());
        
        assertEquals(testEntityProduct.getStatusCodeValue(), 500);
    }

    /**
    * Testing searchProducts  
    */
    @Test
    public void testSearchProducts() throws IOException{
        Product[] testProducts = new Product[3];
        testProducts[0] = new Product("Super Mario World 2",30.00,5);
        when(inventoryDAO.findProducts("mario")).thenReturn(testProducts);

        testEntityProducts = mockInventoryController.searchProducts("mario");
        
        assertEquals(testEntityProducts.getStatusCodeValue(), 200);
    }

    /**
    * Testing searchProducts catch block for IOException 
    */
    @Test
    public void testSearchProductsIO() throws IOException{
        when(inventoryDAO.findProducts(anyString())).thenThrow(IOException.class);
        
        testEntityProducts = mockInventoryController.searchProducts(anyString());
        
        assertEquals(testEntityProducts.getStatusCodeValue(), 500);
    }
    
    /**
    * Testing createProduct givin the Product does not already exist
    */
    @Test
    public void testCreateProduct() throws IOException{
        Product testProduct = new Product("Super Mario World 2",30.00,5);
        when(inventoryDAO.createProduct(testProduct)).thenReturn(testProduct);

        ResponseEntity<Product> testEntity = mockInventoryController.createProduct(testProduct);
        
        assertEquals(testEntity.getStatusCodeValue(), 201);    }
    /**
    * Testing createProduct givin the Product does not already exist
    */
    @Test
    public void testCreateProductConflict() throws IOException{
        Product testProduct = new Product("Super Mario World 2",30.00,5);
        when(inventoryDAO.createProduct(testProduct)).thenReturn(null);

        ResponseEntity<Product> testEntity = mockInventoryController.createProduct(testProduct);
        assertEquals(testEntity.getStatusCodeValue(), 409);    
    }
    /**
    * Testing createProduct catch block for an IOException 
    */
    @Test
    public void testCreateProductIO() throws IOException{
        Product testProduct = new Product("Super Mario World 2",30.00,5);
        when(inventoryDAO.createProduct(any(Product.class))).thenThrow(IOException.class);

        ResponseEntity<Product> testEntity = mockInventoryController.createProduct(testProduct);
        assertEquals(testEntity.getStatusCodeValue(), 500);    
    }
    
    /**
    * Testing updateProduct given the product exists 
    */
    @Test
    public void testUpdateProduct() throws IOException{
        Product testProduct = new Product("Super Mario World 2",30.00,5);
        when(inventoryDAO.updateProduct(testProduct)).thenReturn(testProduct);

        ResponseEntity<Product> testEntity = mockInventoryController.updateProduct(testProduct);
        
        assertEquals(testEntity.getStatusCodeValue(), 200);    
    }

    /**
    * Testing createProduct givin the Product does not already exist
    */
    @Test
    public void testUpdateProductConflict() throws IOException{
        Product testProduct = new Product("Super Mario World 2",30.00,5);
        when(inventoryDAO.updateProduct(testProduct)).thenReturn(null);

        ResponseEntity<Product> testEntity = mockInventoryController.updateProduct(testProduct);
        assertEquals(testEntity.getStatusCodeValue(), 404);    
    }
    /**
    * Testing updateProduct catch block for an IOException 
    */
    @Test
    public void testUpdateProductIO() throws IOException{
        Product testProduct = new Product("Super Mario World 2",30.00,5);
        when(inventoryDAO.updateProduct(any(Product.class))).thenThrow(IOException.class);

        ResponseEntity<Product> testEntity = mockInventoryController.updateProduct(testProduct);
        assertEquals(testEntity.getStatusCodeValue(), 500);    
    }
    

    /**
    * Testing Delete given the Product exists
    */
    @Test
    public void testDeleteProduct() throws IOException{
        Product testProduct = new Product("Super Mario World 2",30.00,5);
        when(inventoryDAO.deleteProduct(testProduct.getName())).thenReturn(testProduct);

        ResponseEntity<Product> testEntity = mockInventoryController.deleteProduct(testProduct.getName());
        
        assertEquals(testEntity.getStatusCodeValue(), 200);    
    }

          /**
    * Testing deleteProduct givin the Product does not already exist
    */
    @Test
    public void testDeleteProductConflict() throws IOException{
        when(inventoryDAO.deleteProduct(anyString())).thenReturn(null);

        ResponseEntity<Product> testEntity = mockInventoryController.deleteProduct(anyString());
        assertEquals(testEntity.getStatusCodeValue(), 404);    
    }
    /**
    * Testing deleteProduct catch block for an IOException 
    */
    @Test
    public void testDeleteProductIO() throws IOException{
        Product testProduct = new Product("1",30.00,5);
        when(inventoryDAO.deleteProduct("1")).thenThrow(IOException.class);

        ResponseEntity<Product> testEntity = mockInventoryController.deleteProduct(testProduct.getName());
        assertEquals(testEntity.getStatusCodeValue(), 500);    
    }

    
}



