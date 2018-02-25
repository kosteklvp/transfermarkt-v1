package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import common.Connector;
import javax.swing.JScrollPane;
import javax.net.ssl.SSLEngineResult.Status;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.Toolkit;

public class ModifyTransferWindow extends JFrame {

	/////////////////////////////
	//// VARIABLES
	/////////////////////////////

	private JPanel contentPane;

	private JComboBox comboBoxPilkarz;
	private JComboBox comboBoxKlub;
	private JTextField textFieldData;
	private JTextField textFieldKwota;

	private JButton btnZmien;
	private Connector connector = null;
	private JFrame window;

	/////////////////////////////
	//// CONSTRUCTOR
	/////////////////////////////

	public ModifyTransferWindow(Connector connector, JFrame window) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ModifyTransferWindow.class.getResource("/resources/shuffle.png")));

		this.connector = connector;
		this.window = window;
		
		setResizable(false);
		initializeComponents();
		addEvents();
		setVisible(true);

	}

	/////////////////////////////
	//// METHODS
	/////////////////////////////

	/////////////////////////////
	//// DATA
	/////////////////////////////
	private void initializeComponents() {

		window.setEnabled(false);
		setTitle("Modyfikuj transfer");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(302, 225, 404, 250);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Transfer", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE).addContainerGap()));

		JLabel lblPilkarz = new JLabel("Pi\u0142karz:");

		JLabel lblKlubPozyskujacy = new JLabel("Klub pozyskuj\u0105cy:");

		JLabel lblKwotaTransferu = new JLabel("Kwota transferu(\u20AC):");

		JLabel lblDataTransferu = new JLabel("Data transferu:");

		comboBoxPilkarz = new JComboBox();
		comboBoxPilkarz.setModel(connector.getComboBoxModelOfPlayers());
		comboBoxPilkarz.setSelectedItem(connector.getComboBoxPlayerItem());

		comboBoxKlub = new JComboBox();
		comboBoxKlub.setModel(connector.getComboBoxModelOfClubs());
		comboBoxKlub.setSelectedItem(connector.getComboBoxClubItem());

		textFieldKwota = new JTextField();
		textFieldKwota.setColumns(10);
		textFieldKwota.setText(connector.getTextFieldPrice());

		textFieldData = new JTextField();
		textFieldData.setToolTipText("Podaj dat\u0119 w formacie: RRRR-MM-DD");
		textFieldData.setColumns(10);
		textFieldData.setText(connector.getTextFieldDate());

		btnZmien = new JButton("Zmie\u0144");

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addGroup(Alignment.LEADING,
				gl_panel.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addComponent(btnZmien)
								.addGroup(gl_panel.createSequentialGroup()
										.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addComponent(lblKwotaTransferu)
												.addComponent(lblKlubPozyskujacy).addComponent(lblDataTransferu).addComponent(lblPilkarz))
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false).addComponent(textFieldData)
												.addComponent(textFieldKwota).addComponent(comboBoxKlub, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(comboBoxPilkarz, 0, 234, Short.MAX_VALUE))))
						.addContainerGap(16, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(comboBoxPilkarz, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblPilkarz))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(lblKlubPozyskujacy).addComponent(comboBoxKlub,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(lblKwotaTransferu).addComponent(textFieldKwota,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(lblDataTransferu).addComponent(textFieldData,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18).addComponent(btnZmien).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		panel.setLayout(gl_panel);
		getContentPane().setLayout(groupLayout);

	}

	/////////////////////////////
	//// ACTION
	/////////////////////////////
	private void addEvents() {

		btnZmien.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String idPilkarza = ((String) comboBoxPilkarz.getSelectedItem()).replaceAll("\\D+", "");
				String idKlubPoz = ((String) comboBoxKlub.getSelectedItem()).replaceAll("\\D+", "");
				String kwota = textFieldKwota.getText().replaceAll("\\D+", "");
				String data = textFieldData.getText();
				String id = connector.getTransferID();

				connector.modifyTransfer(idPilkarza, idKlubPoz, kwota, data, id);
				BasicWindow.statusLabel.setText("Zmodyfikowano transfer ID=" + id);
				BasicWindow.table.setModel(connector.getModelOfTransfers());
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
