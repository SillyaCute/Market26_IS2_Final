package packageTest;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dataAccess.DataAccess;
import domain.Comprador;
import domain.Contraoferta;
import domain.Seller;
import domain.Usuario;
import exceptions.NoGmailException;
import exceptions.NotInDBException;
import exceptions.ParamNullException;

public class CreateSaleBDBlackTest {
	
	 //sut:system under test
	 static DataAccess sut=new DataAccess();
	 
	 //additional operations needed to execute the test 
	 static TestDataAccess testDA=new TestDataAccess();

	 @SuppressWarnings("unused")
	private Seller seller; 
	private Comprador comprador;
	private Usuario user;
	private String sellerMail;
	private String sellerName;
	private String compradorMail;
	private String compradorName;
	private String userMail;
	private String userName;
	private String title;
	private String description;
	private int status;
	private float price;
	private Date pubDate;
	private List<Contraoferta> contraofertas = new ArrayList<>();
	
	@Before
	public void defaultValues() {
	    sellerMail="sellerTest@gamil.com";
		sellerName="Seller Test";
		compradorMail="compradorTest@gmail.com";
		compradorName="Comprador Test";
		userMail="userTest@gmail.com";
		userName="Usuario Test";
		title="futbol baloia";
		description="Used one hour";
		status=0;
		price=10;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		pubDate=null;
		try {
			pubDate = sdf.parse("05/10/2026");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
   }
	
	@Test //1
	public void testGetContraofertas1() {		
		comprador = null;
		try {
			sut.open();
			contraofertas = sut.getContraofertas(comprador);
			sut.close();			
			
			fail();
			
		/*} catch (ParamNullException e ) { 
			assertTrue(true);*/
		} catch (Exception e ) { 
				
			e.printStackTrace();
		    System.out.println("Error: " + e.getMessage());
			fail();
		} 
	}
	
	@Test //2
	public void testGetContraofertas2() {
		
		testDA.open();
		comprador = testDA.crearCompradorSinCarrito("compradorTest@ai.com", compradorName);
		testDA.close();
		try {
			sut.open();
			contraofertas = sut.getContraofertas(comprador);
			sut.close();			
			
			//El codigo solo funciona con correos @gmail.com en teoria, asi que getContraofertas deberia fallar
			fail(); 
		
		/*} catch (NoGmailException e ) { 
			assertTrue(true);*/
		} catch (Exception e ) { 
				
			e.printStackTrace();
		    System.out.println("Error: " + e.getMessage());
			fail();
		} finally {   
			testDA.open();
			testDA.removeComprador(compradorMail);
			testDA.close();
	    }
	}
	
	@Test //3
	public void testGetContraofertas3() {		
		comprador = new Comprador(compradorName, compradorMail, "Soy Teto-Chan", "Comprador", 100);
		try {
			sut.open();
			contraofertas = sut.getContraofertas(comprador);
			sut.close();			
			
			fail();
			
		/*} catch (NotInDBException e ) { 
			assertTrue(true);*/
		} catch (Exception e ) { 
				
			e.printStackTrace();
		    System.out.println("Error: " + e.getMessage());
		    //No tiene un control de excepciones, 
			fail();
		} 
	}
	
	@Test //4
	public void testGetContraofertas4() {
		
		testDA.open();
		comprador = testDA.crearCompradorConCarrito(compradorMail, compradorName);
		testDA.close();
		try {
			sut.open();
			contraofertas = sut.getContraofertas(comprador);
			sut.close();			
			
			assertFalse(contraofertas.isEmpty());
			
		} catch (Exception e ) { 
				
			e.printStackTrace();
		    System.out.println("Error: " + e.getMessage());
			fail();
		} finally {   
			testDA.open();
			testDA.removeCompradorConCarrito(compradorMail);
			testDA.close();
	    }
	}
	
	@Test //5
	public void testGetContraofertas5() {
		
		testDA.open();
		comprador = testDA.crearCompradorSinCarrito(compradorMail, compradorName);
		testDA.close();
		try {
			sut.open();
			contraofertas = sut.getContraofertas(comprador);
			sut.close();			
			
			assertTrue(contraofertas.isEmpty());
			
		} catch (Exception e ) { 
				
			e.printStackTrace();
		    System.out.println("Error: " + e.getMessage());
			fail();
		} finally {   
			testDA.open();
			testDA.removeComprador(compradorMail);
			testDA.close();
	    }
	}
	
	@Test //6
	public void testGetContraofertas6() {
		
		testDA.open();
		seller = testDA.crearSellerConVenta(sellerMail, sellerName);
		testDA.close();
		try {
			sut.open();
			contraofertas = sut.getContraofertas(seller);
			sut.close();			
			
			assertTrue(contraofertas.isEmpty());
			
		} catch (Exception e ) { 
				
			e.printStackTrace();
		    System.out.println("Error: " + e.getMessage());
			fail();
		} finally {   
			testDA.open();
			testDA.removeSellerConVenta(sellerMail);
			testDA.close();
	    }
	}
	
	@Test //7
	public void testGetContraofertas7() {
		
		testDA.open();
		seller = testDA.createSeller(sellerMail, sellerName);
		testDA.close();
		try {
			sut.open();
			contraofertas = sut.getContraofertas(seller);
			sut.close();			
			
			assertTrue(contraofertas.isEmpty());
			
		} catch (Exception e ) { 
				
			e.printStackTrace();
		    System.out.println("Error: " + e.getMessage());
			fail();
		} finally {   
			testDA.open();
			testDA.removeSeller(sellerMail);
			testDA.close();
	    }
	}
	
	@Test //8
	public void testGetContraofertas8() {
		
		testDA.open();
		user = testDA.crearUsuario(userMail, userName);
		testDA.close();
		try {
			sut.open();
			contraofertas = sut.getContraofertas(user);
			sut.close();			
			
			assertTrue(contraofertas.isEmpty());
			
		} catch (Exception e ) { 
				
			e.printStackTrace();
		    System.out.println("Error: " + e.getMessage());
			fail();
		} finally {   
			testDA.open();
			testDA.removeSeller(userMail);
			testDA.close();
	    }
	}
}
