package dataAccess;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Seller;
import domain.Usuario;
import domain.Administrador;
import domain.Carrito;
import domain.Comentario;
import domain.Comprador;
import domain.Contraoferta;
import domain.Reporte;
import domain.Sale;
import exceptions.FileNotUploadedException;
import exceptions.MustBeLaterThanTodayException;
import exceptions.SaleAlreadyExistException;
import gui.MainGUI;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess  {
	private  EntityManager  db;
	private  EntityManagerFactory emf;
    private static final int baseSize = 160;
    private Seller ofertasBorradas;

	private static final String basePath="src/main/resources/images/";
	private static final String dbServerDir = "src/main/resources/db/";


	ConfigXML c=ConfigXML.getInstance();

     public DataAccess()  {
		if (c.isDatabaseInitialized()) {
			String fileName=c.getDbFilename();

			if (!c.isDatabaseLocal()) fileName=dbServerDir+fileName;
			
			File fileToDelete= new File(fileName);
			if(fileToDelete.delete()){
				File fileToDeleteTemp= new File(fileName+"$");
				fileToDeleteTemp.delete();
				System.out.println("File deleted");
			 } else {
				 System.out.println("Operation failed");
				}
		}
		open();
		if  (c.isDatabaseInitialized()) 
			initializeDB();
		System.out.println("DataAccess created => isDatabaseLocal: "+c.isDatabaseLocal()+" isDatabaseInitialized: "+c.isDatabaseInitialized());

		close();

	}
     
    public DataAccess(EntityManager db) {
    	this.db=db;
    }

	
	
	/**
	 * This method  initializes the database with some products and sellers.
	 * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	public void initializeDB(){
		
		db.getTransaction().begin();

		try { 
	       
		    //Create sellers 
			Seller seller1=new Seller("Aitor Fernandez", "seller1@gmail.com","123", "Vendedor");
			Seller seller2=new Seller("Ane Gaztañaga", "seller2@gmail.com","321", "Vendedor");
			Seller seller3=new Seller("Test Seller", "seller3@gmail.com", "000", "Vendedor");
			Seller seller4=new Seller("a", "a@gmail.com", "a", "Vendedor", 200);
			ofertasBorradas=new Seller("Basura", "basuraBasurez@gmail.com", "basura", "Vendedor");

			//Crear comprador
			Comprador comprador1=new Comprador("b", "b@gmail.com", "b", "Comprador", 200);
			
			//Crear admin supremo
			Administrador admin=new Administrador("Admin", "admin@gmail.com", "admin", "Administrador");
			
			//Create products
			Date today = UtilDate.trim(new Date());
		
			
			seller1.addSale("futbol baloia", "oso polita, gutxi erabilita", 2, 10,  today, null, 1);
			seller1.addSale("salomon mendiko botak", "44 zenbakia, 3 ateraldi",2, 20,  today, null, 1);
			seller1.addSale("samsung 42\" telebista", "berria, erabili gabe", 2, 175,  today, null, 1);


			seller2.addSale("imac 27", "7 urte, dena ondo dabil", 1, 200,today, null, 1);
			seller2.addSale("iphone 17", "oso gutxi erabilita", 2, 400, today, null, 1);
			seller2.addSale("orbea mendiko bizikleta", "29\" 10 urte, mantenua behar du", 3,225, today, null, 1);
			seller2.addSale("polar kilor erlojua", "Vantage M, ondo dago", 3, 30, today, null, 1);

			seller3.addSale("sukaldeko mahaia", "1.8*0.8, 4 aulkiekin. Prezio finkoa", 3,45, today, null, 1);
			
			seller4.addSale("Vaca", "Dos buenas vacas que puedo decir", 1, 200, today, null, 2);
			seller4.addSale("Espaguetis", "Espaguetis a medio empezar", 2, 10, today, null, 30);
			seller4.addSale("Cocos", "Sacados del himalaya", 1, 20, today, null, 12);
			
			db.persist(seller1);
			db.persist(seller2);
			db.persist(seller3);
			db.persist(seller4);
			db.persist(comprador1);
			db.persist(admin);
			db.persist(ofertasBorradas);
	
			db.getTransaction().commit();
			System.out.println("Db initialized");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * This method creates/adds a product to a seller
	 * 
	 * @param title of the product
	 * @param description of the product
	 * @param status 
	 * @param selling price
	 * @param category of a product
	 * @param publicationDate
	 * @return Product
 	 * @throws SaleAlreadyExistException if the same product already exists for the seller
	 */
	
	
	public Sale createSale(String title, String description, int status, float price,  Date pubDate, String sellerEmail, File file//Unai:
			, int cant) throws  FileNotUploadedException, MustBeLaterThanTodayException, SaleAlreadyExistException {
		

		System.out.println(">> DataAccess: createProduct=> title= "+title+" seller="+sellerEmail);
		try {
		

			if(pubDate.before(UtilDate.trim(new Date()))) {
				throw new MustBeLaterThanTodayException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.ErrorSaleMustBeLaterThanToday"));
			}
			if (file==null)
				throw new FileNotUploadedException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.ErrorFileNotUploadedException"));

			db.getTransaction().begin();
			
			Seller seller = db.find(Seller.class, sellerEmail);
			System.out.println(""+seller);
			if (seller.doesSaleExist(title)) {
				db.getTransaction().commit();
				throw new SaleAlreadyExistException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.SaleAlreadyExist"));
			}

			Sale sale = seller.addSale(title, description, status, price, pubDate, file, cant);
			//next instruction can be obviated

			db.persist(seller); 
			db.getTransaction().commit();
			 System.out.println("sale stored "+sale+ " "+seller);

			return sale;
		} catch (NullPointerException e) {
			   e.printStackTrace();
			// TODO Auto-generated catch block
			db.getTransaction().commit();
			return null;
		}
		
		
	}
	
	public void borrarOferta(Sale oferta) {
		db.getTransaction().begin();
		Query query = db.createQuery("DELETE FROM Sale s WHERE s.saleNumber=" + oferta.getSaleNumber());
		int reportesBorrados = query.executeUpdate();
		db.getTransaction().commit();
	}
	
	public void descatalogarOferta(Sale oferta) {
		db.getTransaction().begin();
		Sale ofertaDescatalogar = db.find(Sale.class, oferta.getSaleNumber());
		ofertaDescatalogar.setDescatalogada(true);
		db.getTransaction().commit();
	}
	
	public void crearUsuario(String nombre, String correo, String contraseña, String tipo, float saldo) {
		
		db.getTransaction().begin();
		
			if(tipo.equals("Vendedor")) {
				Seller nuevoVendedor = new Seller(nombre, correo, contraseña, tipo, saldo);
				db.persist(nuevoVendedor);
				System.out.println("Vendedor guardado "+nuevoVendedor);
			}else if(tipo.equals("Comprador")) {
				Comprador nuevoComprador = new Comprador(nombre, correo, contraseña, tipo, saldo);
				db.persist(nuevoComprador);
				System.out.println("Comprador guardado "+nuevoComprador);
			}else if(tipo.equals("Administrador")) {
				Administrador nuevoAdministrador = new Administrador(nombre, correo, contraseña, tipo);
				db.persist(nuevoAdministrador);
				System.out.println("Administrador guardado "+nuevoAdministrador);
			}
			
		db.getTransaction().commit();
	}
	
	public boolean usuarioExistente(String correo) {
		if(db.find(Usuario.class, correo) != null) {
			return true;
		}else {
			return false;
		}
	}
	
	public void banearUsuario(String correo) {
		Usuario usuarioBaneado;
		
		db.getTransaction().begin();
		usuarioBaneado = db.find(Usuario.class, correo);
		usuarioBaneado.setTipo("Baneado");
		db.getTransaction().commit();
	}
	
	public boolean esUsuarioBaneado(String correo) {
		Usuario usuarioRevisar = db.find(Usuario.class, correo);
		if(usuarioRevisar != null){
			return usuarioRevisar.getTipo().equals("Baneado");
		}else {
			return false;
		}
	}
	
	public boolean contraseñaCorrecta(String correo, String contraseña) {
		if(db.find(Usuario.class, correo).getContraseña().equals(contraseña)) {
			return true;
		}else {
			return false;
		}
	}
	
	public Usuario getUsuario(String correo) {
		return db.find(Usuario.class, correo);
	}
	
	public void añadirAlCarrito(Sale compra, Comprador comprador, float precioOfrecido) {
		Carrito carritoCompra;
		
		db.getTransaction().begin();
		
		comprador = db.find(Comprador.class, comprador.getEmail());
		compra = db.find(Sale.class, compra.getSaleNumber());
		
		Contraoferta contra = new Contraoferta(precioOfrecido, compra, comprador, compra.getSeller());
		
		carritoCompra = comprador.getCarrito();
		
		if(carritoCompra == null) {
			carritoCompra = new Carrito();
			comprador.setCarrito(carritoCompra);
			carritoCompra.setComprador(comprador);
		}
		
		carritoCompra.addCompraAlmacenada(contra);
		carritoCompra.setPrecioTotal(carritoCompra.getPrecioTotal() + precioOfrecido);
		db.persist(contra);
		db.persist(carritoCompra);
		db.getTransaction().commit();
	}
	
	public void crearContraoferta(Comprador comprador) {
		Carrito carritoCompra;
		
		db.getTransaction().begin();
		
		comprador = db.find(Comprador.class, comprador.getEmail());
		carritoCompra = comprador.getCarrito();
		
		for(Contraoferta contra:carritoCompra.getComprasAlmacenadas()) {
			contra.getVendedor().getContraofertasRecibidas().add(contra);
		}
		
		db.getTransaction().commit();
	}
	//Al confirmar que se acepta la contraoferta, se activará esta funcion para eliminar todas las contraofertas 
	//del producto y la oferta original del comprador (+cuando se aplique el saldo a los usuarios también 
	//se descontará lo acordado al comprador y se añadirá al vendedor)
	
	public void aceptarContraoferta(Contraoferta pOferta, MainGUI main) {
		
		
		Sale compraDescatalogar = pOferta.getCompra();
		System.out.println(pOferta.getComprador());
		//Encuentra la oferta
		db.getTransaction().begin();
		compraDescatalogar = db.find(Sale.class, compraDescatalogar.getSaleNumber());
		//Su comprador
		Comprador compradorDeCompra = db.find(Comprador.class, pOferta.getComprador().getEmail());
		//Y su vendedor
		Seller sellerAQuienCompra = db.find(Seller.class, compraDescatalogar.getSeller().getEmail());	
		//Asigna la oferta al comprador
		compradorDeCompra.getComprasRealizadas().add(compraDescatalogar);
		System.out.println(compradorDeCompra);
		compraDescatalogar.getCompradores().add(compradorDeCompra);
		//Actualiza el saldo de los dos usuarios
		compradorDeCompra.setSaldo(compradorDeCompra.getSaldo() - pOferta.getPrecioOfrecido());
		sellerAQuienCompra.setSaldo(sellerAQuienCompra.getSaldo() + pOferta.getPrecioOfrecido());
		//Actualiza el saldo mostrado en la MainGUI
		main.actualizarSaldo(compradorDeCompra.getSaldo(), sellerAQuienCompra.getSaldo());
		//Borra las demas contraofertas
		Iterator<Contraoferta> contraofertasPosibles = sellerAQuienCompra.getContraofertasRecibidas().iterator();
		while(contraofertasPosibles.hasNext()) {
		    Contraoferta contra = contraofertasPosibles.next();
		    contra = db.find(Contraoferta.class, contra.getIdentificador());
		    
		    if(contra.getCompra().getSaleNumber().equals(compraDescatalogar.getSaleNumber())) {
		    	contraofertasPosibles.remove();
		    }
		}
		Query query = db.createQuery("DELETE FROM Contraoferta c WHERE c.oferta.saleNumber=" +compraDescatalogar.getSaleNumber());
		int contraofertasBorradas = query.executeUpdate();
		
		if(compraDescatalogar.getCant()>1) {
			compraDescatalogar.setCant(compraDescatalogar.getCant()-1);
		}else {
			compraDescatalogar.setDescatalogada(true);
		}
				
		db.getTransaction().commit();
	}
	//unai:
	public void anadirSaldo(Float saldo, Usuario usuario, MainGUI main) {
		db.getTransaction().begin();
		Float saldoActualizado = usuario.getSaldo();
		saldoActualizado += saldo;
		usuario.setSaldo(saldoActualizado);
		main.actualizarSaldo(saldoActualizado, saldoActualizado);
		db.getTransaction().commit();
	}
	public void retirarSaldo(Float saldo, Usuario usuario, MainGUI main) {
		db.getTransaction().begin();
		Float saldoActualizado = usuario.getSaldo();
		saldoActualizado -= saldo;
		usuario.setSaldo(saldoActualizado);
		main.actualizarSaldo(saldoActualizado, saldoActualizado);
		db.getTransaction().commit();
	}
	
	public void crearReporte(String correoReportado, String razonReporte, String correoReportante) {
		Usuario reportado;
		Usuario reportante;
		
		db.getTransaction().begin();
		reportado = db.find(Usuario.class, correoReportado);
		reportante = db.find(Usuario.class, correoReportante);
		Reporte nuevoReporte = new Reporte(razonReporte, new Date(), reportado, reportante);
		
		reportado.getReportes().add(nuevoReporte);
		reportante.getReportes().add(nuevoReporte);
		db.persist(nuevoReporte);
		db.getTransaction().commit();
	}
	
	public void borrarReporte(Reporte reporteBorrar) {
		
		db.getTransaction().begin();
		Query query = db.createQuery("DELETE FROM Reporte r WHERE r.numeroReporte=" + reporteBorrar.getNumeroReporte());
		int reportesBorrados = query.executeUpdate();
		db.getTransaction().commit();
	}
	
	public void crearComentario(String asunto, String descripcion, int puntuacion, Sale ventaComentada, String correoComprador) {
		Seller comentado;
		Comprador comentador;
		Sale ventaInstanciada;
		
		db.getTransaction().begin();
		ventaInstanciada = db.find(Sale.class, ventaComentada.getSaleNumber());
		comentado = db.find(Seller.class, ventaInstanciada.getSeller().getEmail());
		comentador = db.find(Comprador.class, correoComprador);
		Comentario nuevoComentario = new Comentario(asunto, descripcion, puntuacion, new Date(), comentado, comentador, ventaInstanciada);
		comentador.getComentarios().add(nuevoComentario);
		comentado.getComentarios().add(nuevoComentario);
		db.persist(nuevoComentario);
		db.getTransaction().commit();
	}

	public void borrarComentario(Comentario comentarioBorrar) {
		db.getTransaction().begin();
		Query query = db.createQuery("DELETE FROM Comentario c WHERE c.numeroComentario=" + comentarioBorrar.getNumeroComentario());
		int reportesBorrados = query.executeUpdate();
		db.getTransaction().commit();
	}
	
	public List<Comentario> getComentariosVenta(Sale venta) {
		Sale ventaComentada;
		
		ventaComentada = db.find(Sale.class, venta.getSaleNumber());
		return ventaComentada.getComentarios();
		
	};
	
	public List<Sale> getComprasRealizadasComprador(Comprador comprador){

		comprador = db.find(Comprador.class, comprador.getEmail());
		System.out.println(comprador);
		return comprador.getComprasRealizadas();
	}
	
	/**
	 * This method retrieves all the products that contain a desc text in a title
	 * 
	 * @param desc the text to search
	 * @return collection of products that contain desc in a title
	 */
	public List<Sale> getSales(String desc) {
		System.out.println(">> DataAccess: getProducts=> from= "+desc);

		List<Sale> res = new ArrayList<Sale>();	
		TypedQuery<Sale> query = db.createQuery("SELECT s FROM Sale s WHERE s.title LIKE ?1",Sale.class);   
		query.setParameter(1, "%"+desc+"%");
		
		List<Sale> sales = query.getResultList();
	 	 for (Sale sale:sales){
		   res.add(sale);
		  }
	 	return res;
	}
	
	public List<Contraoferta> getContraofertas(Usuario compradorVendedor){
		Comprador comprador;
		Seller vendedor;
		Carrito carritoComprador;
		
		compradorVendedor = db.find(Usuario.class, compradorVendedor.getEmail());
		if(compradorVendedor.getTipo().equals("Comprador")) {

		    comprador = db.find(Comprador.class, compradorVendedor.getEmail());
		    carritoComprador = comprador.getCarrito();
		    if(carritoComprador == null) {
		        return new ArrayList<Contraoferta>();
		    }
		    
		    carritoComprador = db.find(Carrito.class, carritoComprador.getIdCarrito());

		    return carritoComprador.getComprasAlmacenadas();
		}else if(compradorVendedor.getTipo().equals("Vendedor")) {
			vendedor = db.find(Seller.class, compradorVendedor.getEmail());
			System.out.println("ola");
		    return vendedor.getContraofertasRecibidas();
		}else {
			return new ArrayList<Contraoferta>();
		}
	}
	
	/**
	 * This method retrieves the products that contain a desc text in a title and the publicationDate today or before
	 * 
	 * @param desc the text to search
	 * @return collection of products that contain desc in a title
	 */
	public List<Sale> getPublishedSales(String desc, Date pubDate) {
		System.out.println(">> DataAccess: getProducts=> from= "+desc);

		List<Sale> res = new ArrayList<Sale>();	
		TypedQuery<Sale> query = db.createQuery("SELECT s FROM Sale s WHERE s.title LIKE ?1 AND s.pubDate <=?2 AND s.cant > 0 AND s.seller.tipo <> 'Baneado' AND NOT s.descatalogada",Sale.class);   
		query.setParameter(1, "%"+desc+"%");
		query.setParameter(2,pubDate);
		
		List<Sale> sales = query.getResultList();
	 	 for (Sale sale:sales){
		   res.add(sale);
		  }
	 	return res;
	}
	
	public List<Comentario> getComentarios(){
		TypedQuery<Comentario> query = db.createQuery("SELECT c FROM Comentario c", Comentario.class);
		
		return query.getResultList();
	}
	
	public List<Reporte> getReportes(){
		TypedQuery<Reporte> query = db.createQuery("SELECT r FROM Reporte r", Reporte.class);
		
		return query.getResultList();
	}
	
	public void añadirCarrito(Sale compra, Comprador comprador) {
		
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
		System.out.println("DataAccess opened => isDatabaseLocal: "+c.isDatabaseLocal());

		
	}

	public BufferedImage getFile(String fileName) {
		File file=new File(basePath+fileName);
		BufferedImage targetImg=null;
		try {
             targetImg = rescale(ImageIO.read(file));
        } catch (IOException ex) {
            //Logger.getLogger(MainAppFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
		return targetImg;

	}
	
	public BufferedImage rescale(BufferedImage originalImage)
    {
		System.out.println("rescale "+originalImage);
        BufferedImage resizedImage = new BufferedImage(baseSize, baseSize, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, baseSize, baseSize, null);
        g.dispose();
        return resizedImage;
    }
	
	
	
	public void close(){
		db.close();
		System.out.println("DataAcess closed");
	}
	
}