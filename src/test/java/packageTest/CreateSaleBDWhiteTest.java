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
import exceptions.MustBeLaterThanTodayException;
import exceptions.ParamNullException;

public class CreateSaleBDWhiteTest {
	
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
	    sellerMail="sellerTest@ehu.eus";
		sellerName="Seller Test";
		compradorMail="compradorTest@ehu.eus";
		compradorName="Comprador Test";
		userMail="userTest@ehu.eus";
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
	@Test //El parametro de entrada es un comprador cuyo carrito está vacio
	public void testGetContraofertas1() {
		
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
	
	@Test //El parametro entrada es un comprador que ha realizado contraofertas, asi que su carrito no esta vacio
	public void testGetContraofertas2() {
		
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
	
	@Test //El parametro de entrada es un vendedor
	public void testGetContraofertas3() {
		
		testDA.open();
		seller = testDA.crearSellerConContraoferta(sellerMail, sellerName);
		System.out.println(seller);
		testDA.close();
		try {
			sut.open();
			contraofertas = sut.getContraofertas(seller);
			sut.close();			
			
			assertFalse(contraofertas.isEmpty());
			
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
	
	/*@Test //El parametro de entrada es un usuario, no es comprador ni vendedor
	public void testGetContraofertas4() {
		
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
	}*/

}
