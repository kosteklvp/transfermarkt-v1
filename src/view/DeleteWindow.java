package view;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import java.awt.Toolkit;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import common.Connector;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;


public class DeleteWindow extends JFrame {
	
	/////////////////////////////
	//// VARIABLES
	/////////////////////////////
	
	private JLabel lblQuestion;
	private JButton btnTak;
	private JButton btnAnuluj;
	
	private Connector connector = null;
	private JFrame window;
	
	
	/////////////////////////////
	//// CONSTRUCTOR
	/////////////////////////////
	
	public DeleteWindow(Connector connector, JFrame window) {
		
		this.window = window;
		this.connector = connector;
		setVisible(true);
		initializeComponents();
		addEvents();
		
	}
	
	
	/////////////////////////////
	//// DATA
	/////////////////////////////
		
	private void initializeComponents() {
		
		window.setEnabled(false);
		setTitle("Usu\u0144 ");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(383, 288, 242, 125);
		setIconImage(Toolkit.getDefaultToolkit().getImage(DeleteWindow.class.getResource("/resources/002-rubbish-bin.png")));
		setResizable(false);
		

		lblQuestion = new JLabel("Czy na pewno chcesz usun\u0105\u0107 rekord?");
		
		btnTak = new JButton("Tak");
		
		btnAnuluj = new JButton("Anuluj");
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(23)
							.addComponent(lblQuestion))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(48)
							.addComponent(btnTak)
							.addGap(35)
							.addComponent(btnAnuluj)))
					.addContainerGap(24, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblQuestion, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAnuluj)
						.addComponent(btnTak))
					.addContainerGap(19, Short.MAX_VALUE))
		);
		getContentPane().setLayout(groupLayout);
		
		
		getRootPane().setDefaultButton(btnTak);
		
	}
	
	
	/////////////////////////////
	//// ACTION
	/////////////////////////////
	
	private void addEvents() {
		
		btnAnuluj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.setEnabled(true);
				dispose();
				System.out.println(BasicWindow.currentTableModel);
			}
		});
		
		
		btnTak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				switch (BasicWindow.currentTableModel) {
				
				case 't':
					connector.deleteTransfer();
					break;
				case 'p':
					connector.deletePlayer();
					break;
				case 'c':
					
				case 'l':
				}
				window.setEnabled(true);
				dispose();
			}
		});
		
		this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
            	window.setEnabled(true);
            	dispose();
            }
        });
		
	}
}