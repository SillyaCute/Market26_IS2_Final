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
import domain.Comprador;
import domain.Sale;

public class AceptarOfertaGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JLabel jLabelProducts = null; 

	private JButton btnVerCarrito = null;
	private JButton jButtonSearch = null; 
	private JButton jButtonClose = null;
	
	private JTextField jTextFieldSearch;

	private JScrollPane scrollPanelProducts = new JScrollPane();
	private JTable tableProducts= new JTable();

	private DefaultTableModel tableModelProducts;

	private JFrame thisFrame; 

	private String[] columnNamesProducts = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.Title"), 
			ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.Price"),
			ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.PublicationDate"),

	};

	public AceptarOfertaGUI(Comprador comprador) {
		tableProducts.setEnabled(false);
		thisFrame=this;
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(610, 381));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.FindProducts"));
		
		//Label
		jLabelProducts = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.Products"));
		jLabelProducts.setBounds(52, 108, 427, 16);

		//Tabla scroll y contenidos
		scrollPanelProducts.setBounds(new Rectangle(52, 137, 492, 150));
		scrollPanelProducts.setViewportView(tableProducts);
		
		tableModelProducts = new DefaultTableModel(null, columnNamesProducts);
		tableProducts.setModel(tableModelProducts);

		tableModelProducts.setDataVector(null, columnNamesProducts);
		tableModelProducts.setColumnCount(4);

		tableProducts.getColumnModel().getColumn(0).setPreferredWidth(200);
		tableProducts.getColumnModel().getColumn(1).setPreferredWidth(10);
		tableProducts.getColumnModel().getColumn(2).setPreferredWidth(70);
		tableProducts.getColumnModel().removeColumn(tableProducts.getColumnModel().getColumn(3));
		
		//Text Fields
		jTextFieldSearch = new JTextField();
		jTextFieldSearch.setBounds(52, 56, 357, 26);
		jTextFieldSearch.setColumns(10);
		
		//Buttons
		jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		jButtonClose.setBounds(new Rectangle(118, 298, 130, 30));
		jButtonClose.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				thisFrame.setVisible(false);
			}
		});	
		
		jButtonSearch = new JButton(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.Search"));
		jButtonSearch.setBounds(427, 56, 117, 29);
		jButtonSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					tableModelProducts.setDataVector(null, columnNamesProducts);
					tableModelProducts.setColumnCount(4);

					BLFacade facade = MainGUI.getBusinessLogic();
					Date today = UtilDate.trim(new Date());

					List<domain.Sale> sales=facade.getPublishedSales(jTextFieldSearch.getText(),today);

					if (sales.isEmpty() ) jLabelProducts.setText(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.NoProducts"));
					else jLabelProducts.setText(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.Products"));
					for (domain.Sale sale:sales){
						if(!sale.getSeller().getName().equals("Basura")) {
							Vector<Object> row = new Vector<Object>();
							row.add(sale.getTitle());
							row.add(sale.getPrice());
							row.add(new SimpleDateFormat("dd-MM-yyyy").format(sale.getPublicationDate()));
							row.add(sale);
							tableModelProducts.addRow(row);		
						}	
					}
				} catch (Exception e1) {

					e1.printStackTrace();
				}
				tableProducts.getColumnModel().getColumn(0).setPreferredWidth(200);
				tableProducts.getColumnModel().getColumn(1).setPreferredWidth(10);
				tableProducts.getColumnModel().getColumn(2).setPreferredWidth(70);
				tableProducts.getColumnModel().removeColumn(tableProducts.getColumnModel().getColumn(3));
		 		
		 	}
		 });
		
		//Content Pane
		getContentPane().add(jLabelProducts);
		getContentPane().add(jButtonClose, null);
		getContentPane().add(scrollPanelProducts, null);
		getContentPane().add(jTextFieldSearch);
		getContentPane().add(jButtonSearch);
		
		btnVerCarrito = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AceptarOfertaGUI.VerCarrito"));
		btnVerCarrito.setBounds(321, 298, 130, 30);
		getContentPane().add(btnVerCarrito);
		btnVerCarrito.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFrame a = new VerCarritoGUI(comprador);
				a.setVisible(true);
			}
		});	
	    
		tableProducts.addMouseListener(new MouseAdapter() {
		        @Override
		        public void mousePressed(MouseEvent mouseEvent) {
		            
		            if(mouseEvent.getClickCount() == 2)
		            {
				        JTable table =(JTable) mouseEvent.getSource();
		            	Point point = mouseEvent.getPoint();
				        int row = table.rowAtPoint(point);
		            	Sale s=(Sale) tableModelProducts.getValueAt(row, 3);
		            	JFrame a = new ContraOfertaGUI(s, comprador);
						a.setVisible(true);
		            }
		        }
		 });
	}
}
