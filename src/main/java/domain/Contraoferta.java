package domain;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Contraoferta implements Serializable {
	@XmlID
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private int identificador;
	private float precioOfrecido;
	@ManyToOne(fetch = FetchType.EAGER)
	private Sale oferta;
	@ManyToOne
	private Comprador comprador;
	@ManyToOne
	private Seller vendedor;
	
	public Contraoferta(){
		super();
	}
		
	public Contraoferta(float precioOfrecido, Sale oferta, Comprador comprador, Seller vendedor) {
		super();

		this.precioOfrecido=precioOfrecido;
		this.oferta=oferta;
		this.comprador=comprador;
		this.vendedor=vendedor;
	}
	
	public int getIdentificador() {
		return identificador;
	}
	
	public void setIdentificador(int identificador) {
		this.identificador = identificador;
	}

	public float getPrecioOfrecido() {
		return precioOfrecido;
	}

	public void setPrecioOfrecido(float precioOfrecido) {
		this.precioOfrecido = precioOfrecido;
	}
	
	public Comprador getComprador() {
		return comprador;
	}

	public void setComprador(Comprador comprador) {
		this.comprador = comprador;
	}
	
	public Seller getVendedor() {
		return vendedor;
	}

	public void setComprador(Seller vendedor) {
		this.vendedor = vendedor;
	}
	
	public Sale getCompra() {
		return oferta;
	}

	public void setCompra(Sale oferta) {
		this.oferta = oferta;
	}
	
	public String toString(){
		return identificador+";"+precioOfrecido+";"+oferta;  
	}




	
}
