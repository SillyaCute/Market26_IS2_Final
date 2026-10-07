package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Sale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import javax.swing.table.DefaultTableModel;


public class QuerySalesGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JFrame thisFrame; 
	
	private JLabel lblProducts = null;
	private JLabel lblNombre = null;
	private JLabel lblPrecioMaximo = null;
	private JLabel lblFecha = null;

	private JButton buttonSearch = null;
	private JButton buttonClose = null;

	private JTextField textFieldSearch;
	private JTextField textFieldPrecioMax;
	private JTextField textFieldFecha;
	
	private JScrollPane scrollPanelProducts = new JScrollPane();
	private JTable tableProducts = new JTable();
	private DefaultTableModel tableModelProducts;

	private String[] columnNamesProducts = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.Title"), 
			ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.Price"),
			ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.PublicationDate"),

	};

	public QuerySalesGUI() {
		tableProducts.setEnabled(false);
		thisFrame=this;
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(585, 392));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.FindProducts"));

		//Labels
		lblNombre = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.Nombre"));
		lblNombre.setBounds(52, 27, 80, 17);
		
		lblPrecioMaximo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.PrecioMaximo"));
		lblPrecioMaximo.setBounds(220, 27, 130, 17);
		
		lblFecha = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.Fecha"));
		lblFecha.setBounds(368, 27, 94, 17);
		
		lblProducts = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.Products"));
		lblProducts.setBounds(52, 108, 427, 16);
		
		//Tabla scroll y contenidos
		scrollPanelProducts.setBounds(new Rectangle(52, 137, 459, 150));
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
		textFieldSearch = new JTextField();
		textFieldSearch.setBounds(52, 56, 158, 26);
		textFieldSearch.setColumns(10);
		
		textFieldPrecioMax = new JTextField();
		textFieldPrecioMax.setColumns(10);
		textFieldPrecioMax.setBounds(220, 56, 130, 26);
		
		textFieldFecha = new JTextField();
		textFieldFecha.setColumns(10);
		textFieldFecha.setBounds(362, 56, 130, 26);
		
		//Buttons
		buttonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.Close"));
		buttonClose.setBounds(new Rectangle(220, 308, 130, 30));
		buttonClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				thisFrame.setVisible(false);

			}
		});		
		
		buttonSearch = new JButton(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.Search"));
		buttonSearch.setBounds(375, 105, 117, 23);
		buttonSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					tableModelProducts.setDataVector(null, columnNamesProducts);
					tableModelProducts.setColumnCount(4);

					BLFacade facade = MainGUI.getBusinessLogic();
					Date today = UtilDate.trim(new Date());

					//Los filtros son 1-la del nombre la default 2- De fechas despues de X  3- De precios menores que X 
					//Comprobar que no es null y puedo hacer el filtro
					Boolean fecha = false;
					Boolean precio = false;
					float precioMax = 0;
					Date date = null;
					
					//Parte del filtro de fecha
					SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
					String dateInString = textFieldFecha.getText();
					if(!dateInString.equals("")) {
						try {
							date = formatter.parse(dateInString);
							fecha = true;
						}catch(Exception pp) {
							fecha = false;
						}
					}
					
					//Parte del filtro de precio 
					if(!textFieldPrecioMax.getText().equals("")) {
						try {
							precioMax = Float.parseFloat(textFieldPrecioMax.getText());
							precio = true;
						}catch(Exception pp) {
							precioMax = 0; 
							precio = false;
						}
					}

					List<domain.Sale> sales=facade.getPublishedSales(textFieldSearch.getText(),today);

					if (sales.isEmpty() ) lblProducts.setText(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.NoProducts"));
					else lblProducts.setText(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.Products"));
					for (domain.Sale sale:sales){
						if(!sale.getSeller().getName().equals("Basura")) {
							//Si se ha dado ambas la fecha y el precio
							if( fecha && precio) {
								if(sale.getPrice()<precioMax &&sale.getPublicationDate().after(date)) {
									Vector<Object> row = new Vector<Object>();
									row.add(sale.getTitle());
									row.add(sale.getPrice());
									row.add(new SimpleDateFormat("dd-MM-yyyy").format(sale.getPublicationDate()));
									row.add(sale);
									tableModelProducts.addRow(row);	
								}
						    //Filtro condicion para agregar dependiendo de fecha
							}else if(fecha) {
								if(sale.getPublicationDate().after(date)) {
									Vector<Object> row = new Vector<Object>();
									row.add(sale.getTitle());
									row.add(sale.getPrice());
									row.add(new SimpleDateFormat("dd-MM-yyyy").format(sale.getPublicationDate()));
									row.add(sale);
									tableModelProducts.addRow(row);	
								}
							//Solo precio 
							}else if(precio){
								if(sale.getPrice()<precioMax) {
									Vector<Object> row = new Vector<Object>();
									row.add(sale.getTitle());
									row.add(sale.getPrice());
									row.add(new SimpleDateFormat("dd-MM-yyyy").format(sale.getPublicationDate()));
									row.add(sale);
									tableModelProducts.addRow(row);	
								}
							//Default
							}else {
								Vector<Object> row = new Vector<Object>();
								row.add(sale.getTitle());
								row.add(sale.getPrice());
								row.add(new SimpleDateFormat("dd-MM-yyyy").format(sale.getPublicationDate()));
								row.add(sale);
								tableModelProducts.addRow(row);	
							}
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
				getContentPane().add(lblNombre);
				getContentPane().add(lblPrecioMaximo);
				getContentPane().add(lblFecha);
				getContentPane().add(lblProducts);
				getContentPane().add(scrollPanelProducts, null);
				getContentPane().add(textFieldSearch);
				getContentPane().add(textFieldPrecioMax);
				getContentPane().add(textFieldFecha);
				getContentPane().add(buttonSearch);
				getContentPane().add(buttonClose, null);
		
		tableProducts.addMouseListener(new MouseAdapter() {
		        @Override
		        public void mousePressed(MouseEvent mouseEvent) {
		            
		            if(mouseEvent.getClickCount() == 2)
		            {
				        JTable table =(JTable) mouseEvent.getSource();
		            	Point point = mouseEvent.getPoint();
				        int row = table.rowAtPoint(point);
		            	Sale s=(Sale) tableModelProducts.getValueAt(row, 3);
			            new ShowSaleGUI(s);
		            }
		        }
		 });
	}
}
