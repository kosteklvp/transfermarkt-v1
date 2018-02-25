package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import common.Connector;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

public class BasicWindow extends JFrame {

	/////////////////////////////
	//// VARIABLES
	/////////////////////////////

	private JPanel contentPane;
	private static Connector connector = null;

	public static JTable table;
	public static JLabel statusLabel;
	private static JLabel lblAdministrator;

	public static boolean isAdminModeOn = false;
	public static char currentTableModel;

	private JMenuItem mntmWyswietlTransfery;
	private JMenu mnPikarze;
	private JMenuItem mntmWyswietlPilkarzy;
	private JMenu mnKluby;
	private JMenu mnTransfery;
	private JMenuItem mntmWyswietlKluby;
	private static JMenuItem mntmDodajTransfer;
	private static JMenuItem mntmUsunTransfer;
	private static JMenuItem mntmModyfikujTransfer;
	private static JMenuItem mntmDodajPilkarza;
	private static JMenuItem mntmUsunPilkarza;
	private static JMenuItem mntmModyfikujPilkarza;
	private static JMenuItem mntmDodajKlub;
	private static JMenuItem mntmUsunKlub;
	private static JMenuItem mntmModyfikujKlub;
	private JMenuItem mntmWyswietlLigi;
	private static JMenuItem mntmDodajLige;
	private static JMenuItem mntmUsunLige;
	private static JMenuItem mntmModyfikujLige;
	private JMenu mnOpcje;
	private static JMenuItem mntmAdmin;
	private JMenuItem mntmWyjscie;
	private JMenuItem mntmWyszukajTransfer;
	private JMenuItem mntmWyszukajPilkarza;
	private JMenuItem mntmNewMenuItem;
	private JMenuItem mntmWyszukajKlub;
	private JMenuItem mntmWyszukajLig;
	private JScrollPane scrollPane;
	private JPopupMenu popupMenu;
	private static JMenuItem mntmUsun;
	private static JMenuItem mntmModyfikuj;

	/////////////////////////////
	//// CONSTRUCTOR
	/////////////////////////////

	public BasicWindow(Connector connector) {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(BasicWindow.class.getResource("/resources/running-sportive-shoe-for-soccer-players.png")));

