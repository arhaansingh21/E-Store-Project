package com.estore.api.estoreapi.model;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;


/**
 * Test for the product class
 */
@Tag("Model-tier")
public class ProductTest {

    Product product;
    String name;
    double price;
    int quantity;
    Product product2;
    String name2;
    double price2;
    int quantity2;
    Product product3;
    String name3;
    double price3;
    int quantity3;
    Product product4;
    String name4;
    double price4;
    int quantity4;

    @BeforeEach
    public void setUpProductTest(){
        name = "Mario Kart";
        price = 10.0;
        quantity = 8;
        product = new Product(name, price, quantity);

        name2 = "Mario Kart";
        price2 = 10.0;
        quantity2 = 10;
        product2 = new Product(name2, price2, quantity);
        
        name3 = "Yeet";
        price3 = 5.0;
        quantity3 = 8;
        product3 = new Product(name3, price3, quantity3);

        name4 = "Mario Kart";
        price4 = 5.0;
        quantity4 = 8;
        product4 = new Product(name4, price4, quantity4);
    }

    /**
     * Tests the product name getter
     */
    @Test
    public void testGetName(){
        assertEquals(name, product.getName());
    }

    /**
     * Tests the product name setter
     */
    @Test
    public void testSetName(){
        product.setName("Minecraft");
        assertEquals("Minecraft", product.getName());
    }

    /**
     * Tests the product quantity getter
     */
    @Test
    public void testGetQuantity(){
        assertEquals(8, product.getQuantity());
    }

    @Test 
    public void testEquals() {
        LinkedList<Product> list = new LinkedList<>();
        list.add(product);
        assertTrue(product.equals(product2));
        assertFalse(product.equals(product3));
        assertFalse(product.equals(product4));
        assertTrue(list.contains(product2));

        Object testObj = new Object();
        assertFalse(product.equals(testObj));
    }

    /**
     * Tests the product quantity setter
     */
    @Test
    public void testSetQuantity(){
        product.setQuantity(18);
        assertEquals(18, product.getQuantity());

        product.setQuantity(-1);
        assertEquals(0, product.getQuantity());
    }
    /**
     * Tests the product quantity exception
     */
    /*
     @Test
    public void testQuantityException(){
        assertThrows(IllegalArgumentException.class,() -> {
            product.setQuantity(-1); });
    }
    */
    

    /**
     * Tests the product price getter
     */
    @Test
    public void testGetPrice(){
        assertEquals(price, product.getPrice());
    }

    /**
     * Tests the product price setter
     */
    @Test
    public void testSetPrice(){
        product.setPrice(15.00);
        assertEquals(15.00, product.getPrice());

        product.setPrice(-1);
        assertEquals(0, product.getPrice());

        product.setPrice(0);
        assertEquals(0, product.getPrice());
    }

    /**
     * Tests the product set price exception
     */
    /*
    @Test
    public void testPriceException(){
        assertThrows(IllegalArgumentException.class,() -> {
            product.setPrice(-1); });
    }
    */

    /**
     * Tests the product toString
     */
    @Test
    public void testToString(){
        int quantity = 4;
        double price = 10.0;
        String name = "Mario Kart";
        String expectedString = String.format(Product.STRING_FORMAT, name, price, quantity);
        Product tester = new Product(name, price, quantity);
        String actualString = tester.toString();
        assertEquals(expectedString, actualString);
    }

    @Test
    public void testHash() {
        assertNotNull(product.hashCode());
    }

    /**
     * Tests the product constructor
     */
    @Test
    public void testConstructor(){
        String name = "Cuphead";
        double price = 20.7;
        int quantity = 8;
        Product product = new Product(name, price, quantity);
        assertEquals(name, product.getName());
        assertEquals(price, product.getPrice());
        assertEquals(quantity, product.getQuantity());
    }

    @Test
    public void testConstructorNegative(){
        String name = "Cuphead";
        double price = -1;
        int quantity = -1;
        Product product = new Product(name, price, quantity);
        assertEquals(name, product.getName());
        assertEquals(0, product.getPrice());
        assertEquals(0, product.getQuantity());
    }

}
