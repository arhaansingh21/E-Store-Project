package com.estore.api.estoreapi.model;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.ShoppingCart;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

/**
 * Test for the Shopping Cart class
 */
@Tag("Model-tier")
public class ShoppingCartTest {
    private Product mockProduct1;
    private Product mockProduct2;
    private Product mockProduct3;
    private Product mockProduct1andahalf;

    private List<Product> productList;

    private ShoppingCart shoppingCart1;
    private ShoppingCart shoppingCart2;
    private ShoppingCart shoppingCartEmpty;

    /**
     * Set up. 
     * shoppingCart = ShoppingCart(null, 0)
     */
    @BeforeEach
    public void setupShoppingCart() {
        productList = new LinkedList<Product>();

        mockProduct1 = new Product("1", 1, 1);
        mockProduct1andahalf = new Product("1", 1, 2);
        mockProduct2 = new Product("2", 2, 2);
        mockProduct3 = new Product("3", 3, 3);

        productList.add(mockProduct1);
        productList.add(mockProduct2);
        productList.add(mockProduct3);
        
        shoppingCart1 = new ShoppingCart();
        shoppingCart2 = new ShoppingCart();
        shoppingCartEmpty = new ShoppingCart();

        for (Product product : productList) {
            shoppingCart1.addToCart(product);
            shoppingCart2.addToCart(product);
        }
    }

    @Test
    public void testConstructorNotNull() {
        assertNotNull(shoppingCart1);
        assertNotNull(shoppingCart2);
        assertNotNull(shoppingCartEmpty);
    }

    @Test
    public void testGetPrice() {
        assertEquals(14, shoppingCart1.getPrice());
        assertEquals(14, shoppingCart2.getPrice());
        assertEquals(0, shoppingCartEmpty.getPrice());
    }

    @Test
    public void testGetProducts() {
        assertNotNull(shoppingCart1.getProducts());
        assertNotNull(shoppingCart2.getProducts());
        assertNotNull(shoppingCartEmpty.getProducts());

        assertTrue(shoppingCart1.getProducts().contains(mockProduct1));
        assertTrue(shoppingCart1.getProducts().contains(mockProduct2));
        assertTrue(shoppingCart1.getProducts().contains(mockProduct2));
        assertNotSame(productList, shoppingCartEmpty.getProducts());

    }

    @Test
    public void testGetItemCount() {
        assertEquals(6, shoppingCart1.getItemCount());
        assertEquals(6, shoppingCart2.getItemCount());
        assertEquals(0, shoppingCartEmpty.getItemCount());
    }

    @Test
    public void testAddToCart() {
        shoppingCartEmpty.addToCart(mockProduct1);

        assertEquals(1, shoppingCartEmpty.getItemCount());
        assertEquals(1, shoppingCartEmpty.getPrice());

        shoppingCartEmpty.addToCart(mockProduct1);
        assertEquals(2, shoppingCartEmpty.getItemCount());
        assertEquals(2, shoppingCartEmpty.getPrice());
    }

    @Test
    public void testAddToCartQuantity() {
        shoppingCartEmpty.addToCart(mockProduct3, 2);

        assertEquals(2, shoppingCartEmpty.getItemCount());
        assertEquals(6, shoppingCartEmpty.getPrice());
        
        shoppingCartEmpty.addToCart(mockProduct3, 2);
        assertEquals(4, shoppingCartEmpty.getItemCount());
        assertEquals(12, shoppingCartEmpty.getPrice());
    }

    @Test
    public void testRemoveFromCart() {
        shoppingCart1.removeFromCart(mockProduct1);
        shoppingCart2.removeFromCart(mockProduct1);
        
        shoppingCart1.removeFromCart(mockProduct2);
        shoppingCart2.removeFromCart(mockProduct2);

        assertEquals(3, shoppingCart1.getItemCount());
        assertEquals(3, shoppingCart2.getItemCount());

        assertEquals(9, shoppingCart1.getPrice());
        assertEquals(9, shoppingCart2.getPrice());

        //assertThrows(IllegalArgumentException.class, () -> {
        //    shoppingCart1.removeFromCart(mockProduct2);
        //});
        shoppingCart1.removeFromCart(mockProduct1);
    }

    @Test
    public void testRemoveFromCartQuantity() throws IllegalArgumentException {
        shoppingCart1.removeFromCart(mockProduct1, 1);
        shoppingCart1.removeFromCart(mockProduct2, 2);
        shoppingCart1.removeFromCart(mockProduct3, 2);

        assertEquals(1, shoppingCart1.getItemCount());
        assertEquals(3, shoppingCart1.getPrice());

        shoppingCart1.removeFromCart(mockProduct1, 1);
    }

    @Test
    public void testRemoveFromCartQuantityZero() throws IllegalArgumentException {
        shoppingCart1.removeFromCart(mockProduct1, 1);
        shoppingCart1.removeFromCart(mockProduct2, 2);
        shoppingCart1.removeFromCart(mockProduct3, 3);

        assertEquals(new LinkedList<>(), shoppingCart1.getProducts());

        //assertThrows(IllegalArgumentException.class, () -> {
        //    shoppingCart1.removeFromCart(mockProduct3, 3);
        //});

        assertEquals(0, shoppingCart1.getItemCount());
        assertEquals(0, shoppingCart1.getPrice());
    }
    
    @Test
    public void modifyTest() {
        Product test1 = shoppingCart1.modifyItem(mockProduct1andahalf);
        int index = shoppingCart1.getProducts().indexOf(mockProduct1);
        Product test2 = shoppingCart1.getProducts().get(index);
        assertEquals(mockProduct1andahalf, test2);
        assertEquals(mockProduct1andahalf, test1);

        test1 = shoppingCart1.modifyItem(new Product("test", 1, 1));
        assertNull(test1);
    }
}
