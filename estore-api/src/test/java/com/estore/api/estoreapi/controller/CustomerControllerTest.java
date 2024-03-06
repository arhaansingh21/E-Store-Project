package com.estore.api.estoreapi.controller;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.estore.api.estoreapi.model.Customer;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.ShoppingCart;
import com.estore.api.estoreapi.persistence.CustomerDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.Tag;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
@Tag("Controller-tier")
public class CustomerControllerTest {

    @InjectMocks
    CustomerController mockCustomerController;

    @Mock
    CustomerDAO customerDAO;

    ResponseEntity<Product> productResponseEntity;
    ResponseEntity<ShoppingCart> cartResponseEntity;
    ResponseEntity<Customer> customerResponseEntity; 

    @Test
    public void testGetCart() throws IOException{
        ShoppingCart cart = new ShoppingCart(); 
        when(customerDAO.getShoppingCart(anyString())).thenReturn(cart);

        cartResponseEntity = mockCustomerController.getShoppingCart("");
        
        assertEquals(cartResponseEntity.getStatusCodeValue(), 200);
    }

    @Test
    public void testGetProductsIO() throws IOException{
        when(customerDAO.getShoppingCart(anyString())).thenThrow(IOException.class);
        
        cartResponseEntity = mockCustomerController.getShoppingCart("");
        
        assertEquals(cartResponseEntity.getStatusCodeValue(), 500);
    }

    @Test
    public void testGetProductNotFound() throws IOException{
        when(customerDAO.getShoppingCart(anyString())).thenReturn(null);

        cartResponseEntity= mockCustomerController.getShoppingCart("");
        
        assertEquals(cartResponseEntity.getStatusCodeValue(), 404);
    }

    @Test
    public void testAddItem() throws IOException{
        Product product = new Product("", 0, 0);
        when(customerDAO.addItem(anyString(), any(Product.class))).thenReturn(product);
        
        productResponseEntity = mockCustomerController.addItem("", product);
        
        assertEquals(productResponseEntity.getStatusCodeValue(), 200);
    }

    @Test
    public void testAddItemNull() throws IOException{
        Product product = new Product("", 0, 0);
        when(customerDAO.addItem(anyString(), any(Product.class))).thenReturn(null);
        
        productResponseEntity = mockCustomerController.addItem("", product);
        
        assertEquals(productResponseEntity.getStatusCodeValue(), 400);
    }

    @Test
    public void testAddItemIO() throws IOException{
        Product product = new Product("", 0, 0);
        when(customerDAO.addItem(anyString(), any(Product.class))).thenThrow(IOException.class);
        
        productResponseEntity = mockCustomerController.addItem("", product);
        
        assertEquals(productResponseEntity.getStatusCodeValue(), 500);
    }

    @Test
    public void testAddItemQuantity() throws IOException{
        Product product = new Product("", 0, 0);
        when(customerDAO.addItem(anyString(), any(Product.class), anyInt())).thenReturn(product);
        
        productResponseEntity = mockCustomerController.addItem("", 1, product);
        
        assertEquals(productResponseEntity.getStatusCodeValue(), 200);
    }

    @Test
    public void testAddItemQuantityNull() throws IOException{
        Product product = new Product("", 0, 0);
        when(customerDAO.addItem(anyString(), any(Product.class), anyInt())).thenReturn(null);
        
        productResponseEntity = mockCustomerController.addItem("", 1, product);
        
        assertEquals(productResponseEntity.getStatusCodeValue(), 400);
    }

    @Test
    public void testAddItemQuantityIO() throws IOException{
        Product product = new Product("", 0, 0);
        when(customerDAO.addItem(anyString(), any(Product.class), anyInt())).thenThrow(IOException.class);
        
        productResponseEntity = mockCustomerController.addItem("", 1, product);
        
        assertEquals(productResponseEntity.getStatusCodeValue(), 500);
    }

    @Test
    public void testRemoveItem() throws IOException{
        Product product = new Product("", 0, 0);
        when(customerDAO.removeItem(anyString(), any(Product.class))).thenReturn(product);
        
        productResponseEntity = mockCustomerController.removeItem("", product);
        
        assertEquals(productResponseEntity.getStatusCodeValue(), 200);
    }

    @Test
    public void testRemoveItemNull() throws IOException{
        Product product = new Product("", 0, 0);
        when(customerDAO.removeItem(anyString(), any(Product.class))).thenReturn(null);
        
        productResponseEntity = mockCustomerController.removeItem("", product);
        
        assertEquals(productResponseEntity.getStatusCodeValue(), 400);
    }

    @Test
    public void testRemoveItemIO() throws IOException{
        Product product = new Product("", 0, 0);
        when(customerDAO.removeItem(anyString(), any(Product.class))).thenThrow(IOException.class);
        
        productResponseEntity = mockCustomerController.removeItem("", product);
        
        assertEquals(productResponseEntity.getStatusCodeValue(), 500);
    }
    @Test
    public void testRemoveItemQuantity() throws IOException{
        Product product = new Product("", 0, 0);
        when(customerDAO.removeItem(anyString(), any(Product.class), anyInt())).thenReturn(product);
        
        productResponseEntity = mockCustomerController.removeItem("", 1, product);
        
        assertEquals(productResponseEntity.getStatusCodeValue(), 200);
    }

