package businessLogic;

import java.io.File;
import java.util.Date;
import java.util.List;

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

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.awt.image.BufferedImage;
import java.awt.Image;

import gui.*;
/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  {
	  

	/**
	 * This method creates/adds a product to a seller
	 * 
	 * @param title of the product
	 * @param description of the product
	 * @param status 
	 * @param selling price
	 * @param category of a product
	 * @param publicationDate
	 * @return Sale
	 */
   @WebMethod
	public Sale createSale(String title, String description, int status, float price, Date pubDate, String sellerEmail, File file//Unai
			, int cant) throws  FileNotUploadedException, MustBeLaterThanTodayException, SaleAlreadyExistException;
	
	
	/**
	 * This method retrieves the products that contain desc
	 * 
	 * @param desc the text to search
	 * @return collection of sales that contain desc 
	 */
	@WebMethod public List<Sale> getSales(String desc);
	
	/**
	 * Borra una oferta del sistema directamente
	 * 
	 * @param la oferta a borrar
	 */
	@WebMethod public void borrarOferta(Sale oferta);
	
	/**
	 * Descataloga una oferta del mercado para que no aparezca al resto de usuarios
	 * 
	 * @param La oferta a descatalogar
	 */
	@WebMethod public void descatalogarOferta(Sale oferta);
	
	/**
	 * Este metodo crea un usuario
	 * 
	 * @param Nombre del usuario
	 * @param Correo del usuario
	 * @param Contraseña del usuario
	 * @param Tipo de usuario
	 */
	@WebMethod public void crearUsuario(String nombre, String correo, String contraseña, String tipo //Unai:
			, float saldo);
	
	/**
	 * Este metodo guarda la contraoferta realizada por el comprador en la base de datos, y borra de la misma la compra a la
	 * cual se le realiza la contraoferta.
	 * 
	 * @param La contraoferta realizada por el comprador
	 */	
	@WebMethod public void añadirAlCarrito(Sale compra, Comprador comprador, float precioOfrecido);
	/**
	 * Este metodo guarda la contraoferta realizada por el comprador en la base de datos, y borra de la misma la compra a la
	 * cual se le realiza la contraoferta.
	 * 
	 * @param La contraoferta realizada por el comprador
	 */	
	@WebMethod public void crearContraoferta(Comprador comprador);
	
	/**
	 * Este metodo devuelve una lista de contraofertas de un vendedor en especifico
	 * 
	 * @return Lista de contraofertas
	 */	
	@WebMethod public List<Contraoferta> getContraofertas(Usuario compraVendedor);
	
	//Unai: debido a mis cambios crearContraoferta no eliminará la compra original
		/**
		 * Este metodo elimina todas las contraofertas del producto que se haya vendido así como el propio producto 
		 * (+Pendiente: hacer la transacción monetaria respectiva al producto)
		 * @param main 
		 * @param la contraoferta seleccionada como aceptada por el comprador
		 */
		@WebMethod public void aceptarContraoferta(Contraoferta c, MainGUI main);
	//Unai:
		/**
		 * Este metodo retira el saldo indicado al usuario registrado
		 * @param saldo
		 */
	@WebMethod public void retirarSaldo(Float saldo, Usuario usuario, MainGUI main);
		/**
		 * Este metodo añade el saldo indicado al usuario registrado
		 * @param saldo
		 */
	@WebMethod public void anadirSaldo(Float saldo, Usuario usuario, MainGUI main);
	//
	/**
	 * Este metodo busca en la base de datos el usuario que coincida con el correo indicado
	 *  
	 * @param Correo del vendedor
	 * @param Contraseña del vendedor
	 */
	@WebMethod public Usuario getUsuario(String correo);
	
	/**
	 * Este metodo busca en la base de datos el correo prporcionado para saber si existe ya un usuario con ese correo
	 *  
	 * @param Correo del vendedor
	 * @return Si existe o no el usuario
	 */
	@WebMethod public boolean usuarioExistente(String correo);
	
	/**
	 * Este metodo busca en la base de datos el usuario que le corresponda el correo y lo banea del programa;
	 * al marcarlo como baneado sus ventas ya no seran visibles al publico (si es que tiene) y perderá el acceso a la cuenta.
	 *  
	 * @param Correo del vendedor
	 */
	@WebMethod public void banearUsuario(String correo);
	
	/**
	 * Este metodo revisa si el correo que se ha proporcionado pertenece a una cuenta baneada del sistema
	 *  
	 * @param Correo del vendedor
	 */
	@WebMethod public boolean esUsuarioBaneado(String correo);
	
	/**
	 * Este metodo busca en la base de datos el usuario que corresponda con el correo adjunto, y revisa si la contraseña coincide
	 *  
	 * @param Correo del vendedor
	 * @param Contraseña del vendedor
	 * @return Si la contraseña es correcta
	 */
	@WebMethod public boolean contraseñaCorrecta(String correo, String contraseña);
	
	/**
	 * Este metodo crea un nuevo reporte hacia un usuario y lo guarda en la base de datos
	 *  
	 * @param Correo del reportado
	 * @param Razon del reporte
	 * @param Correo del reportante
	 */
	@WebMethod public void crearReporte(String correoReportado, String razonReporte, String correoReportante);
	
	/**
	 * Este metodo toma un reporte y lo borra de la base de datos
	 *  
	 * @param Reporte a borrar
	 */
	@WebMethod public void borrarReporte(Reporte reporte);
	
	/**
	 * Este metodo crea un nuevo comentario sobre un vendedor y su producto y lo guarda
	 *  
	 * @param Una breve descripción (asunto) sobre de que va el comentario
	 * @param El propio comentario
	 * @param La puntuacion que se le otorga al vendedor, el cual es un numero del 1 al 10
	 * @param Correo de a quien va dirigido el comentario
	 * @param Correo de quien realiza el comentario
	 * @param La venta relacionada al comentario
	 */
	@WebMethod public void crearComentario(String asunto, String descripcion, int puntuacion, Sale ventaComentada, String correoComprador);
	
	/**
	 * Este metodo borra un comentario
	 *  
	 * @param El comentario a borrar
	 */
	@WebMethod public void borrarComentario(Comentario comentario);
	
	/**
	 * Este metodo busca en la base de datos todos los comentarios relacionados con la venta en la que se encuentra el usuario
	 *  
	 * @param La venta que intenta adquirir el comprador
	 * @return Devuelve una lista con los comentarios
	 */
	@WebMethod public List<Comentario> getComentariosVenta(Sale venta);
	
	/**
	 * Este metodo busca en la base de datos todas las compras realizadas por el comprador
	 *  
	 * @param El correo del comprador
	 * @return La lista de compras
	 */
	@WebMethod public List<Sale> getComprasRealizadasComprador(Comprador comprador);
	
	/**
	 * 	 * This method retrieves the products that contain a desc text in a title and the publicationDate today or before
	 * 
	 * @param desc the text to search
	 * @param pubDate the date  of the publication date
	 * @return collection of sales that contain desc and published before pubDate
	 */
	@WebMethod public List<Sale> getPublishedSales(String desc, Date pubDate);

	/**
	 * 	 Este metodo devuelve todos los comentarios hasta hoy
	 * 
	 * @return Una lista de todos los comentarios
	 */
	@WebMethod public List<Comentario> getComentarios();
	
	/**
	 * 	 Este metodo devuelve todos los reportes hasta hoy
	 * 
	 * @return Una lista de todos los reportes
	 */
	@WebMethod public List<Reporte> getReportes();
	
	/**
	 * This method calls the data access to initialize the database with some sellers and products.
	 * It is only invoked  when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod public void initializeBD();
	
		
	@WebMethod public Image downloadImage(String imageName);
	

	
}
