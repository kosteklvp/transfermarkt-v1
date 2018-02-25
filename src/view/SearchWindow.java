package view;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import common.Connector;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.UIManager;

public class SearchWindow extends JFrame {

	/////////////////////////////
	//// VARIABLES
	/////////////////////////////

	private JButton btnSzukaj;
	private JLabel lblWyszukaj;
	private JLabel image;
	private JPanel panel;
	private JTextField textFieldSzukaj;

	private Connector connector = null;
	private JFrame window = null;

	/////////////////////////////
	//// CONSTRUCTOR
	/////////////////////////////

	public SearchWindow(Connector connector, JFrame window) {

		this.connector = connector;
		this.window = window;

		initializeComponents();
		addEvents();

	}

	/////////////////////////////
	//// DATA
	/////////////////////////////

	private void initializeComponents() {

		window.setEnabled(false);
		setTitle("Wyszukiwanie");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(302, 225, 404, 250);
		setResizable(false);

		panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE).addContainerGap()));

		image = new JLabel("");
		image.setIcon(new ImageIcon(SearchWindow.class.getResource("/resources/magnifier (1).png")));

		lblWyszukaj = new JLabel("Wyszukaj:");

		textFieldSzukaj = new JTextField();
		textFieldSzukaj.setToolTipText("Wpisz szukan\u0105 fraz\u0119.");
		textFieldSzukaj.setColumns(10);

		btnSzukaj = new JButton("Szukaj");
		getRootPane().setDefaultButton(btnSzukaj);

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(88)
							.addComponent(lblWyszukaj))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(image)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(textFieldSzukaj, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
						.addComponent(btnSzukaj, Alignment.TRAILING))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblWyszukaj)
						.addComponent(textFieldSzukaj, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnSzukaj)
						.addComponent(image))
					.addGap(29))
		);
		panel.setLayout(gl_panel);
		getContentPane().setLayout(groupLayout);

		setVisible(true);

	}

	/////////////////////////////
	//// ACTION
	/////////////////////////////

	private void addEvents() {
		
		btnSzukaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String search = textFieldSzukaj.getText();

				if (search.equals("")) {

					ErrorWindow error = new ErrorWindow("Pole nie mo¿e byæ puste", SearchWindow.this);
					Toolkit.getDefaultToolkit().beep();

				} else {
					
					window.setEnabled(true);
					dispose();
					
					
					switch (BasicWindow.currentTableModel) {
					
					case 't':
						BasicWindow.table.setModel(connector.getModelOfSearchTransferResults(search));
						BasicWindow.table.setRowSorter(new TableRowSorter<DefaultTableModel>(connector.getModelOfSearchTransferResults(search)));
						break;
					case 'p':
						BasicWindow.table.setModel(connector.getModelOfSearchPlayerResults(search));
						BasicWindow.table.setRowSorter(new TableRowSorter<DefaultTableModel>(connector.getModelOfSearchPlayerResults(search)));
						break;
					case 'c':
						BasicWindow.table.setModel(connector.getModelOfSearchClubResults(search));
						BasicWindow.table.setRowSorter(new TableRowSorter<DefaultTableModel>(connector.getModelOfSearchClubResults(search)));
						break;
					case 'l':
						BasicWindow.table.setModel(connector.getModelOfSearchLeagueResults(search));
						BasicWindow.table.setRowSorter(new TableRowSorter<DefaultTableModel>(connector.getModelOfSearchLeagueResults(search)));
						break;
				}
					BasicWindow.statusLabel.setText("Wyszukiwanie zakoñczone. Liczba wyników: " + connector.getRowCountOfSearchResults(search));

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