    @Test
    public void testRemoveItemQuantityNull() throws IOException{
        Product product = new Product("", 0, 0);
        when(customerDAO.removeItem(anyString(), any(Product.class), anyInt())).thenReturn(null);
        
        productResponseEntity = mockCustomerController.removeItem("", 1, product);
        
        assertEquals(productResponseEntity.getStatusCodeValue(), 400);
    }

    @Test
    public void testRemoveItemQuantityIO() throws IOException{
        Product product = new Product("", 0, 0);
        when(customerDAO.removeItem(anyString(), any(Product.class), anyInt())).thenThrow(IOException.class);
        
        productResponseEntity = mockCustomerController.removeItem("", 1, product);
        
        assertEquals(productResponseEntity.getStatusCodeValue(), 500);
    }
    @Test
    public void testGetCustomer() throws IOException{
        Customer customer = new Customer("1");
        when(customerDAO.getCustomer(anyString())).thenReturn(customer);
        
        customerResponseEntity = mockCustomerController.getCustomer("");
        
        assertEquals(customerResponseEntity.getStatusCodeValue(), 200);
    }

    @Test
    public void testGetCustomerNull() throws IOException{ 
        when(customerDAO.getCustomer(anyString())).thenReturn(null);
        
        customerResponseEntity = mockCustomerController.getCustomer("");
        
        assertEquals(customerResponseEntity.getStatusCodeValue(), 404);
    }

    @Test
    public void testGetCustomerIO() throws IOException{
        when(customerDAO.getCustomer(anyString())).thenThrow(IOException.class);
        
        customerResponseEntity = mockCustomerController.getCustomer("");
        
        assertEquals(customerResponseEntity.getStatusCodeValue(), 500);
    }
    @Test
    public void testCreateCustomer() throws IOException{
        Customer customer = new Customer("1");
        when(customerDAO.createCustomer(anyString())).thenReturn(customer);
        
        customerResponseEntity = mockCustomerController.createCustomer("1");
        
        assertEquals(customerResponseEntity.getStatusCodeValue(), 201);
    }

    @Test
    public void testCreateCustomerNull() throws IOException{
        when(customerDAO.createCustomer(anyString())).thenReturn(null);
        
        customerResponseEntity = mockCustomerController.createCustomer("1");
        
        assertEquals(customerResponseEntity.getStatusCodeValue(), 409);
    }

    @Test
    public void testCreateCustomerIO() throws IOException{
        Customer customer = new Customer("1");
        when(customerDAO.createCustomer(anyString())).thenThrow(IOException.class);
        
        customerResponseEntity = mockCustomerController.createCustomer("1");
        
        assertEquals(customerResponseEntity.getStatusCodeValue(), 500);
    }
    @Test
    public void testDeleteCustomer() throws IOException{
        Customer customer = new Customer("1");
        when(customerDAO.deleteCustomer(anyString())).thenReturn(customer);
        
        customerResponseEntity = mockCustomerController.deleteCustomer("");
        
        assertEquals(customerResponseEntity.getStatusCodeValue(), 200);
    }

    @Test
    public void testDeleteCustomerNull() throws IOException{
        when(customerDAO.deleteCustomer(anyString())).thenReturn(null);
        
        customerResponseEntity = mockCustomerController.deleteCustomer("");
        
        assertEquals(customerResponseEntity.getStatusCodeValue(), 404);
    }

    @Test
    public void testDeleteCustomerIO() throws IOException{
        when(customerDAO.deleteCustomer(anyString())).thenThrow(IOException.class);
        
        customerResponseEntity = mockCustomerController.deleteCustomer("");
        
        assertEquals(customerResponseEntity.getStatusCodeValue(), 500);
    }

    @Test
    public void modifyTest() throws IOException {
        Product product = new Product("a", 1, 1);
        when(customerDAO.modifyItem(anyString(), any(Product.class))).thenReturn(product);
        
        productResponseEntity = mockCustomerController.modifyItem("", product);
        
        assertEquals(productResponseEntity.getStatusCodeValue(), 200);
    }

    @Test
    public void modifyTest404() throws IOException {
        Product product = new Product("a", 1, 1);
        when(customerDAO.modifyItem(anyString(), any(Product.class))).thenReturn(null);
        
        productResponseEntity = mockCustomerController.modifyItem("", product);
        
        assertEquals(productResponseEntity.getStatusCodeValue(), 404);
    }
    
    @Test
    public void modifyTestIO() throws IOException{
        Product product = new Product("a", 1, 1);
        when(customerDAO.modifyItem(anyString(), any(Product.class))).thenThrow(IOException.class);
        
        productResponseEntity = mockCustomerController.modifyItem("", product);
        
        assertEquals(productResponseEntity.getStatusCodeValue(), 500);
    }
    
}