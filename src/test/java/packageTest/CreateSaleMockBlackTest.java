package packageTest;

import static org.junit.Assert.*;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dataAccess.DataAccess;
import domain.Carrito;
import domain.Comprador;
import domain.Contraoferta;
import domain.Sale;
import domain.Seller;
import domain.Usuario;
import exceptions.NoGmailException;
import exceptions.NotInDBException;
import exceptions.ParamNullException;

public class CreateSaleMockBlackTest {
	
	static DataAccess sut;
	
	protected MockedStatic<Persistence> persistenceMock;

	@Mock
	protected  EntityManagerFactory entityManagerFactory;
	@Mock
	protected  EntityManager db;
	@Mock
    protected  EntityTransaction  et;
	
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
	public void init() {
		MockitoAnnotations.openMocks(this);
        persistenceMock = Mockito.mockStatic(Persistence.class);
		persistenceMock.when(() -> Persistence.createEntityManagerFactory(Mockito.any()))
        .thenReturn(entityManagerFactory);
        
        Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();
		Mockito.doReturn(et).when(db).getTransaction();
	    sut=new DataAccess(db);
	    
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
	@After
	public  void tearDown() {
		persistenceMock.close();
	}
   
	
	@Test //El parametro de entrada es nulo
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
	
	@Test //El correo del comprador no contiene @gmail.com
	public void testGetContraofertas2() {
		

		comprador = new Comprador(compradorName, "compradorTest@ai.com", "Hatsune Miku", "Comprador", 100);
		Mockito.when(db.find(Usuario.class, comprador.getEmail())).thenReturn(comprador);
		Mockito.when(db.find(Comprador.class, comprador.getEmail())).thenReturn(comprador);

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
		} 
	}
	
	@Test //El comprador no esta en la base de datos
	public void testGetContraofertas3() {		
		comprador = new Comprador(compradorName, compradorMail, "Soy Teto-Chan", "Comprador", 100);
		Mockito.when(db.find(Usuario.class, comprador.getEmail())).thenReturn(comprador);
		Mockito.when(db.find(Comprador.class, comprador.getEmail())).thenReturn(comprador);
		
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
	
	@Test //El parametro es un comprador que ha realizado una contraoferta
	public void testGetContraofertas4() {
		
		Comprador comprador=null;
		Carrito carrito = null;
		Contraoferta contraoferta = null;
		Seller vendedor = null;
		Sale compraContraoferta = null;
		Date date = new Date();
		File file = null;
		
		comprador=new Comprador(compradorName, compradorMail, "Kasane Teto", "Comprador", 200);
		carrito = new Carrito();
		
		vendedor = new Seller(sellerName, sellerMail, "megustanlasvacas", "Vendedor", 200);
		compraContraoferta = new Sale("Vaca", "Imagina que tienes dos vacas", 0, 100, date, file, vendedor, 1);
		    
		contraoferta = new Contraoferta(100, compraContraoferta, comprador, vendedor);
		    
		carrito.addCompraAlmacenada(contraoferta);
		comprador.setCarrito(carrito);
		carrito.setComprador(comprador);
		
		Mockito.when(db.find(Usuario.class, vendedor.getEmail())).thenReturn(vendedor);
		Mockito.when(db.find(Usuario.class, comprador.getEmail())).thenReturn(comprador);
		Mockito.when(db.find(Seller.class, comprador.getEmail())).thenReturn(vendedor);
		Mockito.when(db.find(Comprador.class, comprador.getEmail())).thenReturn(comprador);
		Mockito.when(db.find(Carrito.class, carrito.getIdCarrito())).thenReturn(carrito);
		
		try {
			sut.open();
			contraofertas = sut.getContraofertas(comprador);
			sut.close();			
			
			assertFalse(contraofertas.isEmpty());
			
		} catch (Exception e ) { 
				
			e.printStackTrace();
		    System.out.println("Error: " + e.getMessage());
			fail();
		}
	}
	
	@Test //El parametro es un comprador que no ha realizado una contraoferta
	public void testGetContraofertas5() {
		
		comprador = new Comprador(compradorName, compradorMail, "Hatsune Miku", "Comprador", 100);
		System.out.println(comprador);
		Mockito.when(db.find(Usuario.class, comprador.getEmail())).thenReturn(comprador);
		Mockito.when(db.find(Comprador.class, comprador.getEmail())).thenReturn(comprador);
		
		try {
			sut.open();
			contraofertas = sut.getContraofertas(comprador);
			sut.close();			
			
			assertTrue(contraofertas.isEmpty());
			
		} catch (Exception e ) { 
				
			e.printStackTrace();
		    System.out.println("Error: " + e.getMessage());
			fail();
		} 
	}
	
	@Test //El parametro es un vendedor que ha recibido contraofertas
	public void testGetContraofertas6() {
		
		Seller vendedor = null;
		Comprador comprador = null;
		Sale ventaContraofertada = null;
		Contraoferta contraoferta = null;
		Date data = new Date();
		File file = null;
		
		vendedor = new Seller(sellerName, sellerMail, "megustanlasvacas", "Vendedor", 200);
		vendedor.addSale("Una vaca", "Dos vacas serian mejor", 0, 100, data, file, 1);
		comprador = new Comprador(compradorName, compradorMail, "contraseñaSegura", "Comprador", 100);
		
		ventaContraofertada = vendedor.getSales().getFirst();
		contraoferta = new Contraoferta(20, ventaContraofertada, comprador, vendedor);
		vendedor.getContraofertasRecibidas().add(contraoferta);
		
		Mockito.when(db.find(Usuario.class, vendedor.getEmail())).thenReturn(vendedor);
		Mockito.when(db.find(Seller.class, vendedor.getEmail())).thenReturn(vendedor);
		
		try {
			sut.open();
			contraofertas = sut.getContraofertas(vendedor);
			sut.close();			
			
			assertFalse(contraofertas.isEmpty());
			
		} catch (Exception e ) { 
				
			e.printStackTrace();
		    System.out.println("Error: " + e.getMessage());
			fail();
		}
	}
	
	@Test //El parametro es un vendedor que ha recibido contraofertas
	public void testGetContraofertas7() {
		
		Seller vendedor = null;
		
		vendedor = new Seller(sellerName, sellerMail, "megustanlasvacas", "Vendedor", 200);
		Mockito.when(db.find(Usuario.class, vendedor.getEmail())).thenReturn(vendedor);
		Mockito.when(db.find(Seller.class, vendedor.getEmail())).thenReturn(vendedor);
		
		try {
			sut.open();
			contraofertas = sut.getContraofertas(vendedor);
			sut.close();			
			
			assertTrue(contraofertas.isEmpty());
			
		} catch (Exception e ) { 
				
			e.printStackTrace();
		    System.out.println("Error: " + e.getMessage());
			fail();
		}
	}
	
	/*@Test //El parametro de entrada es un usuario, sin ser comprador o vendedor
	public void testGetContraofertas8() {
		
		user = new Usuario(userMail, userName);
		Mockito.when(db.find(Usuario.class, user.getEmail())).thenReturn(user);
		
		try {
			sut.open();
			contraofertas = sut.getContraofertas(user);
			sut.close();			
			
			assertTrue(contraofertas.isEmpty());
			
		} catch (Exception e ) { 
				
			e.printStackTrace();
		    System.out.println("Error: " + e.getMessage());
			fail();
		} 
	}*/
}
