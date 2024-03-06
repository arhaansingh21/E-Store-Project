package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
/*
 * Testing Customer Class
 */
@Tag("Model-tier")
public class CustomerTest {
    private Product testProduct1, testProduct2, testProduct1andahalf;
    private List<Product> ProductList;

    @BeforeEach
    public void setUpCustomer() {
        testProduct1 = new Product("Switch",300,10);
        testProduct2 = new Product("3DS",60,10);
    }

    @Test
    public void testAddToCart() {
        Customer testCustomer = new Customer("John");
        testCustomer.addToCart(testProduct1);
        testCustomer.addToCart(testProduct2, 1);
        assertEquals(11, testCustomer.getCart().getItemCount());
        assertEquals(3060.0,testCustomer.getCart().getPrice());
    }

    @Test
    public void testRemoveFromCart() {
        Customer testCustomer = new Customer("John");
        testCustomer.addToCart(testProduct1);
        testCustomer.removeFromCart(testProduct1);
        assertEquals(0, testCustomer.getCart().getItemCount());
        assertEquals(0,testCustomer.getCart().getPrice());
    }

    @Test
    public void testRemoveFromCartQuantity() {
        Customer testCustomer = new Customer("John");
        testCustomer.addToCart(testProduct1);
        testCustomer.removeFromCart(testProduct1, 5);
        assertEquals(5, testCustomer.getCart().getItemCount());
        assertEquals(1500,testCustomer.getCart().getPrice());
    }

    @Test
    public void testGetCart() {
        Customer testCustomer = new Customer("John");
        testCustomer.addToCart(testProduct1);
        testCustomer.addToCart(testProduct2);
        
        assertTrue(testCustomer.getCart().getProducts().contains(testProduct1));
        assertTrue(testCustomer.getCart().getProducts().contains(testProduct2));
    }

    @Test
    public void testGetId() {
        Customer testCustomer = new Customer("John");
        assertEquals("John", testCustomer.getId());
    }

    @Test
    public void testConstructor() {
        Customer testCustomer = new Customer("John");
        assertNotNull(testCustomer);
    }

    @Test
    public void modifyTest() {
        Customer testCustomer = new Customer("John");
        Product mockProduct1 = new Product("1", 1, 1);
        Product mockProduct1andahalf = new Product("1", 1, 2);

        testCustomer.addToCart(mockProduct1);
        Product test = testCustomer.modify(mockProduct1andahalf);
        assertEquals(mockProduct1andahalf, test);

        test = testCustomer.modify(new Product("a", 1, 1));
        assertNull(test);
    }
}