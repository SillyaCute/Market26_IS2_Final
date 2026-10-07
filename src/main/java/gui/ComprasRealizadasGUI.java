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

public class ComprasRealizadasGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JLabel lblProducts = null; 

	private JButton buttonSearch = null; 
	private JButton buttonClose = null;

	private JScrollPane scrollPanelProducts = new JScrollPane();
	private JTable tableProducts= new JTable();

	private DefaultTableModel tableModelProducts;

	private JFrame thisFrame; 

	private String[] columnNamesProducts = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.Title"), 
			ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.Price"),
			ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.PublicationDate"),

	};
	

	public ComprasRealizadasGUI(Comprador comprador) {
		tableProducts.setEnabled(false);
		thisFrame=this;
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(611, 410));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.FindProducts"));

		//Labels
		lblProducts = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.Products"));
		lblProducts.setBounds(52, 58, 349, 24);

		//Scrolls y sus datos
		scrollPanelProducts.setBounds(new Rectangle(52, 95, 492, 211));
		scrollPanelProducts.setViewportView(tableProducts);
		
		tableModelProducts = new DefaultTableModel(null, columnNamesProducts);
		tableProducts.setModel(tableModelProducts);

		tableModelProducts.setDataVector(null, columnNamesProducts);
		tableModelProducts.setColumnCount(4);

		tableProducts.getColumnModel().getColumn(0).setPreferredWidth(200);
		tableProducts.getColumnModel().getColumn(1).setPreferredWidth(10);
		tableProducts.getColumnModel().getColumn(2).setPreferredWidth(70);
		tableProducts.getColumnModel().removeColumn(tableProducts.getColumnModel().getColumn(3));
		
		//Buttons
		buttonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		buttonClose.setBounds(new Rectangle(226, 316, 130, 30));
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
					tableModelProducts.setColumnCount(4);

					BLFacade facade = MainGUI.getBusinessLogic();
					Date today = UtilDate.trim(new Date());

					List<domain.Sale> sales=facade.getComprasRealizadasComprador(comprador);

					if (sales.isEmpty() ) lblProducts.setText(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.NoProducts"));
					else lblProducts.setText(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.Products"));
					for (domain.Sale sale:sales){
						Vector<Object> row = new Vector<Object>();
						row.add(sale.getTitle());
						row.add(sale.getPrice());
						row.add(new SimpleDateFormat("dd-MM-yyyy").format(sale.getPublicationDate()));
						row.add(sale);
						tableModelProducts.addRow(row);			
					}
				} catch (Exception e1) {

					e1.printStackTrace();
				}
				tableProducts.getColumnModel().getColumn(0).setPreferredWidth(200);
				tableProducts.getColumnModel().getColumn(1).setPreferredWidth(10);
				tableProducts.getColumnModel().getColumn(1).setPreferredWidth(70);
				tableProducts.getColumnModel().removeColumn(tableProducts.getColumnModel().getColumn(3));
		 	}
		 });
		
		//Content Pane
		getContentPane().add(lblProducts);
		getContentPane().add(buttonClose, null);
		getContentPane().add(scrollPanelProducts, null);
		getContentPane().add(buttonSearch);	
	    
		tableProducts.addMouseListener(new MouseAdapter() {
		        @Override
		        public void mousePressed(MouseEvent mouseEvent) {
		            
		            if(mouseEvent.getClickCount() == 2)
		            {
				        JTable table =(JTable) mouseEvent.getSource();
		            	Point point = mouseEvent.getPoint();
				        int row = table.rowAtPoint(point);
		            	Sale s=(Sale) tableModelProducts.getValueAt(row, 3);
		            	JFrame a = new ComentarGUI(s, comprador);
						a.setVisible(true);
		            }
		        }
		 });
	}
}