package businessLogic;
import java.io.File;
import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import dataAccess.DataAccess;
import domain.Comentario;
import domain.Comprador;
import domain.Contraoferta;
import domain.Reporte;
import domain.Sale;
import domain.Seller;
import domain.Usuario;
import exceptions.FileNotUploadedException;
import exceptions.MustBeLaterThanTodayException;
import exceptions.SaleAlreadyExistException;
import gui.MainGUI;

import java.awt.image.BufferedImage;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;


/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {
	 private static final int baseSize = 160;

		private static final String basePath="src/main/resources/images/";
	DataAccess dbManager;

	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		dbManager=new DataAccess();		
	}
	
    public BLFacadeImplementation(DataAccess da)  {
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		dbManager=da;		
	}
    

	/**
	 * {@inheritDoc}
	 */
   @WebMethod
	public Sale createSale(String title, String description,int status, float price, Date pubDate, String sellerEmail, File file//Unai:
			, int cant) throws  FileNotUploadedException, MustBeLaterThanTodayException, SaleAlreadyExistException {
		dbManager.open();
		Sale product=dbManager.createSale(title, description, status, price, pubDate, sellerEmail, file, cant);		
		dbManager.close();
		return product;
   };
   
   /**
	 * {@inheritDoc}
	 */
@WebMethod
	public void borrarOferta(Sale oferta) {
		dbManager.open();
		dbManager.borrarOferta(oferta);		
		dbManager.close();
	};
	
	/**
	 * {@inheritDoc}
	 */
@WebMethod
	public void descatalogarOferta(Sale oferta) {
		dbManager.open();
		dbManager.descatalogarOferta(oferta);		
		dbManager.close();
	};
   
	/**
	 * {@inheritDoc}
	 */
  @WebMethod
	public void crearUsuario(String nombre, String correo, String contraseña, String tipo //Unai: 
		, float saldo) {
		dbManager.open();
		dbManager.crearUsuario(nombre, correo, contraseña, tipo, saldo);		
		dbManager.close();
  };
  
	/**
	 * {@inheritDoc}
	 */
@WebMethod
	public Usuario getUsuario(String correo) {
		dbManager.open();
		Usuario usuarioDB = dbManager.getUsuario(correo);		
		dbManager.close();
		return usuarioDB;
	};

	/**
	 * {@inheritDoc}
	 */
@WebMethod
	public boolean usuarioExistente(String correo) {
		dbManager.open();
		Boolean existe = dbManager.usuarioExistente(correo);		
		dbManager.close();
		return existe;
	}

/**
 * {@inheritDoc}
 */
@WebMethod
public void banearUsuario(String correo) {
	dbManager.open();
	dbManager.banearUsuario(correo);		
	dbManager.close();
}

/**
 * {@inheritDoc}
 */
@WebMethod
public boolean esUsuarioBaneado(String correo){
	dbManager.open();
	boolean estaBaneado = dbManager.esUsuarioBaneado(correo);		
	dbManager.close();
	return estaBaneado;
}

/**
 * {@inheritDoc}
 */
@WebMethod
	public boolean contraseñaCorrecta(String correo, String contraseña) {
	dbManager.open();
	Boolean correcta = dbManager.contraseñaCorrecta(correo, contraseña);		
	dbManager.close();
	return correcta;
}

/**
 * {@inheritDoc}
 */
@WebMethod
	public void añadirAlCarrito(Sale compra, Comprador comprador, float precioOfrecido)  {
		dbManager.open();
		dbManager.añadirAlCarrito(compra, comprador, precioOfrecido);		
		dbManager.close();
};

/**
 * {@inheritDoc}
 */
@WebMethod
	public void crearContraoferta(Comprador comprador) {
		dbManager.open();
		dbManager.crearContraoferta(comprador);		
		dbManager.close();
};

/**
 * {@inheritDoc}
 */
@WebMethod
	public void crearReporte(String correoReportado, String razonReporte, String correoReportante) {
		dbManager.open();
		dbManager.crearReporte(correoReportado, razonReporte, correoReportante);		
		dbManager.close();
};

