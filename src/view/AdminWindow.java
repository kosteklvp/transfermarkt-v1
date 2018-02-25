package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import common.Connector;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.JPasswordField;
import java.awt.Color;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;

public class AdminWindow extends JFrame {

	/////////////////////////////
	//// VARIABLES
	/////////////////////////////

	private Properties properties = null;

	private String adminPassword;

	private JPasswordField passwordField;
	private JButton btnZaloguj;
	private JLabel lblPassword;
	private JLabel image;
	
	private JFrame window;

	/////////////////////////////
	//// CONSTRUCTOR
	/////////////////////////////

	public AdminWindow(JFrame window) {
		
		this.window = window;
		initializeComponents();
		addEvents();
		setVisible(true);
	}

	/////////////////////////////
	//// DATA
	/////////////////////////////

	private void initializeComponents() {

		try {

			properties = new Properties();
			properties.load(new FileInputStream("data.properties"));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		adminPassword = properties.getProperty("admin");

		window.setEnabled(false);
		setTitle("Administrator");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(302, 225, 404, 250);
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminWindow.class.getResource("/resources/admin-with-cogwheels.png")));
		setResizable(false);

		image = new JLabel("");
		image.setIcon(new ImageIcon(AdminWindow.class.getResource("/resources/admin-with-cogwheels (1).png")));

		lblPassword = new JLabel("Has\u0142o:");
		passwordField = new JPasswordField();
		btnZaloguj = new JButton("Zaloguj");

		getRootPane().setDefaultButton(btnZaloguj);

		setLayout();

	}

	private void setLayout() {

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(24).addComponent(image).addGap(18).addComponent(lblPassword).addGap(18)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE).addComponent(btnZaloguj))
						.addContainerGap(64, Short.MAX_VALUE)));
		groupLayout
				.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addGap(39).addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(image)
								.addGroup(groupLayout.createSequentialGroup().addGap(14)
										.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblPassword).addComponent(passwordField,
												GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
										.addGap(18).addComponent(btnZaloguj)))
								.addContainerGap(54, Short.MAX_VALUE)));

		getContentPane().setLayout(groupLayout);
	}

	/////////////////////////////
	//// ACTION
	/////////////////////////////

	private void addEvents() {

		btnZaloguj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (String.valueOf(passwordField.getPassword()).equals(adminPassword)) {
					BasicWindow.changeToAdminMode();
					window.setEnabled(true);
					dispose();
				} else {
					Toolkit.getDefaultToolkit().beep();
					ErrorWindow error = new ErrorWindow("Podane has³o jest nieprawid³owe", AdminWindow.this);
				}
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