		this.connector = connector;
		initializeComponents();
		addEvents();
		setVisible(true);

	}

	/////////////////////////////
	//// DATA
	/////////////////////////////
	private void initializeComponents() {

		setTitle("Football Market");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 809, 500);

		scrollPane = new JScrollPane();

		JPanel statusBar = new JPanel();
		statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 773, Short.MAX_VALUE).addComponent(statusBar,
										Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 773, Short.MAX_VALUE))
						.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				groupLayout.createSequentialGroup().addContainerGap().addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(statusBar, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		statusBar.setLayout(null);

		statusLabel = new JLabel("Po³¹czono");
		statusLabel.setBounds(10, 5, 654, 14);
		statusBar.add(statusLabel);

		lblAdministrator = new JLabel("Administrator");
		lblAdministrator.setVisible(false);
		lblAdministrator.setForeground(new Color(0, 200, 0));
		lblAdministrator.setIcon(new ImageIcon(BasicWindow.class.getResource("/resources/admin_green.png")));
		lblAdministrator.setBounds(674, 5, 89, 14);
		statusBar.add(lblAdministrator);

		table = new JTable();
		currentTableModel = 't';

		table.setModel(connector.getModelOfTransfers());
		scrollPane.setViewportView(table);
		
		table.setRowSorter(new TableRowSorter<DefaultTableModel>(connector.getModelOfTransfers()));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		addMenu();
		
		getContentPane().setLayout(groupLayout);

	}

	public static void changeToAdminMode() {

		mntmDodajTransfer.setEnabled(true);
		mntmDodajPilkarza.setEnabled(true);
		mntmUsun.setEnabled(true);
		mntmModyfikuj.setEnabled(true);

		isAdminModeOn = true;

		statusLabel.setText("Tryb administratora zosta³ w³¹czony");
		lblAdministrator.setVisible(true);
		table.setModel(connector.getModelOfTransfers());
		table.setRowSorter(new TableRowSorter<DefaultTableModel>(connector.getModelOfTransfers()));
		
		switch (BasicWindow.currentTableModel) {
		case 't':
			BasicWindow.table.setModel(connector.getModelOfTransfers());
			break;
		case 'p':
			BasicWindow.table.setModel(connector.getModelOfPlayers());
			break;
		case 'c':
			BasicWindow.table.setModel(connector.getModelOfClubs());
			break;
		case 'l':
			BasicWindow.table.setModel(connector.getModelOfLeagues());
			break;
		}

		mntmAdmin.setText("Wy³¹cz tryb administratora");
		mntmAdmin.setIcon(new ImageIcon(BasicWindow.class.getResource("/resources/user-silhouette.png")));
	}

	public static void changeToNormalMode() {
		mntmDodajTransfer.setEnabled(false);
		mntmDodajPilkarza.setEnabled(false);
		mntmDodajKlub.setEnabled(false);
		mntmDodajLige.setEnabled(false);
		mntmUsun.setEnabled(false);
		mntmModyfikuj.setEnabled(false);
		mntmUsunTransfer.setEnabled(false);
		mntmUsunPilkarza.setEnabled(false);
		mntmUsunKlub.setEnabled(false);
		mntmUsunLige.setEnabled(false);
		mntmModyfikujTransfer.setEnabled(false);
		mntmModyfikujPilkarza.setEnabled(false);
		mntmModyfikujKlub.setEnabled(false);
		mntmModyfikujLige.setEnabled(false);

		isAdminModeOn = false;
		statusLabel.setText("Tryb administratora zosta³ wy³¹czony");
		table.setModel(connector.getModelOfTransfers());
		table.setRowSorter(new TableRowSorter<DefaultTableModel>(connector.getModelOfTransfers()));
		
		lblAdministrator.setVisible(false);

		mntmAdmin.setText("W³¹cz tryb administratora");
		mntmAdmin.setIcon(new ImageIcon(BasicWindow.class.getResource("/resources/admin-with-cogwheels.png")));
	}

	/////////////////////////////
	//// MENU
	/////////////////////////////

	private void addMenu() {

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnTransfery = new JMenu("Transfery");
		menuBar.add(mnTransfery);

		mntmWyswietlTransfery = new JMenuItem("Wy\u015Bwietl transfery");
		mntmWyswietlTransfery.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, 0));
		mntmWyswietlTransfery.setIcon(new ImageIcon(BasicWindow.class.getResource("/resources/menu.png")));
		mnTransfery.add(mntmWyswietlTransfery);

		mntmWyszukajTransfer = new JMenuItem("Wyszukaj transfer");
		mntmWyszukajTransfer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.SHIFT_MASK));
		mntmWyszukajTransfer.setIcon(new ImageIcon(BasicWindow.class.getResource("/resources/magnifying-glass-finder.png")));
		mnTransfery.add(mntmWyszukajTransfer);

		mntmDodajTransfer = new JMenuItem("Dodaj transfer");
		mntmDodajTransfer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_MASK));
		mntmDodajTransfer.setEnabled(false);
		mntmDodajTransfer.setIcon(new ImageIcon(BasicWindow.class.getResource("/resources/001-add.png")));
		mnTransfery.add(mntmDodajTransfer);

		mntmUsunTransfer = new JMenuItem("Usu\u0144 transfer");
		mntmUsunTransfer.setEnabled(false);
		mntmUsunTransfer.setIcon(new ImageIcon(BasicWindow.class.getResource("/resources/002-rubbish-bin.png")));
		mnTransfery.add(mntmUsunTransfer);

		mntmModyfikujTransfer = new JMenuItem("Modyfikuj transfer");
		mntmModyfikujTransfer.setEnabled(false);
		mntmModyfikujTransfer.setIcon(new ImageIcon(BasicWindow.class.getResource("/resources/007-update-arrow.png")));
		mnTransfery.add(mntmModyfikujTransfer);

		mnPikarze = new JMenu("Pi\u0142karze");
		menuBar.add(mnPikarze);

		mntmWyswietlPilkarzy = new JMenuItem("Wy\u015Bwietl pi\u0142karzy");
		mntmWyswietlPilkarzy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0));
		mntmWyswietlPilkarzy.setIcon(new ImageIcon(BasicWindow.class.getResource("/resources/menu.png")));
		mnPikarze.add(mntmWyswietlPilkarzy);

		mntmWyszukajPilkarza = new JMenuItem("Wyszukaj pi\u0142karza");
		mntmWyszukajPilkarza.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.SHIFT_MASK));
		mntmWyszukajPilkarza.setIcon(new ImageIcon(BasicWindow.class.getResource("/resources/magnifying-glass-finder.png")));
		mnPikarze.add(mntmWyszukajPilkarza);

		mntmDodajPilkarza = new JMenuItem("Dodaj pi\u0142karza");
		mntmDodajPilkarza.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		mntmDodajPilkarza.setEnabled(false);
		mntmDodajPilkarza.setIcon(new ImageIcon(BasicWindow.class.getResource("/resources/003-user.png")));
		mnPikarze.add(mntmDodajPilkarza);

		mntmUsunPilkarza = new JMenuItem("Usu\u0144 pi\u0142karza");
		mntmUsunPilkarza.setEnabled(false);
		mntmUsunPilkarza.setIcon(new ImageIcon(BasicWindow.class.getResource("/resources/002-user-1.png")));
		mnPikarze.add(mntmUsunPilkarza);

		mntmModyfikujPilkarza = new JMenuItem("Modyfikuj pi\u0142karza");
		mntmModyfikujPilkarza.setEnabled(false);
		mntmModyfikujPilkarza.setIcon(new ImageIcon(BasicWindow.class.getResource("/resources/001-refresh-user-sign.png")));
		mnPikarze.add(mntmModyfikujPilkarza);

		mnKluby = new JMenu("Kluby");
		menuBar.add(mnKluby);

		mntmWyswietlKluby = new JMenuItem("Wy\u015Bwietl kluby");
		mntmWyswietlKluby.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, 0));
		mntmWyswietlKluby.setIcon(new ImageIcon(BasicWindow.class.getResource("/resources/menu.png")));
		mnKluby.add(mntmWyswietlKluby);

		mntmWyszukajKlub = new JMenuItem("Wyszukaj klub");
		mntmWyszukajKlub.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, InputEvent.SHIFT_MASK));
		mntmWyszukajKlub.setIcon(new ImageIcon(BasicWindow.class.getResource("/resources/magnifying-glass-finder.png")));
		mnKluby.add(mntmWyszukajKlub);

		mntmDodajKlub = new JMenuItem("Dodaj klub");
		mntmDodajKlub.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, InputEvent.CTRL_MASK));
		mntmDodajKlub.setEnabled(false);
		mntmDodajKlub.setIcon(new ImageIcon(BasicWindow.class.getResource("/resources/001-add.png")));
		mnKluby.add(mntmDodajKlub);

		mntmUsunKlub = new JMenuItem("Usu\u0144 klub");
		mntmUsunKlub.setEnabled(false);
		mntmUsunKlub.setIcon(new ImageIcon(BasicWindow.class.getResource("/resources/002-rubbish-bin.png")));
		mnKluby.add(mntmUsunKlub);

		mntmModyfikujKlub = new JMenuItem("Modyfikuj klub");
		mntmModyfikujKlub.setEnabled(false);
		mntmModyfikujKlub.setIcon(new ImageIcon(BasicWindow.class.getResource("/resources/007-update-arrow.png")));
		mnKluby.add(mntmModyfikujKlub);

		JMenu mnLigi = new JMenu("Ligi");
		mnLigi.setIcon(null);
		menuBar.add(mnLigi);

		mntmWyswietlLigi = new JMenuItem("Wy\u015Bwietl ligi");
		mntmWyswietlLigi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, 0));
		mntmWyswietlLigi.setIcon(new ImageIcon(BasicWindow.class.getResource("/resources/menu.png")));
		mnLigi.add(mntmWyswietlLigi);

		mntmWyszukajLig = new JMenuItem("Wyszukaj lig\u0119");
		mntmWyszukajLig.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.SHIFT_MASK));
		mntmWyszukajLig.setIcon(new ImageIcon(BasicWindow.class.getResource("/resources/magnifying-glass-finder.png")));
		mnLigi.add(mntmWyszukajLig);

		mntmDodajLige = new JMenuItem("Dodaj lig\u0119");
		mntmDodajLige.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
		mntmDodajLige.setIcon(new ImageIcon(BasicWindow.class.getResource("/resources/001-add.png")));
		mntmDodajLige.setEnabled(false);
		mnLigi.add(mntmDodajLige);

		mntmUsunLige = new JMenuItem("Usu\u0144 lig\u0119");
		mntmUsunLige.setIcon(new ImageIcon(BasicWindow.class.getResource("/resources/002-rubbish-bin.png")));
		mntmUsunLige.setEnabled(false);
		mnLigi.add(mntmUsunLige);

		mntmModyfikujLige = new JMenuItem("Modyfikuj lig\u0119");
		mntmModyfikujLige.setIcon(new ImageIcon(BasicWindow.class.getResource("/resources/007-update-arrow.png")));
		mntmModyfikujLige.setEnabled(false);
		mnLigi.add(mntmModyfikujLige);

		mnOpcje = new JMenu("Opcje");
		menuBar.add(mnOpcje);

		mntmAdmin = new JMenuItem("W\u0142\u0105cz tryb administratora");
		mntmAdmin.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0));
		mntmAdmin.setIcon(new ImageIcon(BasicWindow.class.getResource("/resources/admin-with-cogwheels.png")));
		mnOpcje.add(mntmAdmin);

		mnOpcje.addSeparator();

		mntmWyjscie = new JMenuItem("Wyj\u015Bcie");
		mntmWyjscie.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
		mntmWyjscie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		mntmWyjscie.setIcon(new ImageIcon(BasicWindow.class.getResource("/resources/exit-door.png")));
		mnOpcje.add(mntmWyjscie);
		

		popupMenu = new JPopupMenu();
		addPopup(table, popupMenu);

		mntmUsun = new JMenuItem("Usu\u0144");
		mntmUsun.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, 0));
		mntmUsun.setIcon(new ImageIcon(BasicWindow.class.getResource("/resources/002-rubbish-bin.png")));
		mntmUsun.setEnabled(false);
		popupMenu.add(mntmUsun);

		mntmModyfikuj = new JMenuItem("Modyfikuj");
		mntmModyfikuj.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, 0));
		mntmModyfikuj.setIcon(new ImageIcon(BasicWindow.class.getResource("/resources/007-update-arrow.png")));
		mntmModyfikuj.setEnabled(false);
		popupMenu.add(mntmModyfikuj);

	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	/////////////////////////////
	//// ACTION
	/////////////////////////////

	private void addEvents() {

		mntmWyswietlTransfery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				table.setModel(connector.getModelOfTransfers());
				table.setRowSorter(new TableRowSorter<DefaultTableModel>(connector.getModelOfTransfers()));
				BasicWindow.currentTableModel = 't';
				statusLabel.setText("Wyœwietlono listê transferów");
			}
		});

		mntmWyswietlPilkarzy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.setModel(connector.getModelOfPlayers());
				table.setRowSorter(new TableRowSorter<DefaultTableModel>(connector.getModelOfPlayers()));
				BasicWindow.currentTableModel = 'p';
				statusLabel.setText("Wyœwietlono listê pi³karzy");
			}
		});

		mntmWyswietlKluby.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.setModel(connector.getModelOfClubs());
				table.setRowSorter(new TableRowSorter<DefaultTableModel>(connector.getModelOfClubs()));
				BasicWindow.currentTableModel = 'c';
				statusLabel.setText("Wyœwietlono listê klubów");
				
			}
		});

		mntmWyswietlLigi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.setModel(connector.getModelOfLeagues());
				table.setRowSorter(new TableRowSorter<DefaultTableModel>(connector.getModelOfLeagues()));
				BasicWindow.currentTableModel = 'l';
				statusLabel.setText("Wyœwietlono listê lig");
			}
		});

		mntmDodajTransfer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddTransferWindow addTransferWindow = new AddTransferWindow(connector, BasicWindow.this);
			}
		});

		mntmAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (isAdminModeOn == false) {
					AdminWindow adminwindow = new AdminWindow(BasicWindow.this);
					
				} else if (isAdminModeOn == true) {
					AdminLeaveWindow adminLeaveWindow = new AdminLeaveWindow(BasicWindow.this);
				}
			}
		});

		mntmUsun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() == -1) {
					ErrorWindow error = new ErrorWindow("Nie zaznaczono rekordu", BasicWindow.this);
				} else {
					DeleteWindow deleteWindow = new DeleteWindow(connector, BasicWindow.this);
				}
			}
		});

		mntmUsunTransfer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() == -1) {
					ErrorWindow error = new ErrorWindow("Nie zaznaczono transferu", BasicWindow.this);
				} else {
					DeleteWindow deleteWindow = new DeleteWindow(connector, BasicWindow.this);
				}
			}
		});
		
		mntmModyfikuj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() == -1) {
					ErrorWindow error = new ErrorWindow("Nie zaznaczono rekordu", BasicWindow.this);
				} else {
					switch(BasicWindow.currentTableModel) {
					
					case 't':
						ModifyTransferWindow modify = new ModifyTransferWindow(connector, BasicWindow.this);
						break;
					case 'p':
						ModifyPlayerWindow modify1 = new ModifyPlayerWindow(connector, BasicWindow.this);
						break;
					case 'c':
						
					case 'l':
					}
					
				}
			}
		});

		mntmModyfikujTransfer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() == -1) {
					ErrorWindow error = new ErrorWindow("Nie zaznaczono transferu", BasicWindow.this);
				} else {
					ModifyTransferWindow modify = new ModifyTransferWindow(connector, BasicWindow.this);
				}
			}
		});
	
		mntmWyszukajTransfer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				SearchWindow searchWindow = new SearchWindow(connector, BasicWindow.this);
				BasicWindow.table.setModel(connector.getModelOfTransfers());
				BasicWindow.table.setRowSorter(new TableRowSorter<DefaultTableModel>(connector.getModelOfTransfers()));
				BasicWindow.currentTableModel = 't';
			}
		});
		
		mntmWyszukajPilkarza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchWindow searchWindow = new SearchWindow(connector, BasicWindow.this);
				table.setRowSorter(new TableRowSorter<DefaultTableModel>(connector.getModelOfPlayers()));
				BasicWindow.table.setModel(connector.getModelOfPlayers());
				BasicWindow.currentTableModel = 'p';
			}
		});
		
		mntmWyszukajKlub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchWindow searchWindow = new SearchWindow(connector, BasicWindow.this);
				table.setRowSorter(new TableRowSorter<DefaultTableModel>(connector.getModelOfClubs()));
				BasicWindow.table.setModel(connector.getModelOfClubs());
				BasicWindow.currentTableModel = 'c';
			}
		});
		
		mntmWyszukajLig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchWindow searchWindow = new SearchWindow(connector, BasicWindow.this);
				table.setRowSorter(new TableRowSorter<DefaultTableModel>(connector.getModelOfLeagues()));
				BasicWindow.table.setModel(connector.getModelOfLeagues());
				BasicWindow.currentTableModel = 'l';
			}
		});
		
		mntmDodajPilkarza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddPlayerWindow addPlayerWindow = new AddPlayerWindow(connector, BasicWindow.this);
			}
		});
		
		mntmWyjscie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		mntmModyfikujPilkarza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() == -1) {
					ErrorWindow error = new ErrorWindow("Nie zaznaczono pi³karza", BasicWindow.this);
				} else {
					ModifyPlayerWindow modify = new ModifyPlayerWindow(connector, BasicWindow.this);
				}
			}
		});
	}



}


