package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;

import businessLogic.BLFacade;
import domain.Comprador;
import domain.Contraoferta;
import domain.Sale;
import java.awt.Color;

public class ContraOfertaGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JLabel lblPrecioOriginal = null;
	private JLabel lblPrecioOfrecido = null;
	private JLabel lblCompra = null;
	private JLabel lblMostrarError = null;
	private JLabel lblMostrarExito = null;
	
	private JTextField textPrecioOriginal;
	private JTextField textPrecioOfrecido;
	private JTextField textCompra;
	
	private JButton btnConfirmarContraoferta = null;
	
	public ContraOfertaGUI(Sale compra, Comprador comprador) {
		getContentPane().setLayout(null);
		this.setSize(new Dimension(400, 265));
		
		//Labels
		lblPrecioOriginal = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ContraOferta.PrecioOriginal"));
		lblPrecioOriginal.setBounds(10, 59, 147, 18);
		
		lblPrecioOfrecido = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ContraOferta.PrecioOfrecido"));
		lblPrecioOfrecido.setBounds(10, 87, 147, 18);
		
		lblCompra = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ContraOferta.Compra"));
		lblCompra.setBounds(10, 31, 147, 18);
		
		lblMostrarError = new JLabel();
		lblMostrarError.setForeground(Color.RED);
		lblMostrarError.setBounds(21, 137, 337, 18);
		
		lblMostrarExito = new JLabel();
		lblMostrarExito.setForeground(Color.GREEN);
		lblMostrarExito.setBounds(21, 142, 337, 20);
		
		//Text Fields
		textPrecioOfrecido = new JTextField();
		textPrecioOfrecido.setColumns(10);
		textPrecioOfrecido.setBounds(181, 87, 154, 18);
		
		textCompra = new JTextField();
		textCompra.setText(compra.getTitle());
		textCompra.setEditable(false);
		textCompra.setColumns(10);
		textCompra.setBounds(181, 31, 154, 18);
		
		textPrecioOriginal = new JTextField();
		textPrecioOriginal.setText(compra.getPrice() +  "");
		textPrecioOriginal.setEditable(false);
		textPrecioOriginal.setBounds(181, 59, 154, 18);
		textPrecioOriginal.setColumns(10);
		
		//Buttons
		btnConfirmarContraoferta = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ContraOferta.AñadirAlCarrito"));
		btnConfirmarContraoferta.setBounds(120, 165, 125, 35);
		btnConfirmarContraoferta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BLFacade facade = MainGUI.getBusinessLogic();
				String error = check_fields_Errors();
				
				if(error != null) {
					lblMostrarError.setText(error);
				}else {
					lblMostrarError.setText(null);
					facade.añadirAlCarrito(compra, comprador, Float.parseFloat(textPrecioOfrecido.getText()));
					lblMostrarExito.setText(ResourceBundle.getBundle("Etiquetas").getString("ContraOferta.Exito"));
					new Timer(3000, new ActionListener() {
					    @Override
					    public void actionPerformed(ActionEvent e) {
					        dispose();
					    }
					}) {{
					    setRepeats(false);
					}}.start();
				}
			}
		});
		
		//Content Pane
		getContentPane().add(lblPrecioOriginal);
		getContentPane().add(lblPrecioOfrecido);
		getContentPane().add(lblCompra);
		getContentPane().add(lblMostrarError);
		getContentPane().add(lblMostrarExito);
		getContentPane().add(textPrecioOfrecido);
		getContentPane().add(textCompra);
		getContentPane().add(textPrecioOriginal);
		getContentPane().add(btnConfirmarContraoferta);
	}
	
	private String check_fields_Errors() {
		if(textPrecioOfrecido.getText().length() == 0) {
			return ResourceBundle.getBundle("Etiquetas").getString("ContraOferta.ErrorVacio");
		}else {
			float price = Float.parseFloat(textPrecioOfrecido.getText());
			if(price <= 0) {
				return ResourceBundle.getBundle("Etiquetas").getString("ContraOferta.ErrorVacio");
			}else if(price > Float.parseFloat(textPrecioOriginal.getText())){
				return ResourceBundle.getBundle("Etiquetas").getString("ContraOferta.ErrorSobrePrecio");
			}else {
				return null;
			}
		}
	}

}
