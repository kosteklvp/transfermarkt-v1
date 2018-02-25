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

public class ErrorWindow extends JFrame {

	/////////////////////////////
	//// VARIABLES
	/////////////////////////////

	private JLabel lblTxt;

	private String errorMsg;
	private JButton btnOk;
	private JFrame window;

	/////////////////////////////
	//// CONSTRUCTOR
	/////////////////////////////

	public ErrorWindow(String errorMsg, JFrame window) {

		this.window = window;
		this.errorMsg = errorMsg;
		Toolkit.getDefaultToolkit().beep();
		setVisible(true);
		initializeComponents();
		addEvents();

	}

	/////////////////////////////
	//// DATA
	/////////////////////////////

	private void initializeComponents() {

		window.setEnabled(false);
		setTitle("B³¹d");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(383, 288, 242, 125);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ErrorWindow.class.getResource("/resources/close (1).png")));
		setResizable(false);

		lblTxt = new JLabel(errorMsg);

		btnOk = new JButton("OK");

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(lblTxt).addContainerGap(171, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING,
						groupLayout.createSequentialGroup().addGap(179).addComponent(btnOk).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(20).addComponent(lblTxt, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnOk).addContainerGap(15, Short.MAX_VALUE)));
		getContentPane().setLayout(groupLayout);
		getRootPane().setDefaultButton(btnOk);

	}

	/////////////////////////////
	//// ACTION
	/////////////////////////////

	private void addEvents() {

		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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