package packageTest;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import configuration.ConfigXML;
import domain.Carrito;
import domain.Comprador;
import domain.Contraoferta;
import domain.Sale;
import domain.Seller;
import domain.Usuario;


public class TestDataAccess {
	protected  EntityManager  db;
	protected  EntityManagerFactory emf;

	ConfigXML  c=ConfigXML.getInstance();


	public TestDataAccess()  {
		
		System.out.println("TestDataAccess created");

		//open();
		
	}

	
	public void open(){
		
		String fileName=c.getDbFilename();
		
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			  db = emf.createEntityManager();
    	   }
		System.out.println("TestDataAccess opened");

		
	}
	public void close(){
		db.close();
		System.out.println("TestDataAccess closed");
	}

	public boolean removeSeller(String email) {
		System.out.println(">> TestDataAccess: removeSeller");
		Seller d = db.find(Seller.class, email);
		if (d!=null) {
			db.getTransaction().begin();
			db.remove(d);
			db.getTransaction().commit();
			return true;
		} else 
		return false;
    }
	
	public boolean removeComprador(String email) {
		System.out.println(">> TestDataAccess: removeComprador");
		Comprador d = db.find(Comprador.class, email);
		if (d!=null) {
			db.getTransaction().begin();
			db.remove(d);
			db.getTransaction().commit();
			return true;
		} else 
		return false;
    }
	
	public boolean removeCompradorConCarrito(String email) {
		System.out.println(">> TestDataAccess: removeComprador");
		Comprador compradorBorrar = db.find(Comprador.class, email);
		
		if (compradorBorrar!=null) {
			Carrito carroBorrar = compradorBorrar.getCarrito();
			List<Contraoferta> contraList = new ArrayList<>();
			contraList = carroBorrar.getComprasAlmacenadas();
			Contraoferta contraBorrar = contraList.removeFirst();
			Sale compraBorrar = contraBorrar.getCompra();
			Seller vendedorBorrar = contraBorrar.getVendedor();
			
			vendedorBorrar = db.find(Seller.class, vendedorBorrar);
			
			db.getTransaction().begin();
			db.remove(vendedorBorrar);
			db.remove(compraBorrar);
			db.remove(contraBorrar);
			db.remove(carroBorrar);
			db.remove(compradorBorrar);
			db.getTransaction().commit();
			return true;
		} else 
		return false;
    }
	
	public Seller createSeller(String email, String name) {
		System.out.println(">> TestDataAccess: addSeller");
		Seller seller=null;
			db.getTransaction().begin();
			try {
				seller=new Seller(name, email, "Rim de Lacent", "Vendedor", 100);
				db.persist(seller);
				db.getTransaction().commit();
			}
			catch (Exception e){
				e.printStackTrace();
			}
			return seller;
    }
	
	public Usuario crearUsuario(String email, String name) {
		System.out.println(">> TestDataAccess: addSeller");
		Usuario usuario=null;
			db.getTransaction().begin();
			try {
				usuario=new Usuario(name, email);
				db.persist(usuario);
				db.getTransaction().commit();
			}
			catch (Exception e){
				e.printStackTrace();
			}
			return usuario;
    }
	
	public boolean removeUsario(String email) {
		System.out.println(">> TestDataAccess: removeSeller");
		Usuario d = db.find(Usuario.class, email);
		if (d!=null) {
			db.getTransaction().begin();
			db.remove(d);
			db.getTransaction().commit();
			return true;
		} else 
		return false;
    }
	
	public Seller crearSellerConVenta(String email, String name) {
		System.out.println(">> TestDataAccess: addSeller");
		Seller seller=null;
		Sale compra = null;
		Date date = new Date();
		File file = null;
			db.getTransaction().begin();
			try {
				seller=new Seller(name, email, "Rim de Lacent", "Vendedor", 100);
				compra = new Sale("Una vaca", "Imagina que tuvieras dos vacas", 0, 60, date, file, seller, 1);
			    seller.getSales().add(compra);
				db.persist(seller);
				db.persist(compra);
				db.getTransaction().commit();
			}
			catch (Exception e){
				e.printStackTrace();
			}
			return seller;
    }
	
	public Seller crearSellerConContraoferta(String email, String name) {
		System.out.println(">> TestDataAccess: addSeller");
		Seller seller=null;
		Sale compra = null;
		Contraoferta contraoferta = null;
		Comprador comprador = null;
		Date date = new Date();
		File file = null;
		
			db.getTransaction().begin();
			try {
				seller=new Seller(name, email, "Rim de Lacent", "Vendedor", 100);
				compra = new Sale("Una vaca", "Imagina que tuvieras dos vacas", 0, 60, date, file, seller, 1);
			    seller.getSales().add(compra);
			    comprador = new Comprador("Comprador Test", "compradorTest@gmail.com", "contraseñaSegura", "Comprador", 100);
				contraoferta = new Contraoferta(20, compra, comprador, seller);
				seller.getContraofertasRecibidas().add(contraoferta);
				db.persist(seller);
				db.persist(compra);
				db.persist(contraoferta);
				db.getTransaction().commit();
			}
			catch (Exception e){
				e.printStackTrace();
			}
			return seller;
    }
	
	
	public boolean removeSellerConVenta(String email) {
		System.out.println(">> TestDataAccess: removeSeller");
		Seller d = db.find(Seller.class, email);
		Sale venta = null;
		if (d!=null) {
			venta = d.getSales().getFirst();
			
			db.getTransaction().begin();
			db.remove(d);
			db.remove(venta);
			db.getTransaction().commit();
			return true;
		} else 
		return false;
    }
	
	public Comprador crearCompradorConCarrito(String email, String name) {
		System.out.println(">> TestDataAccess: addSeller");
		Comprador comprador=null;
		Carrito carrito = null;
		Contraoferta contraoferta = null;
		Seller vendedor = null;
		Sale compraContraoferta = null;
		Date date = new Date();
		File file = null;
		
		db.getTransaction().begin();
		try {
		    comprador=new Comprador(name,email, "Kasane Teto", "Comprador", 200);
		    carrito = new Carrito();
		    
		    
		    vendedor = new Seller("Paquito", "PaquitoPaquez@gmail.com", "megustanlasvacas", "Vendedor", 200);
		    compraContraoferta = new Sale("Vaca", "Imagina que tienes dos vacas", 0, 100, date, file, vendedor, 1);
		    
		    contraoferta = new Contraoferta(100, compraContraoferta, comprador, vendedor);
		    
		    carrito.addCompraAlmacenada(contraoferta);
		    
		    comprador.setCarrito(carrito);
		    carrito.setComprador(comprador);
		    
			db.persist(comprador);
			db.persist(carrito);
			db.persist(vendedor);
			db.persist(compraContraoferta);
			db.persist(contraoferta);
			db.getTransaction().commit();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return comprador;
	}
	
	public Comprador crearCompradorSinCarrito(String email, String name) {
		Comprador comprador = null;
		
		db.getTransaction().begin();
		try {
		    
			comprador = new Comprador(name, email, "Hatsune Miku", "Comprador", 100);
			
			db.persist(comprador);
			db.getTransaction().commit();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		return comprador;
	}
	
	public boolean existSeller(String email) {
		 return  db.find(Seller.class, email)!=null;
		 

	}
		
		public Seller addSellerWithSale(String email, String sellerName, String title, String description, int status, float price,  Date pubDate, File file) {
			System.out.println(">> TestDataAccess: addSellerWithSale");
				Seller seller=null;
				db.getTransaction().begin();
				try {
					seller = db.find(Seller.class, email);
					if (seller==null) {
						seller=new Seller(email, sellerName);
				    	db.persist(seller);
					}
					seller.addSale(title, description, status, price, pubDate, file, 1);
					db.getTransaction().commit();
					System.out.println("Seller created "+seller);
					
					return seller;
					
				}
				catch (Exception e){
					e.printStackTrace();
				}
				return null;
	    }
		
	public boolean existSale(String sellerEmail, String title) {
			System.out.println(">> TestDataAccess: existSale");
			Seller s = db.find(Seller.class, sellerEmail);
			if (s!=null) {
				return s.doesSaleExist(title);
			} else 
			return false;
		}
	
		public void removeSale(String sellerEmail, String name) {
			System.out.println(">> TestDataAccess: removeRide");
			Seller vendedor = db.find(Seller.class, sellerEmail);
			Sale venta = db.find(Sale.class, name);
			if (venta!=null && vendedor!=null) {
				if(venta.getSeller().equals(vendedor)) {
					db.getTransaction().begin();
					venta.setDescatalogada(true);
					db.getTransaction().commit();
				} 
			}
		}

		
}