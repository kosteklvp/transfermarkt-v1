package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import common.Connector;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Toolkit;
import javax.swing.UIManager;
import java.awt.Color;

public class ModifyPlayerWindow extends JFrame {

	/////////////////////////////
	//// VARIABLES
	/////////////////////////////

	private JPanel contentPane;

	private JButton btnDodaj;
	private Connector connector = null;
	private JFrame window;
	private JTextField textFieldImie;
	private JTextField textFieldNazwisko;
	private JTextField textFieldWartosc;
	private JComboBox comboBoxPozycja;
	private JComboBox comboBoxNarodowosc;
	private JComboBox comboBoxKlub;

	/////////////////////////////
	//// CONSTRUCTOR
	/////////////////////////////

	public ModifyPlayerWindow(Connector connector, JFrame window) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ModifyPlayerWindow.class.getResource("/resources/007-update-arrow.png")));

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
		setTitle("Modyfikuj pi\u0142karza");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(302, 225, 404, 303);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Pi\u0142karz", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE).addContainerGap()));

		JLabel lblImie = new JLabel("Imi\u0119:");

		JLabel lblNazwisko = new JLabel("Nazwisko:");

		JLabel lblWartoœæ = new JLabel("Warto\u015B\u0107 pi\u0142karza(\u20AC):");

		JLabel lblPozycja = new JLabel("Pozycja:");

		btnDodaj = new JButton("Zmie\u0144");
		
		JLabel lblNarodowosc = new JLabel("Narodowo\u015B\u0107:");
		
		JLabel lblKlub = new JLabel("Klub:");
		
		textFieldImie = new JTextField();
		textFieldImie.setColumns(10);
		textFieldImie.setText(connector.getTextFieldName());
		
		textFieldNazwisko = new JTextField();
		textFieldNazwisko.setColumns(10);
		textFieldNazwisko.setText(connector.getTextFieldSurname());
		
		textFieldWartosc = new JTextField();
		textFieldWartosc.setColumns(10);
		textFieldWartosc.setText(connector.getTextFieldValue());
		
		comboBoxPozycja = new JComboBox();
		comboBoxPozycja.setModel(connector.getComboBoxModelOfPositions());
		comboBoxPozycja.setSelectedItem(connector.getComboBoxPositionItem());
		
		comboBoxNarodowosc = new JComboBox();
		comboBoxNarodowosc.setModel(connector.getComboBoxModelOfCountries());
		comboBoxNarodowosc.setSelectedItem(connector.getComboBoxCountryItem());
		
		comboBoxKlub = new JComboBox();
		comboBoxKlub.setModel(connector.getComboBoxModelOfClubs());
		comboBoxKlub.setSelectedItem(connector.getComboBoxClubItemForPlayers());

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnDodaj)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(gl_panel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(lblKlub))
								.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblNarodowosc)
										.addComponent(lblNazwisko)
										.addComponent(lblImie)
										.addComponent(lblWartoœæ)
										.addComponent(lblPozycja))))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(comboBoxKlub, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(comboBoxNarodowosc, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(textFieldNazwisko)
								.addComponent(textFieldImie, GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
								.addComponent(textFieldWartosc)
								.addComponent(comboBoxPozycja, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
					.addContainerGap(32, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblImie)
						.addComponent(textFieldImie, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNazwisko)
						.addComponent(textFieldNazwisko, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblWartoœæ)
						.addComponent(textFieldWartosc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPozycja)
						.addComponent(comboBoxPozycja, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNarodowosc)
							.addGap(14)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblKlub)
								.addComponent(comboBoxKlub, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED))
						.addComponent(comboBoxNarodowosc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(btnDodaj)
					.addGap(16))
		);

		panel.setLayout(gl_panel);
		getContentPane().setLayout(groupLayout);

	}

	/////////////////////////////
	//// ACTION
	/////////////////////////////
	private void addEvents() {

		btnDodaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String imie = textFieldImie.getText();
				String nazwisko = textFieldNazwisko.getText();
				String wartosc = textFieldWartosc.getText().replaceAll("\\D+", "");
				String pozycja = ((String) comboBoxPozycja.getSelectedItem()).replaceAll("\\D+", "");
				String kraj = ((String) comboBoxNarodowosc.getSelectedItem()).replaceAll("\\D+", "");
				String klub = ((String) comboBoxKlub.getSelectedItem()).replaceAll("\\D+", "");
				String id = connector.getPlayerID();
				
				connector.modifyPlayer(imie, nazwisko, wartosc, pozycja, kraj, klub, id);
				BasicWindow.statusLabel.setText("Zmodyfikowano pi³karza ID=" + id);
				BasicWindow.table.setModel(connector.getModelOfPlayers());
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
