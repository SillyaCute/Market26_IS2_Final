package gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;

import businessLogic.BLFacade;
import domain.Seller;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.Color;

public class HacerLoginGUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblCorreo = null;
	private JLabel lblContraseña = null;
	private JLabel lblMostrarError = null;
	private JLabel lblMostrarExito = null;
	
	private JTextField textCorreo;
	private JTextField textContraseña;
	
	private JButton btnHacerLogin = null;

	public HacerLoginGUI(MainGUI mainGUI) {
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(400, 250));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.CreateProduct"));
		
		setTitle("Register");
		getContentPane().setLayout(null);
		
		//Labels
		lblCorreo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Registrarse.Correo"));
		lblCorreo.setBounds(10, 22, 92, 20);
		
		lblContraseña = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Registrarse.Contrasena"));
		lblContraseña.setBounds(10, 52, 92, 20);
		
		lblMostrarError = new JLabel();
		lblMostrarError.setForeground(Color.RED);
		lblMostrarError.setBounds(25, 112, 337, 20);
		
		lblMostrarExito = new JLabel();
		lblMostrarExito.setForeground(new Color(71, 176, 26));
		lblMostrarExito.setBounds(25, 112, 337, 20);
		
		//Text Fields
		textCorreo = new JTextField();
		textCorreo.setText((String) null);
		textCorreo.setBounds(112, 23, 250, 20);
		textCorreo.setColumns(10);
		
		textContraseña = new JTextField();
		textContraseña.setText((String) null);
		textContraseña.setColumns(10);
		textContraseña.setBounds(112, 53, 250, 20);
		
		//Buttons
		btnHacerLogin = new JButton(ResourceBundle.getBundle("Etiquetas").getString("HacerLogin.HacerLogin"));
		btnHacerLogin.setBounds(60, 142, 239, 31);
		btnHacerLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				String error = check_fields_Errors();
				
				if(error != null) {
					lblMostrarError.setText(error);
				}else {
					lblMostrarError.setText(null);
					mainGUI.setUsuario(facade.getUsuario(textCorreo.getText()));
					lblMostrarExito.setText(ResourceBundle.getBundle("Etiquetas").getString("HacerLogin.Exito"));
					
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
		
		getContentPane().add(lblCorreo);
		getContentPane().add(lblContraseña);
		getContentPane().add(lblMostrarError);
		getContentPane().add(lblMostrarExito);
		getContentPane().add(textCorreo);
		getContentPane().add(textContraseña);
		getContentPane().add(btnHacerLogin);
		
	}
	
	private String check_fields_Errors() {
		//Si hay algun hueco sin rellenar
		if ((textCorreo.getText().length()==0) || (textContraseña.getText().length()==0)) {
			return ResourceBundle.getBundle("Etiquetas").getString("HacerLogin.ErrorVacio");
		} else {
			BLFacade facade = MainGUI.getBusinessLogic();
			//Si el usuario no existe
			if (!facade.usuarioExistente(textCorreo.getText())) {
				return ResourceBundle.getBundle("Etiquetas").getString("HacerLogin.ErrorGmail");
			//Si el usuario esta baneado
			}else if(facade.esUsuarioBaneado(textCorreo.getText())) {
				return ResourceBundle.getBundle("Etiquetas").getString("Registrarse.ErrorBaneado");
			//Si la contraseña es incorrecta
			}else {
				if(!facade.contraseñaCorrecta(textCorreo.getText(), textContraseña.getText())) {
					return ResourceBundle.getBundle("Etiquetas").getString("HacerLogin.ErrorContraseña");
				}else {
					return null;
				}
			}
		}
	}
}
