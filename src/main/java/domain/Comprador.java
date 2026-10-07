package domain;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

@Entity
@DiscriminatorValue(value="Comprador")
public class Comprador extends Usuario {

	private static final long serialVersionUID = 1L;
	@ManyToMany
	protected List<Sale> comprasRealizadas=new ArrayList<Sale>();
	
	@OneToMany
	private List<Contraoferta> contraofertasRealizadas = new ArrayList<Contraoferta>();
	
	@OneToOne(mappedBy = "comprador")
	private Carrito carritoCompra;

	public Comprador() {
        super();
    }

    public Comprador(String name, String email) {
        super(name, email);
    }
    public Comprador(String name, String email, String contraseña) {
        super(name, email, contraseña);
    }
    
    public Comprador(String name, String email, String contraseña, String tipo) {
        super(name, email, contraseña, tipo);
    }
    
    public Comprador(String name, String email, String contraseña, String tipo, float saldo) {
        super(name, email, contraseña, tipo, saldo);
    }
    
    public List<Sale> getComprasRealizadas() {
    	return this.comprasRealizadas;
    }
    
    public void addComprasRealizadas(Sale sale){
    	this.comprasRealizadas.add(sale);
    }
    
    public Carrito getCarrito() {
    	return carritoCompra;
    }
    
    public void setCarrito(Carrito carritoCarritez) {
    	this.carritoCompra = carritoCarritez;
    }
    
    public List<Contraoferta> getContraofertasRealizadas(){
    	return contraofertasRealizadas;
    }

    @Override
    public String toString() {
    	return "correo: "+ this.email + " de " + this.name + "con compras: " + this.comprasRealizadas;
    }
}
