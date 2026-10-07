package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import domain.Administrador;
import domain.Comprador;
import domain.Seller;
import domain.Usuario;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;


public class MainGUI extends JFrame {
	
    private Usuario usuarioMain;
    private Seller vendedorMain;
    private Comprador compradorMain;
    private Administrador adminMain;
	private static final long serialVersionUID = 1L;

	private JPanel contentPane = null;
	private JPanel panel;
	
	protected JLabel lblSelectOption;
	private JLabel lblPrintSaldo;
	private JLabel lblSaldo;
	
	private JButton buttonCreateQuery = null;
	private JButton buttonQueryQueries = null;
	private JButton buttonRegistrarse = null;
	private JButton buttonHacerLogin = null;
	private JButton buttonAceptarOferta = null;;
	private JButton buttonVerOfertasAceptadas = null;
	private JButton buttonReportarUsuario = null;
	private JButton buttonGestionarReportes = null;
	private JButton buttonGestionarComentarios = null;
	private JButton buttonGestionarOfertas = null;
	private JButton buttonComprasRealizadas = null;
	private JButton buttonGestionarSaldo = new JButton();
	
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private final ButtonGroup buttonGroup = new ButtonGroup();

    private static BLFacade appFacadeInterface;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	 
	public static void setBussinessLogic (BLFacade facade){
		appFacadeInterface=facade;
	}

	public MainGUI(String mail) {
		super();

		this.usuarioMain = new Usuario("Sin Registrar", "Usuario");
		
		//Content Pane
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.menu);
		contentPane.setLayout(null);
		this.setSize(614, 475);
		
		//Labels
		lblSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		lblSelectOption.setBounds(152, 0, 294, 67);
		lblSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSelectOption.setForeground(Color.BLACK);
		lblSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblSaldo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MostrarSaldo"));
		lblSaldo.setForeground(Color.BLACK);
		lblSaldo.setHorizontalAlignment(SwingConstants.CENTER);
		lblSaldo.setBounds(456, 27, 80, 14);
		
		lblPrintSaldo = new JLabel();
		lblPrintSaldo.setBounds(546, 27, 46, 14);
		if(vendedorMain != null) {
			lblPrintSaldo.setText(""+vendedorMain.getSaldo());
		}else if(compradorMain != null) {
			lblPrintSaldo.setText(""+compradorMain.getSaldo());
		}else {
			lblPrintSaldo.setText("0");
		}
		
