package domain;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

@Entity
@DiscriminatorValue(value="Vendedor")
public class Seller extends Usuario {

	private static final long serialVersionUID = 1L;
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	protected List<Sale> sales=new ArrayList<Sale>();
	@OneToMany(fetch=FetchType.EAGER)
	private List<Contraoferta> contraofertasRecibidas = new ArrayList<Contraoferta>();

	public Seller() {
        super();
    }
	
	public Seller(String email) {
        super(email);
    }

    public Seller(String email, String name) {
        super(email, name);
    }
	
    public Seller(String name, String correo, String contraseña) {
        super(name,correo,contraseña);
    }
    
    public Seller(String name, String email, String contraseña, String tipo) {
        super(name, email, contraseña, tipo);
    }
    
    public Seller(String name, String email, String contraseña, String tipo, float saldo) {
        super(name, email, contraseña, tipo, saldo);
    }

    @Override
	public String toString(){
    	return "correo: "+ this.email + " de " + this.name + " con ventas: " + sales;
	}
    
    public List<Contraoferta> getContraofertasRecibidas(){
    	return contraofertasRecibidas;
    }
    
    public List<Sale> getSales() {
    	return sales;
    }
	
	/**
	 * This method creates/adds a sale to a seller
	 * 
	 * @param title of the sale
	 * @param description of the sale
	 * @param status 
	 * @param selling price
	 * @param publicationDate
	 * @return Sale
	 */
	public Sale addSale(String title, String description, int status, float price,  Date pubDate, File file, //Unai:
			int cant)  {//
		
		Sale sale=new Sale(title, description, status, price,  pubDate, file, this, cant);
        sales.add(sale);
        return sale;
	}
	
	/**
	 * This method checks if the ride already exists for that driver
	 * 
	 * @param from the origin location 
	 * @param to the destination location 
	 * @param date the date of the ride 
	 * @return true if the ride exists and false in other case
	 */
	public boolean doesSaleExist(String title)  {	
		for (Sale s:sales)
			if (s != null && s.getTitle() != null && s.getTitle().compareTo(title)==0 )
			 return true;
		return false;
	}
		
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Seller other = (Seller) obj;
		if (email != other.email)
			return false;
		return true;
	}

	
}
