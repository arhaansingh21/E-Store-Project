package com.estore.api.estoreapi.persistence;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.contains;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.Customer;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.ShoppingCart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;


@Tag("Persistence-tier")
public class CustomerFileDAOTest {
   CustomerFileDAO customerFileDAO;
   Customer[] testCustomers;
   ObjectMapper mockObjectMapper;
   @BeforeEach
   public void setupCustomerFileDAO() throws IOException {
       mockObjectMapper = mock(ObjectMapper.class);
       testCustomers = new Customer[3];
       testCustomers[0] = new Customer("0");
       testCustomers[1] = new Customer("1");
       testCustomers[2] = new Customer("2");
       when(mockObjectMapper.readValue(new File("blank.txt"),Customer[].class)).thenReturn(testCustomers);
       customerFileDAO = new CustomerFileDAO("blank.txt",mockObjectMapper);
   }

   @Test
   public void testGetShoppingCart() throws IOException {
        ShoppingCart cart = customerFileDAO.getShoppingCart("0");
        assertEquals(testCustomers[0].getCart(), cart);
   }

   @Test
   public void testAddItem() throws IOException {
        Product product = new Product("1", 1, 1);
        customerFileDAO.addItem("0", product);
        assertEquals(1, testCustomers[0].getCart().getItemCount());

        Product product2 = new Product("2", 1, 2);
        customerFileDAO.addItem("1", product2, 1);
        assertEquals(1, testCustomers[1].getCart().getItemCount());
   }

   @Test
   public void testRemoveItem() throws IOException {
        Product product = new Product("1", 1, 1);
        customerFileDAO.addItem("0", product);
        customerFileDAO.removeItem("0", product);
        assertEquals(0, testCustomers[0].getCart().getItemCount());

        Product product2 = new Product("2", 1, 2);
        customerFileDAO.addItem("1", product2);
        customerFileDAO.removeItem("1", product2, 1);
        assertEquals(1, testCustomers[1].getCart().getItemCount());
   }

   @Test
   public void testCreateCustomer() throws IOException {
       Customer c = new Customer("3");
       Customer customer = assertDoesNotThrow(() -> customerFileDAO.createCustomer("3"),"Unexpected exception thrown");
       assertNotNull(customer);
       assertEquals(customer.getId(), c.getId());

       assertNull(customerFileDAO.createCustomer("3"));
   }

   @Test
   public void testDeleteCustomer() throws IOException {
        Customer test = customerFileDAO.deleteCustomer("0");
        assertEquals(testCustomers[0], test);

        test = customerFileDAO.deleteCustomer("0");
        assertNull(test);
   }
   
   @Test
   public void testSaveException() throws IOException{
    doThrow(new IOException()).when(mockObjectMapper).writeValue(any(File.class),any(Customer[].class));
    Customer c = new Customer("test");
    assertThrows(IOException.class,() -> customerFileDAO.createCustomer("test"),"IOException not thrown");
   }

   @Test
   public void testGetCustomer() {
       Customer c = customerFileDAO.getCustomer("1");
       assertEquals(c, testCustomers[1]);
   }

   @Test
   public void testConstructorException() throws IOException {
       ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
       doThrow(new IOException()).when(mockObjectMapper).readValue(new File("blank.txt"),Customer[].class);
       assertThrows(IOException.class,() -> new CustomerFileDAO("blank.txt",mockObjectMapper),"IOException not thrown");
   }

   @Test
   public void modifyTest() throws IOException {
        Product product = new Product("1", 1, 1);
        Product product2 = new Product("1", 1, 2);
        customerFileDAO.addItem("0", product);
        customerFileDAO.modifyItem("0", product2);
        int index = testCustomers[0].getCart().getProducts().indexOf(product);
        assertEquals(product2, testCustomers[0].getCart().getProducts().get(index));
    }
}