		//Radio Buttons
		rdbtnNewRadioButton = new JRadioButton("English");
		rdbtnNewRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("en"));
				paintAgain();				}
		});
		buttonGroup.add(rdbtnNewRadioButton);
		
		rdbtnNewRadioButton_1 = new JRadioButton("Euskara");
		rdbtnNewRadioButton_1.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Locale.setDefault(new Locale("eus"));
				paintAgain();				}
		});
		buttonGroup.add(rdbtnNewRadioButton_1);
		
		rdbtnNewRadioButton_2 = new JRadioButton("Castellano");
		rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("es"));
				paintAgain();
			}
		});
		buttonGroup.add(rdbtnNewRadioButton_2);
	
		panel = new JPanel();
		panel.setBounds(152, 400, 301, 31);
		panel.add(rdbtnNewRadioButton_1);
		panel.add(rdbtnNewRadioButton_2);
		panel.add(rdbtnNewRadioButton);
		
		//Buttons
		buttonCreateQuery = new JButton();
		buttonCreateQuery.setBounds(301, 169, 301, 83);
		buttonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.CreateSale"));
		buttonCreateQuery.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new CreateSaleGUI(vendedorMain.getEmail());
				a.setVisible(true);
			}
		});
		
		buttonQueryQueries = new JButton();
		buttonQueryQueries.setBounds(0, 169, 301, 83);
		buttonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QuerySales"));
		buttonQueryQueries.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new QuerySalesGUI();

				a.setVisible(true);
			}
		});
		
		buttonRegistrarse = new JButton();
		buttonRegistrarse.setBounds(0, 74, 301, 84);
		buttonRegistrarse.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Registrarse"));
		buttonRegistrarse.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new RegisterGUI();
				a.setVisible(true);
			}
		});
		
		buttonHacerLogin = new JButton();
		buttonHacerLogin.setBounds(301, 74, 301, 84);
		buttonHacerLogin.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.HacerLogin"));
		buttonHacerLogin.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new HacerLoginGUI(MainGUI.this);
				a.setVisible(true);
			}
		});
		
		buttonAceptarOferta = new JButton();
		buttonAceptarOferta.setBounds(301, 169, 301, 83);
		buttonAceptarOferta.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.AceptarOferta"));
		buttonAceptarOferta.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new AceptarOfertaGUI(compradorMain);
				a.setVisible(true);
			}
		});
		
		buttonVerOfertasAceptadas = new JButton();
		buttonVerOfertasAceptadas.setBounds(152, 263, 301, 84);
		buttonVerOfertasAceptadas.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.VerOfertasAceptadas"));
		buttonVerOfertasAceptadas.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new VerOfertasAceptadasGUI(vendedorMain, MainGUI.this);
				a.setVisible(true);
			}
		});
		
		buttonReportarUsuario = new JButton();
		buttonReportarUsuario.setBounds(152, 358, 301, 31);
		buttonReportarUsuario.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.ReportarUsuario"));
		buttonReportarUsuario.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if(vendedorMain != null) {
					JFrame a = new ReportarUsuarioGUI(vendedorMain);
					a.setVisible(true);
				}else if(compradorMain != null) {
					JFrame a = new ReportarUsuarioGUI(compradorMain);
					a.setVisible(true);
				}
			}
		});
		
		buttonComprasRealizadas = new JButton();
		buttonComprasRealizadas.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.ComprasRealizadas"));
		buttonComprasRealizadas.setBounds(152, 263, 301, 84);
		buttonComprasRealizadas.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new ComprasRealizadasGUI(compradorMain);
				a.setVisible(true);
			}
		});
		
		buttonGestionarReportes = new JButton();
		buttonGestionarReportes.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.GestionarReportes"));
		buttonGestionarReportes.setBounds(0, 169, 301, 83);
		buttonGestionarReportes.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new GestionarReportesGUI();
				a.setVisible(true);
			}
		});
		
		buttonGestionarComentarios = new JButton();
		buttonGestionarComentarios.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.GestionarComentarios"));
		buttonGestionarComentarios.setBounds(301, 169, 301, 83);
		buttonGestionarComentarios.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new GestionarComentariosGUI();
				a.setVisible(true);
			}
		});
		
		buttonGestionarOfertas = new JButton();
		buttonGestionarOfertas.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.GestionarOfertas"));
		buttonGestionarOfertas.setBounds(152, 263, 301, 84);
		buttonGestionarOfertas.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new GestionarOfertasGUI();
				a.setVisible(true);
			}
		});

		buttonGestionarSaldo = new JButton();
		buttonGestionarSaldo.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.GestionarSaldo"));
		buttonGestionarSaldo.setBounds(10, 23, 165, 23);
		buttonGestionarSaldo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if(vendedorMain==null) {
					JFrame a = new GestionarSaldoGUI(compradorMain, MainGUI.this);
					a.setVisible(true);
				}else if(compradorMain==null) {
					JFrame a = new GestionarSaldoGUI(vendedorMain, MainGUI.this);
					a.setVisible(true);
				}
			}
		});
		//
		
		//Añadir todo al content pane
		contentPane.add(lblSelectOption);
		contentPane.add(lblSaldo);
		contentPane.add(lblPrintSaldo);
		contentPane.add(buttonHacerLogin);
		contentPane.add(buttonRegistrarse);
		contentPane.add(buttonCreateQuery);
		contentPane.add(buttonQueryQueries);
		contentPane.add(buttonAceptarOferta);
		contentPane.add(buttonVerOfertasAceptadas);
		contentPane.add(buttonReportarUsuario);
		contentPane.add(buttonComprasRealizadas);
		contentPane.add(buttonGestionarReportes);
		contentPane.add(buttonGestionarComentarios);
		contentPane.add(buttonGestionarOfertas);
		contentPane.add(buttonGestionarSaldo);
		contentPane.add(panel);
		
		//Inicia con todo deshabilitado
		if(usuarioMain.getTipo() == "Usuario") {
			buttonCreateQuery.setEnabled(false);
			buttonCreateQuery.setVisible(false);
			buttonQueryQueries.setEnabled(false);
			buttonQueryQueries.setVisible(false);
			buttonHacerLogin.setEnabled(true);
			buttonHacerLogin.setVisible(true);
			buttonRegistrarse.setEnabled(true);
			buttonRegistrarse.setVisible(true);
			buttonAceptarOferta.setEnabled(false);
			buttonAceptarOferta.setVisible(false);
			buttonVerOfertasAceptadas.setEnabled(false);
			buttonVerOfertasAceptadas.setVisible(false);
			buttonReportarUsuario.setEnabled(false);
			buttonReportarUsuario.setVisible(false);
			buttonComprasRealizadas.setEnabled(false);
			buttonComprasRealizadas.setVisible(false);
			buttonGestionarReportes.setEnabled(false);
			buttonGestionarReportes.setVisible(false);
			buttonGestionarComentarios.setEnabled(false);
			buttonGestionarComentarios.setVisible(false);
			buttonGestionarOfertas.setEnabled(false);
			buttonGestionarOfertas.setVisible(false);
			buttonGestionarSaldo.setEnabled(false);
			buttonGestionarSaldo.setVisible(false);
		}
		
		
		setContentPane(contentPane);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(1);
			}
		});
	}

	private void paintAgain() {
		lblSelectOption.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		buttonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QuerySales"));
		buttonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.CreateSale"));
		buttonHacerLogin.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.HacerLogin"));
		buttonRegistrarse.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Registrarse"));
		buttonAceptarOferta.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.AceptarOferta"));
		buttonVerOfertasAceptadas.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.VerOfertasAceptadas"));
		buttonReportarUsuario.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.ReportarUsuario"));
		buttonComprasRealizadas.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.ComprasRealizadas"));
		buttonGestionarReportes.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.GestionarReportes"));
		buttonGestionarComentarios.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.GestionarComentarios"));
		buttonGestionarOfertas.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.GestionarOfertas"));
		buttonGestionarSaldo.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.GestionarSaldo"));
		
		if(this.usuarioMain != null) {
			this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle")+ ": "+usuarioMain.getEmail());
			lblPrintSaldo.setText("0.0");
		}else if(this.vendedorMain != null) {
			this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle")+ ": "+vendedorMain.getEmail());
			lblPrintSaldo.setText(""+vendedorMain.getSaldo());
		}else if(this.compradorMain != null) {
			this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle")+ ": "+compradorMain.getEmail());
			lblPrintSaldo.setText(""+compradorMain.getSaldo());
		}else if(this.adminMain != null) {
			this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle")+ ": "+adminMain.getEmail());
			lblPrintSaldo.setText("N/A");
		}
	}
	
	//Actualiza el label de saldo que se muestra arriba a la derecha de la GUI
	public void actualizarSaldo(float nuevoSaldoComprador, float nuevoSaldoVendedor) {
		if(compradorMain != null) {
			this.lblPrintSaldo.setText(""+nuevoSaldoComprador);
		}else {
			this.lblPrintSaldo.setText(""+nuevoSaldoVendedor);
		}
	}
	
	//Sirve para que otras GUI puedan cambiar el usuario que se usa en la MainGUI y actualicen que botones pueden ser pulsados
	public void setUsuario(Usuario usuarioImpuesto) {

	    if(usuarioImpuesto.getTipo().equals("Usuario")) {
	    	this.usuarioMain = (Usuario) usuarioImpuesto;
	    	this.vendedorMain = null;
	    	this.compradorMain = null;
	    	this.adminMain = null;
			buttonCreateQuery.setEnabled(false);
			buttonCreateQuery.setVisible(false);
			buttonQueryQueries.setEnabled(false);
			buttonQueryQueries.setVisible(false);
			buttonHacerLogin.setEnabled(true);
			buttonHacerLogin.setVisible(true);
			buttonRegistrarse.setEnabled(true);
			buttonRegistrarse.setVisible(true);
			buttonAceptarOferta.setEnabled(false);
			buttonAceptarOferta.setVisible(false);
			buttonVerOfertasAceptadas.setEnabled(false);
			buttonVerOfertasAceptadas.setVisible(false);
			buttonReportarUsuario.setEnabled(false);
			buttonReportarUsuario.setVisible(false);
			buttonComprasRealizadas.setEnabled(false);
			buttonComprasRealizadas.setVisible(false);
			buttonGestionarReportes.setEnabled(false);
			buttonGestionarReportes.setVisible(false);
			buttonGestionarComentarios.setEnabled(false);
			buttonGestionarComentarios.setVisible(false);
			buttonGestionarOfertas.setEnabled(false);
			buttonGestionarOfertas.setVisible(false);
			buttonGestionarSaldo.setEnabled(false);
			buttonGestionarSaldo.setVisible(false);
			
		}else if(usuarioImpuesto.getTipo().equals("Vendedor")){
			this.vendedorMain = (Seller) usuarioImpuesto;
			this.compradorMain = null;
			this.usuarioMain = null;
			this.adminMain = null;
			buttonCreateQuery.setEnabled(true);
			buttonCreateQuery.setVisible(true);
			buttonQueryQueries.setEnabled(true);
			buttonQueryQueries.setVisible(true);
			buttonHacerLogin.setEnabled(true);
			buttonHacerLogin.setVisible(true);
			buttonRegistrarse.setEnabled(true);
			buttonRegistrarse.setVisible(true);
			buttonAceptarOferta.setEnabled(false);
			buttonAceptarOferta.setVisible(false);
			buttonVerOfertasAceptadas.setEnabled(true);
			buttonVerOfertasAceptadas.setVisible(true);
			buttonReportarUsuario.setEnabled(true);
			buttonReportarUsuario.setVisible(true);
			buttonComprasRealizadas.setEnabled(false);
			buttonComprasRealizadas.setVisible(false);
			buttonGestionarReportes.setEnabled(false);
			buttonGestionarReportes.setVisible(false);
			buttonGestionarComentarios.setEnabled(false);
			buttonGestionarComentarios.setVisible(false);
			buttonGestionarOfertas.setEnabled(false);
			buttonGestionarOfertas.setVisible(false);
			buttonGestionarSaldo.setEnabled(true);
			buttonGestionarSaldo.setVisible(true);
			
		}else if(usuarioImpuesto.getTipo().equals("Comprador")){
			this.compradorMain = (Comprador) usuarioImpuesto;
			this.vendedorMain = null;
			this.usuarioMain = null;
			this.adminMain = null;
			buttonCreateQuery.setEnabled(false);
			buttonCreateQuery.setVisible(false);
			buttonQueryQueries.setEnabled(true);
			buttonQueryQueries.setVisible(true);
			buttonHacerLogin.setEnabled(true);
			buttonHacerLogin.setVisible(true);
			buttonRegistrarse.setEnabled(true);
			buttonRegistrarse.setVisible(true);
			buttonAceptarOferta.setEnabled(true);
			buttonAceptarOferta.setVisible(true);
			buttonVerOfertasAceptadas.setEnabled(false);
			buttonVerOfertasAceptadas.setVisible(false);
			buttonReportarUsuario.setEnabled(true);
			buttonReportarUsuario.setVisible(true);
			buttonComprasRealizadas.setEnabled(true);
			buttonComprasRealizadas.setVisible(true);
			buttonGestionarReportes.setEnabled(false);
			buttonGestionarReportes.setVisible(false);
			buttonGestionarComentarios.setEnabled(false);
			buttonGestionarComentarios.setVisible(false);
			buttonGestionarOfertas.setEnabled(false);
			buttonGestionarOfertas.setVisible(false);
			buttonGestionarSaldo.setEnabled(true);
			buttonGestionarSaldo.setVisible(true);
			
		}else if(usuarioImpuesto.getTipo().equals("Administrador")){
			this.adminMain = (Administrador) usuarioImpuesto;
			this.compradorMain = null;
			this.vendedorMain = null;
			this.usuarioMain = null;
			buttonCreateQuery.setEnabled(false);
			buttonCreateQuery.setVisible(false);
			buttonQueryQueries.setEnabled(false);
			buttonQueryQueries.setVisible(false);
			buttonHacerLogin.setEnabled(true);
			buttonHacerLogin.setVisible(true);
			buttonRegistrarse.setEnabled(true);
			buttonRegistrarse.setVisible(true);
			buttonAceptarOferta.setEnabled(false);
			buttonAceptarOferta.setVisible(false);
			buttonVerOfertasAceptadas.setEnabled(false);
			buttonVerOfertasAceptadas.setVisible(false);
			buttonReportarUsuario.setEnabled(false);
			buttonReportarUsuario.setVisible(false);
			buttonComprasRealizadas.setEnabled(false);
			buttonComprasRealizadas.setVisible(false);
			buttonGestionarReportes.setEnabled(true);
			buttonGestionarReportes.setVisible(true);
			buttonGestionarComentarios.setEnabled(true);
			buttonGestionarComentarios.setVisible(true);
			buttonGestionarOfertas.setEnabled(true);
			buttonGestionarOfertas.setVisible(true);
			buttonGestionarSaldo.setEnabled(false);
			buttonGestionarSaldo.setVisible(false);
		}
	    paintAgain();
	}
} // @jve:decl-index=0:visual-constraint="0,0"
