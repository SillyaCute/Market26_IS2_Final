package domain;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Carrito {
	@XmlID
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer idCarrito;
	
	private float precioTotal;
	@OneToOne
	private Comprador comprador;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Contraoferta> contraofertasAlmacenadas = new ArrayList<Contraoferta>();
	
	public Carrito() {
		super();
		this.precioTotal = 0;
	}
	
	public Integer getIdCarrito() {
		return this.idCarrito;
	}
	
	public void setIdCarrito(Integer idCarrito) {
		this.idCarrito = idCarrito;
	}
	
	public float getPrecioTotal() {
		return precioTotal;
	}
	
	public void setPrecioTotal(float precioTotal) {
		this.precioTotal = precioTotal;
	}
	
	public Comprador getComprador() {
		return comprador;
	}
	
	public void setComprador(Comprador comprador) {
		this.comprador = comprador;
	}
	
	public List<Contraoferta> getComprasAlmacenadas(){
		return contraofertasAlmacenadas;
	}
	
	public void addCompraAlmacenada(Contraoferta contra) {
		this.contraofertasAlmacenadas.add(contra);
	}
}