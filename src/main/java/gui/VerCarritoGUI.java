package gui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Sale;
import domain.Seller;
//Unai
import domain.Contraoferta;
import domain.Comprador;

public class VerCarritoGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel lblProducts = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.Products")); 

	private JButton buttonSearch = null; 
	private JButton buttonClose = null;

	private JScrollPane scrollPanelProducts = new JScrollPane();
	private JTable tableProducts= new JTable();

	private DefaultTableModel tableModelProducts;
	
	private JTextField textSearch;

	private JFrame thisFrame; 

	private String[] columnNamesProducts = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("VerOfertasAceptadasGUI.Titulo"), 
			ResourceBundle.getBundle("Etiquetas").getString("VerOfertasAceptadasGUI.PrecioOriginal"),
			ResourceBundle.getBundle("Etiquetas").getString("VerOfertasAceptadasGUI.Contraoferta"),
			ResourceBundle.getBundle("Etiquetas").getString("VerOfertasAceptadasGUI.Comprador"),

	};
	private JButton btnAceptarCarrito = null; 
	

	public VerCarritoGUI(Comprador comprador) {
		tableProducts.setEnabled(false);
		thisFrame=this;
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(608, 374));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("VerCarritoGUI.VerCarrito"));
		
		//Labels
		lblProducts = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("VerCarritoGUI.Productos")); 
		lblProducts.setBounds(52, 108, 427, 16);	

		//Scroll y contenido
		scrollPanelProducts.setBounds(new Rectangle(52, 137, 459, 150));
		scrollPanelProducts.setViewportView(tableProducts);
		
		tableModelProducts = new DefaultTableModel(null, columnNamesProducts);
		tableProducts.setModel(tableModelProducts);

		tableModelProducts.setDataVector(null, columnNamesProducts);
		tableModelProducts.setColumnCount(5);

		tableProducts.getColumnModel().getColumn(0).setPreferredWidth(150);
		tableProducts.getColumnModel().getColumn(1).setPreferredWidth(100);
		tableProducts.getColumnModel().getColumn(2).setPreferredWidth(70);
		tableProducts.getColumnModel().getColumn(3).setPreferredWidth(120);
		tableProducts.getColumnModel().removeColumn(tableProducts.getColumnModel().getColumn(4));
		
		//Text Fields
		textSearch = new JTextField();
		textSearch.setBounds(52, 56, 357, 26);
		textSearch.setColumns(10);
		
		//Buttons
		buttonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		buttonClose.setBounds(new Rectangle(94, 298, 130, 30));
		buttonClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				thisFrame.setVisible(false);

			}
		});	
		
		buttonSearch = new JButton(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.Search"));
		buttonSearch.setBounds(427, 56, 117, 29);
		buttonSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		 		try {
					tableModelProducts.setDataVector(null, columnNamesProducts);
					tableModelProducts.setColumnCount(5);
					tableProducts.getColumnModel().getColumn(0).setPreferredWidth(150);
					tableProducts.getColumnModel().getColumn(1).setPreferredWidth(100);
					tableProducts.getColumnModel().getColumn(2).setPreferredWidth(70);
					tableProducts.getColumnModel().getColumn(3).setPreferredWidth(120);
					tableProducts.getColumnModel().removeColumn(tableProducts.getColumnModel().getColumn(4));

					BLFacade facade = MainGUI.getBusinessLogic();
					List<domain.Contraoferta> contraofertas=facade.getContraofertas(comprador);

					if (contraofertas.isEmpty() ) lblProducts.setText(ResourceBundle.getBundle("Etiquetas").getString("VerOfertasAceptadasGUI.SinContraofertas"));
					else lblProducts.setText(ResourceBundle.getBundle("Etiquetas").getString("VerCarritoGUI.Productos"));
					for (domain.Contraoferta contra:contraofertas){
						Vector<Object> row = new Vector<Object>();
						row.add(contra.getCompra().getTitle());
						row.add(contra.getCompra().getPrice());
						row.add(contra.getPrecioOfrecido());
						row.add(contra.getComprador().getName());
						row.add(contra);
						tableModelProducts.addRow(row);		
					}
				} catch (Exception e1) {

					e1.printStackTrace();
				}
		 		
		 	}
		 });
		
		btnAceptarCarrito = new JButton(ResourceBundle.getBundle("Etiquetas").getString("VerCarritoGUI.AceptarCarrito")); 
		btnAceptarCarrito.setBounds(335, 298, 130, 30);
		btnAceptarCarrito.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				BLFacade facade = MainGUI.getBusinessLogic();
				facade.crearContraoferta(comprador);
			}
		});	
		
		//Content Pane
		getContentPane().add(lblProducts);
		getContentPane().add(buttonClose, null);
		getContentPane().add(scrollPanelProducts, null);
		getContentPane().add(textSearch);
		getContentPane().add(buttonSearch);
		getContentPane().add(btnAceptarCarrito);
		
		//Añado un MouseListener para que al hacer doble click en una contraoferta 
		//abra la ventana para aceptar esa oferta.
		tableProducts.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mousePressed(MouseEvent mouseEvent) {
	            
	            if(mouseEvent.getClickCount() == 2)
	            {
			        JTable table =(JTable) mouseEvent.getSource();
	            	Point point = mouseEvent.getPoint();
			        int row = table.rowAtPoint(point);
	            	Contraoferta pOferta=(Contraoferta) tableModelProducts.getValueAt(row, 4);
	            	//JFrame a = new AceptarContraOfertaGUI(pOferta, main);
					//a.setVisible(true);
	            }
	        }
		});
		//
	}
}