/**
 * {@inheritDoc}
 */
@WebMethod
	public void borrarReporte(Reporte reporte){
		dbManager.open();
		dbManager.borrarReporte(reporte);		
		dbManager.close();
};

/**
 * {@inheritDoc}
 */
@WebMethod
	public void crearComentario(String asunto, String descripcion, int puntuacion, Sale ventaComentada, String correoComprador) {
		dbManager.open();
		dbManager.crearComentario(asunto, descripcion, puntuacion, ventaComentada, correoComprador);		
		dbManager.close();
};

/**
 * {@inheritDoc}
 */
@WebMethod
	public void borrarComentario(Comentario comentario) {
		dbManager.open();
		dbManager.borrarComentario(comentario);		
		dbManager.close();
};



/**
 * {@inheritDoc}
 */
@WebMethod
	public List<Comentario> getComentariosVenta(Sale venta) {
		dbManager.open();
		List<Comentario> comentarios = dbManager.getComentariosVenta(venta);		
		dbManager.close();
		return comentarios;
};

/**
 * {@inheritDoc}
 */
@WebMethod
public List<Contraoferta> getContraofertas(Usuario compraVendedor){
	dbManager.open();
	List<Contraoferta> contras = dbManager.getContraofertas(compraVendedor);		
	dbManager.close();
	return contras;
};

/**
 * {@inheritDoc}
 */
//Unai:
@WebMethod
public void aceptarContraoferta(Contraoferta c, MainGUI main) {
	dbManager.open();
	dbManager.aceptarContraoferta(c, main);		
	dbManager.close();
};
//Unai:
@WebMethod
public void anadirSaldo(Float saldo, Usuario usuario, MainGUI main) {
	dbManager.open();
	dbManager.anadirSaldo(saldo, usuario, main);		
	dbManager.close();
};
@WebMethod
public void retirarSaldo(Float saldo, Usuario usuario, MainGUI main) {
	dbManager.open();
	dbManager.retirarSaldo(saldo, usuario, main);		
	dbManager.close();
};
//
	
   /**
    * {@inheritDoc}
    */
	@WebMethod 
	public List<Sale> getSales(String desc){
		dbManager.open();
		List<Sale>  rides=dbManager.getSales(desc);
		dbManager.close();
		return rides;
	}
	
	/**
	    * {@inheritDoc}
	    */
		@WebMethod 
		public List<Sale> getComprasRealizadasComprador(Comprador comprador){
			dbManager.open();
			List<Sale>  compras=dbManager.getComprasRealizadasComprador(comprador);
			dbManager.close();
			return compras;
		}
	
	
	/**
	    * {@inheritDoc}
	    */
		@WebMethod 
		public List<Sale> getPublishedSales(String desc, Date pubDate) {
			dbManager.open();
			List<Sale>  rides=dbManager.getPublishedSales(desc,pubDate);
			dbManager.close();
			return rides;
		}
		
	/**
		* {@inheritDoc}
		*/
		@WebMethod 
		public List<Comentario> getComentarios() {
			dbManager.open();
			List<Comentario>  comentarios=dbManager.getComentarios();
			dbManager.close();
			return comentarios;
		}
		
	/**
		* {@inheritDoc}
		*/
		@WebMethod 
		public List<Reporte> getReportes() {
			dbManager.open();
			List<Reporte>  reportes=dbManager.getReportes();
			dbManager.close();
			return reportes;
		}
		
	/**
	    * {@inheritDoc}
	    */
	@WebMethod public BufferedImage getFile(String fileName) {
		return dbManager.getFile(fileName);
	}

    
	public void close() {
		DataAccess dB4oManager=new DataAccess();
		dB4oManager.close();

	}

	/**
	 * {@inheritDoc}
	 */
    @WebMethod	
	 public void initializeBD(){
    	dbManager.open();
		dbManager.initializeDB();
		dbManager.close();
	}
    /**
	 * {@inheritDoc}
	 */
    @WebMethod public Image downloadImage(String imageName) {
        File image = new File(basePath+imageName);
        try {
            return ImageIO.read(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    
}

