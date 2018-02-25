package view;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import java.awt.Toolkit;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class AdminLeaveWindow extends JFrame {

	/////////////////////////////
	//// VARIABLES
	/////////////////////////////

	private JLabel lblQuestion;
	private JButton btnTak;
	private JButton btnAnuluj;
	
	private JFrame window;

	/////////////////////////////
	//// CONSTRUCTOR
	/////////////////////////////

	public AdminLeaveWindow(JFrame window) {

		this.window = window;
		setVisible(true);
		initializeComponents();
		addEvents();

	}

	/////////////////////////////
	//// DATA
	/////////////////////////////

	private void initializeComponents() {

		window.setEnabled(false);
		setTitle("Wyloguj");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(383, 288, 242, 125);
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminWindow.class.getResource("/resources/admin-with-cogwheels.png")));
		setResizable(false);

		lblQuestion = new JLabel("Czy chcesz opu\u015Bci\u0107 tryb adminstratora?");

		btnTak = new JButton("Tak");

		btnAnuluj = new JButton("Anuluj");

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addGap(23).addComponent(lblQuestion))
						.addGroup(groupLayout.createSequentialGroup().addGap(48).addComponent(btnTak).addGap(35).addComponent(btnAnuluj)))
				.addContainerGap(24, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(lblQuestion, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnAnuluj).addComponent(btnTak))
						.addContainerGap(19, Short.MAX_VALUE)));
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
			}
		});

		btnTak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BasicWindow.changeToNormalMode();
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