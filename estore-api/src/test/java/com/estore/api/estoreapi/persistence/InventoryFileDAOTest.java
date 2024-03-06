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
import com.estore.api.estoreapi.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;
/**
* Test the Product File DAO class
*
*/
@Tag("Persistence-tier")
public class InventoryFileDAOTest {
   InventoryFileDAO inventoryFileDAO;
   Product[] testProducts;
   ObjectMapper mockObjectMapper;
   @BeforeEach
   public void setupInventoryFileDAO() throws IOException {
       mockObjectMapper = mock(ObjectMapper.class);
       testProducts = new Product[3];
       testProducts[0] = new Product("SuperMario",30.00,5);
       testProducts[1] = new Product("The Last of Us",60.00,0);
       testProducts[2] = new Product("Cuphead",20.00,10);
       when(mockObjectMapper.readValue(new File("blank.txt"),Product[].class)).thenReturn(testProducts);
       inventoryFileDAO = new InventoryFileDAO("blank.txt",mockObjectMapper);
   }

   @Test
   public void testGetProducts() throws IOException {
       Product[] p = inventoryFileDAO.getProducts();
       List<Product> productList = Arrays.asList(p);

       assertEquals(p.length,testProducts.length);
       for (int i = 0; i < testProducts.length;++i) assertTrue(productList.contains(testProducts[i]));;
   }

   @Test
   public void testFindProducts() throws IOException {
       Product[] p = inventoryFileDAO.findProducts("up");
       List<Product> productList = Arrays.asList(p);

       assertEquals(p.length, 2);
       assertTrue(productList.contains(testProducts[0]));
       assertTrue(productList.contains(testProducts[2]));
   }
   /**
    * Will test if getProduct and correctly get a product from the name
    */
   @Test
   public void testGetProduct() {
       Product p = inventoryFileDAO.getProduct("Cuphead");
       assertEquals("Cuphead",p.getName());
       assertEquals(10,p.getQuantity());
       assertEquals(20.0,p.getPrice());
   }

   /**
    * Will test if deleteProduct and correctly delete a product from the name
    */
   @Test
   public void testDeleteProduct() {
       Product temp = testProducts[2];
       Product res = assertDoesNotThrow(() -> inventoryFileDAO.deleteProduct("Cuphead"),"Unexpected exception thrown");
       assertEquals(res,temp);
       // Need -1 because of the delete function.
       assertEquals(inventoryFileDAO.products.size(),testProducts.length-1);
   }
   /**
    * Will test if createProduct and correctly create a product from the another product
    */

   @Test
   public void testCreateProduct() throws IOException {
       Product product = new Product("Red Dead Redemption",20.00,10);
       Product res = assertDoesNotThrow(() -> inventoryFileDAO.createProduct(product),"Unexpected exception thrown");
       assertNotNull(res);
       Product actual = inventoryFileDAO.getProduct("Red Dead Redemption");
       assertEquals(actual.getName(),product.getName());
       assertEquals(actual.getPrice(),product.getPrice());
       assertEquals(actual.getQuantity(),product.getQuantity());

       assertNull(inventoryFileDAO.createProduct(testProducts[1]));
   }
   /**
    * Will test if updateProduct and correctly update a product from the another product
    */
   @Test
   public void testUpdateProduct() throws IOException {
       Product p = new Product("Red Dead Redemption 2", 50.00, 20);
       assertDoesNotThrow(() -> inventoryFileDAO.updateProduct(p),"Unexpected exception thrown");
       Product actual = inventoryFileDAO.getProduct("Red Dead Redemption 2");
       assertEquals(null,actual);

       Product p2 = new Product("Red Dead Redemption 2", 100.00, 20);
       inventoryFileDAO.createProduct(p2);
       assertDoesNotThrow(() -> inventoryFileDAO.updateProduct(p2),"Unexpected exception thrown");
       actual = inventoryFileDAO.getProduct("Red Dead Redemption 2");
       assertEquals(p2, actual);
       assertEquals(100, actual.getPrice());
   }
   /**
    * Tests if the save exepction can work
    * @throws IOException
    */
   @Test
   public void testSaveException() throws IOException{
       doThrow(new IOException()).when(mockObjectMapper).writeValue(any(File.class),any(Product[].class));
       Product p = new Product("DarkSouls", 15.00, 15);
       assertThrows(IOException.class,() -> inventoryFileDAO.createProduct(p),"IOException not thrown");
   }
   /**
    * Tries to get a product that does not exist. Should give null 
    */
   @Test
   public void testGetProductNotFound() {
       Product p = inventoryFileDAO.getProduct("Some video game name");
       assertEquals(p,null);
   }

   /**
    *  Tries to delete a product that does not exist. Should give null
    */
   @Test
   public void testDeleteProductNotFound() {
       Product res = assertDoesNotThrow(() -> inventoryFileDAO.deleteProduct("Video game name"),"Unexpected exception thrown");
       assertEquals(res,null);
       // No reason for length -1 because it should not be deleted
       assertEquals(inventoryFileDAO.products.size(),testProducts.length);
   }

   /**
    *  Tests a product that cannot be found by name and tries to update edit it. 
    */
   @Test
   public void testUpdateProductNotFound() throws IOException {
       Product p = new Product("Batman game", 20.00, 15);
       Product res = assertDoesNotThrow(() -> inventoryFileDAO.updateProduct(p),"Unexpected exception thrown");
       assertNull(res);
   }


   @Test
   public void testConstructorException() throws IOException {
       ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
       // We want to simulate with a Mock Object Mapper that an
       // exception was raised during JSON object deseerialization
       // into Java objects
       // When the Mock Object Mapper readValue method is called
       // from the InventoryFileDAO load method, an IOException is
       // raised
       doThrow(new IOException()).when(mockObjectMapper).readValue(new File("blank.txt"),Product[].class);
       assertThrows(IOException.class,() -> new InventoryFileDAO("blank.txt",mockObjectMapper),"IOException not thrown");
   }
}