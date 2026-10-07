package gui;

import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.util.*;
import java.util.List;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.awt.image.BufferedImage;

import businessLogic.BLFacade;
import domain.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class VisualizarPerfilGUI extends JFrame {

	private String[] columnNamesProducts = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.Title"), 
			ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.Price"),
			ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.PublicationDate"),

	};
	private	JScrollPane scrollPanelProducts = new JScrollPane();
	private JTable tableProducts= new JTable();
	private DefaultTableModel tableModelProducts;


	/**
	 * Create the frame.
	 */
	public VisualizarPerfilGUI(Sale sale) {
		tableProducts.setEnabled(false);
		scrollPanelProducts.setViewportView(tableProducts);
		tableModelProducts = new DefaultTableModel(null, columnNamesProducts);

		tableProducts.setModel(tableModelProducts);

		tableModelProducts.setDataVector(null, columnNamesProducts);
		tableModelProducts.setColumnCount(4); // another column added to allocate ride objects

		tableProducts.getColumnModel().getColumn(0).setPreferredWidth(200);
		tableProducts.getColumnModel().getColumn(1).setPreferredWidth(10);
		tableProducts.getColumnModel().getColumn(1).setPreferredWidth(70);


		tableProducts.getColumnModel().removeColumn(tableProducts.getColumnModel().getColumn(3)); // not shown in JTable

		this.getContentPane().add(scrollPanelProducts, null);
		
		BLFacade facade = MainGUI.getBusinessLogic();
		Seller seller = sale.getSeller();
		
		List<Comentario> comentarios=facade.getComentarios();
		List<Comentario> comentariosComprador = new ArrayList<Comentario>();
		
		for(int j = 0; j<comentarios.size();j++) {
			if(comentarios.get(j).getComentado().getEmail().equals(seller.getEmail())) {
				comentariosComprador.add(comentarios.get(j));
			}
		}
		int puntuacion = 0;
		for(int i = 0;i<comentariosComprador.size();i++) {
			puntuacion += comentariosComprador.get(i).getPuntuacion();
		}
		float total = 0;
		total = (float) puntuacion /(float) comentariosComprador.size();
		if(puntuacion==0 && comentariosComprador.size()==0) {
			total = (float)0.0;
		}
		List<Sale> sales = seller.getSales();
		
		
		tableModelProducts.setDataVector(null, columnNamesProducts);
		tableModelProducts.setColumnCount(4);
		
		System.out.println("SALES:="+sales.size());
		for(int c = 0;c<sales.size();c++) {
			Vector<Object> row = new Vector<Object>();
			row.add(sales.get(c).getTitle());
			row.add(sales.get(c).getPrice());
			row.add(new SimpleDateFormat("dd-MM-yyyy").format(sales.get(c).getPublicationDate()));
			row.add(sales.get(c));
			tableModelProducts.addRow(row);
		}
		tableProducts.getColumnModel().getColumn(0).setPreferredWidth(200);
		tableProducts.getColumnModel().getColumn(1).setPreferredWidth(10);
		tableProducts.getColumnModel().getColumn(1).setPreferredWidth(70);
		tableProducts.getColumnModel().removeColumn(tableProducts.getColumnModel().getColumn(3)); // not shown in JTable

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

		
		setBounds(100, 100, 450, 300);
		
		JLabel emailStatic = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Registrarse.Correo")+":");
		
		JLabel emailVendedor = new JLabel(seller.getEmail());
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ComentarGUI.Puntuacion")+":");		
		JLabel clasificacionVendedor = new JLabel(String.valueOf(total));
		
		JLabel sellerName = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Registrarse.Nombre")+":");		
		
		JLabel sellerNameValue = new JLabel(seller.getName());
				

		
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPanelProducts, GroupLayout.PREFERRED_SIZE, 413, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(clasificacionVendedor, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(sellerName)
								.addComponent(emailStatic, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
							.addGap(34)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(emailVendedor, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
								.addComponent(sellerNameValue, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))))
					.addContainerGap(25, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(sellerName)
						.addComponent(sellerNameValue))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(emailStatic, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
						.addComponent(emailVendedor, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addComponent(clasificacionVendedor, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
					.addComponent(scrollPanelProducts, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
	}
}