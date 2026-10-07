package gui;

import java.util.*;

import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.awt.image.BufferedImage;

import businessLogic.BLFacade;
import domain.Sale;


public class BorrarOfertaGUI extends JFrame {
	
    File targetFile;
    BufferedImage targetImg;
    public JPanel panel_1;
    private static final int baseSize = 160;
	private static final String basePath="src/main/resources/images/";
	
	private static final long serialVersionUID = 1L;
	private JFrame thisFrame;
	
	private JLabel lblStatus = null; 
	private JLabel lblTitle = null;
	private JLabel lblDescription = null; 
	private JLabel lblProductStatus = null;
	private JLabel lblPrice = null;
	private JLabel lblMsg = new JLabel();
	private JLabel lblError = new JLabel();
	private JLabel lblStatusField=new JLabel();
	
	private JTextField txtPrice = new JTextField();
	private JTextField txtTitle=new JTextField();
	private JTextField txtDescription=new JTextField();
	
	private File selectedFile;
    private String irudia;

	private JScrollPane scrollPaneEvents = new JScrollPane();
	private DefaultComboBoxModel<String> statusOptions = new DefaultComboBoxModel<String>();
	
	private JButton jButtonClose = null;
	private JButton jButtonBorrar = null;
	private JButton jButtonDescatalogar = null;
	private JLabel lblMostrarExito = null;
	
	public BorrarOfertaGUI(Sale sale) { 
		thisFrame=this; 
		this.setVisible(true);
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 425));

		//Labels
		lblStatus = new JLabel(new SimpleDateFormat("dd-MM-yyyy").format(sale.getPublicationDate()));
		lblStatus.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblStatus.setBounds(37, 231, 289, 16);
		
		lblTitle = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.Title"));
		lblTitle.setBounds(new Rectangle(6, 56, 140, 20));
		
		lblDescription = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.Description"));
		lblDescription.setBounds(6, 81, 140, 16);
		
		lblProductStatus = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.Status"));
		lblProductStatus.setBounds(new Rectangle(40, 15, 140, 25));
		lblProductStatus.setBounds(6, 187, 140, 25);
		
		lblPrice = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.Price"));
		lblPrice.setBounds(new Rectangle(6, 166, 101, 20));
		
		lblMsg.setBounds(new Rectangle(275, 214, 305, 20));
		lblMsg.setForeground(Color.red);
		
		lblError.setBounds(new Rectangle(6, 231, 320, 20));
		lblError.setForeground(Color.red);
		
		lblStatusField = new JLabel(Utils.getStatus(sale.getStatus())); 
		lblStatusField.setBounds(148, 191, 92, 16);
		
		lblMostrarExito = new JLabel();
		lblMostrarExito.setBounds(16, 334, 310, 20);
		lblMostrarExito.setForeground(Color.green);
		
		//Text Fields
		txtPrice.setText(Float.toString(sale.getPrice()));		
		txtPrice.setEditable(false);
		txtPrice.setBounds(new Rectangle(148, 166, 60, 20));
		
		txtTitle.setText(sale.getTitle());
		txtTitle.setEditable(false);
		txtTitle.setBounds(148, 56, 370, 26);
		txtTitle.setColumns(10);
		
		txtDescription.setText(sale.getDescription());
		txtDescription.setEditable(false);
		txtDescription.setBounds(148, 84, 370, 73);
		txtDescription.setColumns(10);
		
		//Scroll
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));
		
		//Buttons
		jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		jButtonClose.setBounds(new Rectangle(16, 268, 114, 30));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisFrame.setVisible(false);			}
		});
		
		jButtonBorrar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BorrarOfertaGUI.Borrar"));
		jButtonBorrar.setBounds(new Rectangle(16, 268, 114, 30));
		jButtonBorrar.setBounds(168, 244, 114, 30);
		jButtonBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
		 		facade.borrarOferta(sale);
		 		lblMostrarExito.setText(ResourceBundle.getBundle("Etiquetas").getString("BorrarOfertaGUI.ExitoBorrado"));
		 		
		 		new Timer(3000, new ActionListener() {
				    @Override
				    public void actionPerformed(ActionEvent e) {
				        dispose();
				    }
				}) {{
				    setRepeats(false);
				}}.start();
			}
		});
		
		jButtonDescatalogar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BorrarOfertaGUI.Descatalogar"));
		jButtonDescatalogar.setBounds(new Rectangle(16, 268, 114, 30));
		jButtonDescatalogar.setBounds(168, 293, 114, 30);
		jButtonDescatalogar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
		 		facade.descatalogarOferta(sale);
		 		lblMostrarExito.setText(ResourceBundle.getBundle("Etiquetas").getString("BorrarOfertaGUI.ExitoDescatalogado"));
		 		
		 		new Timer(3000, new ActionListener() {
				    @Override
				    public void actionPerformed(ActionEvent e) {
				        dispose();
				    }
				}) {{
				    setRepeats(false);
				}}.start();
			}
		});
		
		//Panel
		panel_1 = new JPanel();
		panel_1.setBounds(338, 169, 180, 160);
		
		//Codigo para la imagen
		BLFacade facade = MainGUI.getBusinessLogic();
		String file=sale.getFile();
		if (file!=null) {
			Image img=facade.downloadImage(file);
			targetImg = rescale((BufferedImage)img);
			panel_1.setLayout(new BorderLayout(0, 0));
			panel_1.add(new JLabel(new ImageIcon(targetImg))); 
		}
		
		System.out.println("status: "+sale.getStatus());
		
		getContentPane().add(lblStatus);
		getContentPane().add(lblTitle, null);
		getContentPane().add(lblDescription);
		getContentPane().add(lblProductStatus);
		getContentPane().add(lblPrice, null);
		getContentPane().add(lblMsg, null);
		getContentPane().add(lblError, null);
		getContentPane().add(lblStatusField);
		getContentPane().add(lblMostrarExito);
		getContentPane().add(txtPrice, null);
		getContentPane().add(txtTitle);
		getContentPane().add(txtDescription);
		getContentPane().add(jButtonClose, null);
		getContentPane().add(jButtonBorrar);
		getContentPane().add(jButtonDescatalogar);
		getContentPane().add(panel_1);
		
		setVisible(true);
	}	 
	
	public BufferedImage rescale(BufferedImage originalImage){
        BufferedImage resizedImage = new BufferedImage(baseSize, baseSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, baseSize, baseSize, null);
        g.dispose();
        return resizedImage;
    }
	
	
	
}

