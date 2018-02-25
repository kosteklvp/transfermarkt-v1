package common;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import view.BasicWindow;
import view.ErrorWindow;

public class Connector {

	/////////////////////////////
	//// VARIABLES
	/////////////////////////////

	private Connection connectionWithDB;
	private Statement myStatement;
	private ResultSet myResultSet;
	private Properties properties = null;
	private String url = null;
	private String login = null;
	private String password = null;

	/////////////////////////////
	//// CONSTRUCTOR
	/////////////////////////////

	public Connector() {

		connectToDB();
	}

	/////////////////////////////
	//// METHODS
	/////////////////////////////

	private void connectToDB() {


		
		try {
			properties = new Properties();
			properties.load(new FileInputStream("data.properties"));
			url = properties.getProperty("dburl");
			login = properties.getProperty("user");
			password = properties.getProperty("password");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			connectionWithDB = DriverManager.getConnection("jdbc:mysql://sql11.freesqldatabase.com:3306/sql11223218", "sql11223218", "2SekpEkkhQ");
			myStatement = connectionWithDB.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Po³¹czono z " + url);
	}

	public DefaultTableModel getModelOfTransfers() {

		DefaultTableModel model = new DefaultTableModel() {

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};

		model.addColumn("IMIÊ");
		model.addColumn("NAZWISKO");
		model.addColumn("DATA TRANSFERU");
		model.addColumn("KWOTA TRANSFERU(€)");
		model.addColumn("WARTOŒÆ PI£KARZA(€)");
		model.addColumn("KLUB POZYSKUJ¥CY");

		if (BasicWindow.isAdminModeOn) {
			model.addColumn("ID");
		}
		
		int rowCount = 0;

		try {
			if (BasicWindow.isAdminModeOn) {
				myResultSet = myStatement.executeQuery(
						"SELECT p.imie_pilkarza, p.nazwisko_pilkarza, t.data_transferu, t.kwota, p.wartosc_pilkarza, k.nazwa_klubu, t.ID_transfer_PK "
								+ "FROM pilkarze AS p, transfery AS t, kluby AS k "
								+ "WHERE p.ID_pilkarz_PK = t.ID_pilkarz_FK AND t.ID_klub_poz_FK = k.ID_klub_PK " + "ORDER BY t.data_transferu DESC");

				while (myResultSet.next()) {

					model.insertRow(rowCount,
							new Object[] { 
									myResultSet.getString("imie_pilkarza"), 
									myResultSet.getString("nazwisko_pilkarza"),
									myResultSet.getString("data_transferu"), 
									myResultSet.getInt("kwota"), 
									myResultSet.getInt("wartosc_pilkarza"),
									myResultSet.getString("nazwa_klubu"), 
									myResultSet.getInt("ID_transfer_PK") });
					rowCount++;

				}
				
			} else {
				myResultSet = myStatement
						.executeQuery("SELECT p.imie_pilkarza, p.nazwisko_pilkarza, t.data_transferu, t.kwota, p.wartosc_pilkarza, k.nazwa_klubu "
								+ "FROM pilkarze AS p, transfery AS t, kluby AS k "
								+ "WHERE p.ID_pilkarz_PK = t.ID_pilkarz_FK AND t.ID_klub_poz_FK = k.ID_klub_PK " + "ORDER BY t.data_transferu DESC");

				while (myResultSet.next()) {

					model.insertRow(rowCount,
							new Object[] { 
									myResultSet.getString("imie_pilkarza"), 
									myResultSet.getString("nazwisko_pilkarza"),
									myResultSet.getString("data_transferu"), 
									myResultSet.getInt("kwota"), 
									myResultSet.getInt("wartosc_pilkarza"),
									myResultSet.getString("nazwa_klubu") });
					rowCount++;

				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		BasicWindow.currentTableModel = 't';
		
		return model;

	}

	public DefaultTableModel getModelOfPlayers() {

		DefaultTableModel model = new DefaultTableModel() {

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};


		model.addColumn("IMIÊ");
		model.addColumn("NAZWISKO");
		model.addColumn("POZYCJA");
		model.addColumn("NARODOWOŒÆ");
		model.addColumn("KLUB");
		model.addColumn("WARTOŒÆ(€)");

		if (BasicWindow.isAdminModeOn) {
			model.addColumn("ID");
		}

		int rowCount = 0;

		try {
			if (BasicWindow.isAdminModeOn) {
				myResultSet = myStatement.executeQuery(
						"SELECT p.imie_pilkarza, p.nazwisko_pilkarza, po.nazwa_pozycji, kr.nazwa_kraju, k.nazwa_klubu, p.wartosc_pilkarza, p.ID_pilkarz_PK "
								+ "FROM pilkarze AS p, pozycje AS po, kraje AS kr, kluby AS k "
								+ "WHERE po.ID_pozycja_PK = p.ID_pozycja_FK AND kr.ID_kraj_PK = p.ID_kraj_FK AND k.ID_klub_PK = p.ID_klub_FK "
								+ "ORDER BY p.wartosc_pilkarza DESC");

				while (myResultSet.next()) {

					model.insertRow(rowCount,
							new Object[] { 
									myResultSet.getString("imie_pilkarza"), 
									myResultSet.getString("nazwisko_pilkarza"),
									myResultSet.getString("nazwa_pozycji"), 
									myResultSet.getString("nazwa_kraju"), 
									myResultSet.getString("nazwa_klubu"),
									myResultSet.getInt("wartosc_pilkarza"), 
									myResultSet.getInt("ID_pilkarz_PK") });
					rowCount++;

				}
			} else {
				myResultSet = myStatement
						.executeQuery("SELECT p.imie_pilkarza, p.nazwisko_pilkarza, po.nazwa_pozycji, kr.nazwa_kraju, k.nazwa_klubu, p.wartosc_pilkarza "
								+ "FROM pilkarze AS p, pozycje AS po, kraje AS kr, kluby AS k "
								+ "WHERE po.ID_pozycja_PK = p.ID_pozycja_FK AND kr.ID_kraj_PK = p.ID_kraj_FK AND k.ID_klub_PK = p.ID_klub_FK "
								+ "ORDER BY p.wartosc_pilkarza DESC");

				while (myResultSet.next()) {

					model.insertRow(rowCount,
							new Object[] { 
									myResultSet.getString("imie_pilkarza"), 
									myResultSet.getString("nazwisko_pilkarza"),
									myResultSet.getString("nazwa_pozycji"), 
									myResultSet.getString("nazwa_kraju"), 
									myResultSet.getString("nazwa_klubu"),
									myResultSet.getInt("wartosc_pilkarza") });
					rowCount++;

				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BasicWindow.currentTableModel = 'p';
		return model;

	}

	public DefaultTableModel getModelOfClubs() {

		DefaultTableModel model = new DefaultTableModel() {

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};

		model.addColumn("NAZWA KLUBU");
		model.addColumn("DATA ZA£O¯ENIA");
		model.addColumn("STADION");
		model.addColumn("POJEMNOŒÆ");
		model.addColumn("TRENER");
		model.addColumn("LIGA");

		if (BasicWindow.isAdminModeOn) {
			model.addColumn("ID");
		}

		int rowCount = 0;

		try {
			if (BasicWindow.isAdminModeOn) {
				myResultSet = myStatement
						.executeQuery("SELECT k.nazwa_klubu, k.data_zalozenia, s.nazwa_stadionu, s.pojemnosc, tr.nazwisko_trenera, l.nazwa_ligi, k.ID_klub_PK "
								+ "FROM kluby AS k, kraje AS kr, stadiony AS s, trenerzy AS tr, ligi AS l "
								+ "WHERE kr.ID_kraj_PK = k.ID_kraj_FK AND k.ID_stadion_FK = s.ID_stadion_PK AND k.ID_trener_FK = tr.ID_trener_PK AND k.ID_liga_FK = l.ID_liga_PK");

				while (myResultSet.next()) {

					model.insertRow(rowCount,
							new Object[] { 
									myResultSet.getString("nazwa_klubu"), 
									myResultSet.getString("data_zalozenia"),
									myResultSet.getString("nazwa_stadionu"),
									myResultSet.getInt("pojemnosc"), 
									myResultSet.getString("nazwisko_trenera"),
									myResultSet.getString("nazwa_ligi"), 
									myResultSet.getInt("ID_klub_PK") });
					rowCount++;

				}
			} else {
				myResultSet = myStatement
						.executeQuery("SELECT k.nazwa_klubu, k.data_zalozenia, s.nazwa_stadionu, s.pojemnosc, tr.nazwisko_trenera, l.nazwa_ligi "
								+ "FROM kluby AS k, kraje AS kr, stadiony AS s, trenerzy AS tr, ligi AS l "
								+ "WHERE kr.ID_kraj_PK = k.ID_kraj_FK AND k.ID_stadion_FK = s.ID_stadion_PK AND k.ID_trener_FK = tr.ID_trener_PK AND k.ID_liga_FK = l.ID_liga_PK");

				while (myResultSet.next()) {

					model.insertRow(rowCount,
							new Object[] { 
									myResultSet.getString("nazwa_klubu"), 
									myResultSet.getString("data_zalozenia"),
									myResultSet.getString("nazwa_stadionu"), 
									myResultSet.getInt("pojemnosc"), 
									myResultSet.getString("nazwisko_trenera"),
									myResultSet.getString("nazwa_ligi") });
					rowCount++;

				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BasicWindow.currentTableModel = 'c';
		return model;

	}

	public DefaultTableModel getModelOfLeagues() {

		DefaultTableModel model = new DefaultTableModel() {

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};

		model.addColumn("NAZWA LIGI");
		model.addColumn("SZCZEBEL");
		model.addColumn("ILOŒÆ DRU¯YN");
		model.addColumn("KRAJ");

		if (BasicWindow.isAdminModeOn) {
			model.addColumn("ID");
		}

		int rowCount = 0;

		try {
			if (BasicWindow.isAdminModeOn) {
				myResultSet = myStatement.executeQuery("SELECT l.nazwa_ligi, l.szczebel, l.ilosc_druzyn, kr.nazwa_kraju, l.ID_liga_PK "
						+ "FROM ligi AS l, kraje AS kr " 
						+ "WHERE l.ID_kraj_FK = kr.ID_kraj_PK");

				while (myResultSet.next()) {

					model.insertRow(rowCount, new Object[] { 
							myResultSet.getString("nazwa_ligi"), 
							myResultSet.getString("szczebel"),
							myResultSet.getString("ilosc_druzyn"), 
							myResultSet.getString("nazwa_kraju"), 
							myResultSet.getInt("ID_liga_PK") });
					rowCount++;

				}

			} else {
				myResultSet = myStatement.executeQuery("SELECT l.nazwa_ligi, l.szczebel, l.ilosc_druzyn, kr.nazwa_kraju " + "FROM ligi AS l, kraje AS kr "
						+ "WHERE l.ID_kraj_FK = kr.ID_kraj_PK");

				while (myResultSet.next()) {

					model.insertRow(rowCount, new Object[] { 
							myResultSet.getString("nazwa_ligi"), 
							myResultSet.getString("szczebel"),
							myResultSet.getString("ilosc_druzyn"), 
							myResultSet.getString("nazwa_kraju") });
					rowCount++;

				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BasicWindow.currentTableModel = 'l';
		return model;

	}

	public DefaultTableModel getModelOfSearchTransferResults(String search) {

		DefaultTableModel model = new DefaultTableModel() {

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};

		model.addColumn("IMIÊ");
		model.addColumn("NAZWISKO");
		model.addColumn("DATA TRANSFERU");
		model.addColumn("KWOTA TRANSFERU(€)");
		model.addColumn("WARTOŒÆ PI£KARZA(€)");
		model.addColumn("KLUB POZYSKUJ¥CY");

		if (BasicWindow.isAdminModeOn) {
			model.addColumn("ID");
		}

		int rowCount = 0;

		try {
			if (BasicWindow.isAdminModeOn) {
				myResultSet = myStatement.executeQuery(
						"SELECT p.imie_pilkarza, p.nazwisko_pilkarza, t.data_transferu, t.kwota, p.wartosc_pilkarza, k.nazwa_klubu, t.ID_transfer_PK "
								+ "FROM pilkarze AS p, transfery AS t, kluby AS k "
								+ "WHERE p.ID_pilkarz_PK = t.ID_pilkarz_FK AND t.ID_klub_poz_FK = k.ID_klub_PK AND "
								+ "CONCAT(p.imie_pilkarza, p.nazwisko_pilkarza, k.nazwa_klubu, t.data_transferu) LIKE '%" + search + "%' "
								+ "ORDER BY t.data_transferu DESC");

				while (myResultSet.next()) {

					model.insertRow(rowCount,
							new Object[] { 
									myResultSet.getString("imie_pilkarza"), 
									myResultSet.getString("nazwisko_pilkarza"),
									myResultSet.getString("data_transferu"), 
									myResultSet.getInt("kwota"), 
									myResultSet.getInt("wartosc_pilkarza"),
									myResultSet.getString("nazwa_klubu"), 
									myResultSet.getInt("ID_transfer_PK") });
					rowCount++;
					System.out.println("cp1");
				}
			} else {
				myResultSet = myStatement
						.executeQuery("SELECT p.imie_pilkarza, p.nazwisko_pilkarza, t.data_transferu, t.kwota, p.wartosc_pilkarza, k.nazwa_klubu "
								+ "FROM pilkarze AS p, transfery AS t, kluby AS k "
								+ "WHERE p.ID_pilkarz_PK = t.ID_pilkarz_FK AND t.ID_klub_poz_FK = k.ID_klub_PK AND "
								+ "CONCAT(p.imie_pilkarza, p.nazwisko_pilkarza, k.nazwa_klubu, t.data_transferu) LIKE '%" + search + "%' "
								+ "ORDER BY t.data_transferu DESC");

				while (myResultSet.next()) {

					model.insertRow(rowCount,
							new Object[] { 
									myResultSet.getString("imie_pilkarza"), 
									myResultSet.getString("nazwisko_pilkarza"),
									myResultSet.getString("data_transferu"), 
									myResultSet.getInt("kwota"), 
									myResultSet.getInt("wartosc_pilkarza"),
									myResultSet.getString("nazwa_klubu") });
					rowCount++;


				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BasicWindow.currentTableModel = 't';
		return model;

	}
	
	public DefaultTableModel getModelOfSearchPlayerResults(String search) {

		DefaultTableModel model = new DefaultTableModel() {

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};

		model.addColumn("IMIÊ");
		model.addColumn("NAZWISKO");
		model.addColumn("POZYCJA");
		model.addColumn("NARODOWOŒÆ");
		model.addColumn("KLUB");
		model.addColumn("WARTOŒÆ");

		if (BasicWindow.isAdminModeOn) {
			model.addColumn("ID");
		}

		int rowCount = 0;

		try {
			if (BasicWindow.isAdminModeOn) {
				myResultSet = myStatement.executeQuery(	
								"SELECT p.imie_pilkarza, p.nazwisko_pilkarza, po.nazwa_pozycji, kr.nazwa_kraju, k.nazwa_klubu, p.wartosc_pilkarza, p.ID_pilkarz_PK "
									+ "FROM pilkarze AS p, pozycje AS po, kraje AS kr, kluby AS k "
									+ "WHERE po.ID_pozycja_PK = p.ID_pozycja_FK AND kr.ID_kraj_PK = p.ID_kraj_FK AND k.ID_klub_PK = p.ID_klub_FK AND "
									+ "CONCAT(p.imie_pilkarza, p.nazwisko_pilkarza, po.nazwa_pozycji, kr.nazwa_kraju, k.nazwa_klubu) LIKE '%" + search + "%' "
									+ "ORDER BY p.wartosc_pilkarza DESC");

				while (myResultSet.next()) {

					model.insertRow(rowCount,
							new Object[] { 
									myResultSet.getString("imie_pilkarza"), 
									myResultSet.getString("nazwisko_pilkarza"),
									myResultSet.getString("nazwa_pozycji"), 
									myResultSet.getString("nazwa_kraju"), 
									myResultSet.getString("nazwa_klubu"),
									myResultSet.getInt("wartosc_pilkarza"), 
									myResultSet.getInt("ID_pilkarz_PK") });
					rowCount++;

				}
			} else {
				myResultSet = myStatement.executeQuery(
								"SELECT p.imie_pilkarza, p.nazwisko_pilkarza, po.nazwa_pozycji, kr.nazwa_kraju, k.nazwa_klubu, p.wartosc_pilkarza "
										+ "FROM pilkarze AS p, pozycje AS po, kraje AS kr, kluby AS k "
										+ "WHERE po.ID_pozycja_PK = p.ID_pozycja_FK AND kr.ID_kraj_PK = p.ID_kraj_FK AND k.ID_klub_PK = p.ID_klub_FK AND "
										+ "CONCAT(p.imie_pilkarza, p.nazwisko_pilkarza, po.nazwa_pozycji, kr.nazwa_kraju, k.nazwa_klubu) LIKE '%" + search + "%' "
										+ "ORDER BY p.wartosc_pilkarza DESC");

				while (myResultSet.next()) {

					model.insertRow(rowCount,
							new Object[] { 
									myResultSet.getString("imie_pilkarza"), 
									myResultSet.getString("nazwisko_pilkarza"),
									myResultSet.getString("nazwa_pozycji"), 
									myResultSet.getString("nazwa_kraju"), 
									myResultSet.getString("nazwa_klubu"),
									myResultSet.getInt("wartosc_pilkarza") });
					rowCount++;

				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BasicWindow.currentTableModel = 'p';
		return model;

	}

	public DefaultTableModel getModelOfSearchClubResults(String search) {

		DefaultTableModel model = new DefaultTableModel() {

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};

		model.addColumn("NAZWA KLUBU");
		model.addColumn("DATA ZA£O¯ENIA");
		model.addColumn("STADION");
		model.addColumn("POJEMNOŒÆ");
		model.addColumn("TRENER");
		model.addColumn("LIGA");

		if (BasicWindow.isAdminModeOn) {
			model.addColumn("ID");
		}

		int rowCount = 0;

		try {
			if (BasicWindow.isAdminModeOn) {
				myResultSet = myStatement
						.executeQuery("SELECT k.nazwa_klubu, k.data_zalozenia, s.nazwa_stadionu, s.pojemnosc, tr.nazwisko_trenera, l.nazwa_ligi, k.ID_klub_PK "
								+ "FROM kluby AS k, kraje AS kr, stadiony AS s, trenerzy AS tr, ligi AS l "
								+ "WHERE kr.ID_kraj_PK = k.ID_kraj_FK AND k.ID_stadion_FK = s.ID_stadion_PK AND k.ID_trener_FK = tr.ID_trener_PK AND k.ID_liga_FK = l.ID_liga_PK AND "
								+ "CONCAT(k.nazwa_klubu, s.nazwa_stadionu, tr.nazwisko_trenera) LIKE '%" + search + "%' ");
				
				while (myResultSet.next()) {

					model.insertRow(rowCount,
							new Object[] { 
									myResultSet.getString("nazwa_klubu"), 
									myResultSet.getString("data_zalozenia"),
									myResultSet.getString("nazwa_stadionu"),
									myResultSet.getInt("pojemnosc"), 
									myResultSet.getString("nazwisko_trenera"),
									myResultSet.getString("nazwa_ligi"), 
									myResultSet.getInt("ID_klub_PK") });
					rowCount++;

				}
			} else {
				myResultSet = myStatement
						.executeQuery("SELECT k.nazwa_klubu, k.data_zalozenia, s.nazwa_stadionu, s.pojemnosc, tr.nazwisko_trenera, l.nazwa_ligi "
								+ "FROM kluby AS k, kraje AS kr, stadiony AS s, trenerzy AS tr, ligi AS l "
								+ "WHERE kr.ID_kraj_PK = k.ID_kraj_FK AND k.ID_stadion_FK = s.ID_stadion_PK AND k.ID_trener_FK = tr.ID_trener_PK AND k.ID_liga_FK = l.ID_liga_PK AND "
								+ "CONCAT(k.nazwa_klubu, s.nazwa_stadionu, tr.nazwisko_trenera) LIKE '%" + search + "%' ");

				while (myResultSet.next()) {

					model.insertRow(rowCount,
							new Object[] { 
									myResultSet.getString("nazwa_klubu"), 
									myResultSet.getString("data_zalozenia"),
									myResultSet.getString("nazwa_stadionu"),
									myResultSet.getInt("pojemnosc"), 
									myResultSet.getString("nazwisko_trenera"),
									myResultSet.getString("nazwa_ligi") });
					rowCount++;

				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BasicWindow.currentTableModel = 'c';
		return model;
	}
 
	public DefaultTableModel getModelOfSearchLeagueResults(String search) {
		

		DefaultTableModel model = new DefaultTableModel() {

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};

		model.addColumn("NAZWA LIGI");
		model.addColumn("SZCZEBEL");
		model.addColumn("ILOŒÆ DRU¯YN");
		model.addColumn("KRAJ");

		if (BasicWindow.isAdminModeOn) {
			model.addColumn("ID");
		}

		int rowCount = 0;

		try {
			if (BasicWindow.isAdminModeOn) {
				
				myResultSet = myStatement
						.executeQuery("SELECT l.nazwa_ligi, l.szczebel, l.ilosc_druzyn, kr.nazwa_kraju, l.ID_liga_PK "
								+ "FROM ligi AS l, kraje AS kr " 
								+ "WHERE l.ID_kraj_FK = kr.ID_kraj_PK AND "
								+ "CONCAT(l.nazwa_ligi, kr.nazwa_kraju) LIKE '%" + search + "%' ");
				
				while (myResultSet.next()) {

					model.insertRow(rowCount, new Object[] { 
							myResultSet.getString("nazwa_ligi"), 
							myResultSet.getString("szczebel"),
							myResultSet.getString("ilosc_druzyn"), 
							myResultSet.getString("nazwa_kraju"), 
							myResultSet.getInt("ID_liga_PK") });
					rowCount++;

				}
			} else {
				
				myResultSet = myStatement
						.executeQuery("SELECT l.nazwa_ligi, l.szczebel, l.ilosc_druzyn, kr.nazwa_kraju "
								+ "FROM ligi AS l, kraje AS kr " 
								+ "WHERE l.ID_kraj_FK = kr.ID_kraj_PK AND "
								+ "CONCAT(l.nazwa_ligi, kr.nazwa_kraju) LIKE '%" + search + "%' ");

				while (myResultSet.next()) {

					model.insertRow(rowCount, new Object[] { 
							myResultSet.getString("nazwa_ligi"), 
							myResultSet.getString("szczebel"),
							myResultSet.getString("ilosc_druzyn"), 
							myResultSet.getString("nazwa_kraju") });
					rowCount++;

				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BasicWindow.currentTableModel = 'l';
		return model;
	}
	
	public DefaultComboBoxModel<String> getComboBoxModelOfPlayers() {

		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();

		try {
			myResultSet = myStatement.executeQuery(
				"SELECT ID_pilkarz_PK, imie_pilkarza, nazwisko_pilkarza " 
					+ "FROM pilkarze " 
					+ "ORDER BY nazwisko_pilkarza");

			while (myResultSet.next()) {

				model.addElement(
						myResultSet.getString("nazwisko_pilkarza") + " "
						+ myResultSet.getString("imie_pilkarza") + " (ID:"
						+ myResultSet.getInt("ID_pilkarz_PK") + ")");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}
	
	public DefaultComboBoxModel<String> getComboBoxModelOfPositions() {

		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();

		try {
			myResultSet = myStatement.executeQuery(
				"SELECT ID_pozycja_PK, nazwa_pozycji " 
					+ "FROM pozycje " 
					+ "ORDER BY nazwa_pozycji");

			while (myResultSet.next()) {

				model.addElement(
						myResultSet.getString("nazwa_pozycji") + " (ID:"
						+ myResultSet.getInt("ID_pozycja_PK") + ")");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}

	public DefaultComboBoxModel<String> getComboBoxModelOfCountries() {

		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();

		try {
			myResultSet = myStatement.executeQuery(
				"SELECT ID_kraj_PK, nazwa_kraju " 
					+ "FROM kraje " 
					+ "ORDER BY nazwa_kraju");

			while (myResultSet.next()) {

				model.addElement(
						myResultSet.getString("nazwa_kraju") + " (ID:"
						+ myResultSet.getInt("ID_kraj_PK") + ")");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}
	
	public DefaultComboBoxModel<String> getComboBoxModelOfClubs() {

		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();

		try {
			myResultSet = myStatement.executeQuery(
					"SELECT ID_klub_PK, nazwa_klubu "
							+ "FROM kluby "
							+ "ORDER BY nazwa_klubu");

			while (myResultSet.next()) {

				model.addElement(
						myResultSet.getString("nazwa_klubu") + " (ID:" + 
						myResultSet.getInt("ID_klub_PK") + ")");

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}

	public void addTransfer(String idPilkarza, String idKlubPoz, String kwota, String data) {

		String idKlubOdd = null;

		try {

			myResultSet = myStatement.executeQuery("SELECT ID_klub_FK FROM pilkarze " + "WHERE ID_pilkarz_PK = 3 ");

			while (myResultSet.next()) {

				idKlubOdd = myResultSet.getString("ID_klub_FK");
			}

			myStatement
					.executeUpdate("INSERT INTO `transfery` (`ID_transfer_PK`, `kwota`, `data_transferu`, `ID_klub_poz_FK`, `ID_klub_odd_FK`, `ID_pilkarz_FK`) "
							+ "VALUES (NULL, '" + kwota + "', '" + data + "', '" + idKlubPoz + "', '" + idKlubOdd + "', '" + idPilkarza + "')");

			myStatement.executeUpdate("UPDATE `pilkarze` SET `ID_klub_FK` = '" + idKlubPoz + "' WHERE `pilkarze`.`ID_pilkarz_PK` = " + idPilkarza);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void addPlayer(String imie, String nazwisko, String wartosc, String pozycja, String kraj, String klub) {
		

		try {
			myStatement
					.executeUpdate("INSERT INTO `pilkarze` (`ID_pilkarz_PK`, `imie_pilkarza`, `nazwisko_pilkarza`, `wartosc_pilkarza`, `ID_pozycja_FK`, `ID_kraj_FK`, `ID_klub_FK`) "
							+ "VALUES (NULL, '" + imie + "', '" + nazwisko + "', '" + wartosc + "', '" + pozycja + "', '" + kraj + "', '" + klub + "')");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public String getPlayerName(String idPilkarza) {

		String name = null;

		try {
			myResultSet = myStatement.executeQuery(
					"SELECT imie_pilkarza, nazwisko_pilkarza FROM pilkarze " 
					+ "WHERE ID_pilkarz_PK = " + idPilkarza);

			while (myResultSet.next()) {

				name = myResultSet.getString("imie_pilkarza") + " " + myResultSet.getString("nazwisko_pilkarza");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return name;
	}

	public String getClubName(String idKlubu) {

		String name = null;

		try {
			myResultSet = myStatement.executeQuery("SELECT nazwa_klubu FROM kluby " + "WHERE ID_klub_PK = " + idKlubu);

			while (myResultSet.next()) {

				name = myResultSet.getString("nazwa_klubu");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return name;
	}

	public int getRowCountOfSearchResults(String search) {

		int rowCount = 0;

		switch (BasicWindow.currentTableModel) {
		
		case 't':
			
			try {
				myResultSet = myStatement
						.executeQuery("SELECT p.imie_pilkarza, p.nazwisko_pilkarza, t.data_transferu, t.kwota, p.wartosc_pilkarza, k.nazwa_klubu "
								+ "FROM pilkarze AS p, transfery AS t, kluby AS k "
								+ "WHERE p.ID_pilkarz_PK = t.ID_pilkarz_FK AND t.ID_klub_poz_FK = k.ID_klub_PK AND "
								+ "CONCAT(p.imie_pilkarza, p.nazwisko_pilkarza, k.nazwa_klubu, t.data_transferu) LIKE '%" + search + "%' "
								+ "ORDER BY t.data_transferu DESC");

				while (myResultSet.next()) {

					rowCount++;
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return rowCount;

		case 'p':

			try {
				myResultSet = myStatement
						.executeQuery("SELECT p.imie_pilkarza, p.nazwisko_pilkarza, po.nazwa_pozycji, kr.nazwa_kraju, k.nazwa_klubu, p.wartosc_pilkarza "
								+ "FROM pilkarze AS p, pozycje AS po, kraje AS kr, kluby AS k "
								+ "WHERE po.ID_pozycja_PK = p.ID_pozycja_FK AND kr.ID_kraj_PK = p.ID_kraj_FK AND k.ID_klub_PK = p.ID_klub_FK AND "
								+ "CONCAT(p.imie_pilkarza, p.nazwisko_pilkarza, po.nazwa_pozycji, kr.nazwa_kraju, k.nazwa_klubu) LIKE '%" + search + "%' "
								+ "ORDER BY p.wartosc_pilkarza DESC");

				while (myResultSet.next()) {

					rowCount++;
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return rowCount;
			
		case 'c':
		
			try {
				myResultSet = myStatement
						.executeQuery("SELECT k.nazwa_klubu, k.data_zalozenia, s.nazwa_stadionu, s.pojemnosc, tr.nazwisko_trenera, l.nazwa_ligi, k.ID_klub_PK "
								+ "FROM kluby AS k, kraje AS kr, stadiony AS s, trenerzy AS tr, ligi AS l "
								+ "WHERE kr.ID_kraj_PK = k.ID_kraj_FK AND k.ID_stadion_FK = s.ID_stadion_PK AND k.ID_trener_FK = tr.ID_trener_PK AND k.ID_liga_FK = l.ID_liga_PK AND "
								+ "CONCAT(k.nazwa_klubu, s.nazwa_stadionu, tr.nazwisko_trenera) LIKE '%" + search + "%' ");

				while (myResultSet.next()) {

					rowCount++;
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return rowCount;
			
		case 'l':
		
			try {
				myResultSet = myStatement
						.executeQuery("SELECT l.nazwa_ligi, l.szczebel, l.ilosc_druzyn, kr.nazwa_kraju, l.ID_liga_PK "
								+ "FROM ligi AS l, kraje AS kr " 
								+ "WHERE l.ID_kraj_FK = kr.ID_kraj_PK AND "
								+ "CONCAT(l.nazwa_ligi, kr.nazwa_kraju) LIKE '%" + search + "%' ");

				while (myResultSet.next()) {

					rowCount++;
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return rowCount;
			
		}
		
		return 0;
	}

	public String getComboBoxPlayerItem() {

		int row = BasicWindow.table.getSelectedRow();

		String comboBoxPlayer = null;
		String firstName = BasicWindow.table.getValueAt(row, 0).toString();
		String lastName = BasicWindow.table.getValueAt(row, 1).toString();

		try {
			myResultSet = myStatement.executeQuery(
					"SELECT imie_pilkarza, nazwisko_pilkarza, ID_pilkarz_PK " + "FROM pilkarze " 
					+ "WHERE imie_pilkarza = '"
					+ firstName + "' AND nazwisko_pilkarza = '" + lastName + "'");

			while (myResultSet.next()) {
				comboBoxPlayer = myResultSet.getString("nazwisko_pilkarza") + " " + myResultSet.getString("imie_pilkarza") + " (ID:"
						+ myResultSet.getInt("ID_pilkarz_PK") + ")";
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return comboBoxPlayer;

	}
	
	public String getTextFieldName() {

		int row = BasicWindow.table.getSelectedRow();

		String text = BasicWindow.table.getValueAt(row, 0).toString();

		return text;

	}
	
	public String getTextFieldSurname() {

		int row = BasicWindow.table.getSelectedRow();

		String text = BasicWindow.table.getValueAt(row, 1).toString();

		return text;

	}

	public String getTextFieldValue() {

		int row = BasicWindow.table.getSelectedRow();

		String text = BasicWindow.table.getValueAt(row, 5).toString();

		return text;

	}
	
	public String getComboBoxPositionItem() {

		int row = BasicWindow.table.getSelectedRow();

		String positionName = BasicWindow.table.getValueAt(row, 2).toString();
		String comboBoxPosition = null;

		try {
			myResultSet = myStatement.executeQuery("SELECT ID_pozycja_PK, nazwa_pozycji "  + "FROM pozycje "  + "WHERE nazwa_pozycji = '" + positionName + "'");

			
			
			while (myResultSet.next()) {
				comboBoxPosition = myResultSet.getString("nazwa_pozycji") + " (ID:" + myResultSet.getInt("ID_pozycja_PK") + ")";
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return comboBoxPosition;
	}

	public String getComboBoxClubItem() {

		int row = BasicWindow.table.getSelectedRow();

		String clubName = BasicWindow.table.getValueAt(row, 5).toString();
		String comboBoxClub = null;

		try {
			myResultSet = myStatement.executeQuery("SELECT nazwa_klubu, ID_klub_PK " + "FROM kluby " + "WHERE nazwa_klubu = '" + clubName + "'");

			while (myResultSet.next()) {
				comboBoxClub = myResultSet.getString("nazwa_klubu") + " (ID:" + myResultSet.getInt("ID_klub_PK") + ")";
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return comboBoxClub;
	}
	
	public String getComboBoxClubItemForPlayers() {

		int row = BasicWindow.table.getSelectedRow();

		String clubName = BasicWindow.table.getValueAt(row, 4).toString();
		String comboBoxClub = null;

		try {
			myResultSet = myStatement.executeQuery("SELECT nazwa_klubu, ID_klub_PK " + "FROM kluby " + "WHERE nazwa_klubu = '" + clubName + "'");

			while (myResultSet.next()) {
				comboBoxClub = myResultSet.getString("nazwa_klubu") + " (ID:" + myResultSet.getInt("ID_klub_PK") + ")";
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return comboBoxClub;
	}

	public String getComboBoxCountryItem() {

		int row = BasicWindow.table.getSelectedRow();

		String countryName = BasicWindow.table.getValueAt(row, 3).toString();
		String comboBoxClub = null;
		
		try {
			myResultSet = myStatement.executeQuery("SELECT ID_kraj_PK, nazwa_kraju " + "FROM kraje " + "WHERE nazwa_kraju = '" + countryName + "'");
			
			while (myResultSet.next()) {
				comboBoxClub = myResultSet.getString("nazwa_kraju") + " (ID:" + myResultSet.getInt("ID_kraj_PK") + ")";
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return comboBoxClub;
	}

	public String getTextFieldPrice() {

		int row = BasicWindow.table.getSelectedRow();

		String text = BasicWindow.table.getValueAt(row, 3).toString();

		return text;
	}

	public String getTextFieldDate() {

		int row = BasicWindow.table.getSelectedRow();

		String text = BasicWindow.table.getValueAt(row, 2).toString();

		return text;
	}

	public void deleteTransfer() {

		int row = BasicWindow.table.getSelectedRow();

		String id = BasicWindow.table.getValueAt(row, 6).toString();
		String name = BasicWindow.table.getValueAt(row, 0).toString() + " " + BasicWindow.table.getValueAt(row, 1).toString();
		String club = BasicWindow.table.getValueAt(row, 5).toString();

		BasicWindow.statusLabel.setText("Usuniêto transfer pi³karza " + name + " do klubu " + club);

		try {
			myStatement.executeUpdate("DELETE FROM transfery WHERE ID_transfer_PK = " + id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BasicWindow.table.setModel(getModelOfTransfers());

	}

	public void deletePlayer() {

		int row = BasicWindow.table.getSelectedRow();

		String id = BasicWindow.table.getValueAt(row, 6).toString();
		String name = BasicWindow.table.getValueAt(row, 0).toString() + " " + BasicWindow.table.getValueAt(row, 1).toString();
		

		BasicWindow.statusLabel.setText("Usuniêto pi³karza " + name + "");

		try {
			myStatement.executeUpdate("DELETE FROM pilkarze WHERE ID_pilkarz_PK = " + id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BasicWindow.table.setModel(getModelOfPlayers());

	}
	
	public String getTransferID() {

		int row = BasicWindow.table.getSelectedRow();

		String text = BasicWindow.table.getValueAt(row, 6).toString();

		return text;

	}

	public String getPlayerID() {

		int row = BasicWindow.table.getSelectedRow();

		String text = BasicWindow.table.getValueAt(row, 6).toString();

		return text;

	}
	
	public void modifyTransfer(String idPilkarza, String idKlubPoz, String kwota, String data, String id) {

		try {
			myStatement.executeUpdate("UPDATE `transfery` SET `kwota` = '" + kwota + "', `data_transferu` = '" + data
					+ "', `ID_klub_poz_FK` = '" + idKlubPoz + "', `ID_pilkarz_FK` = '" + idPilkarza 
					+ "' WHERE `transfery`.`ID_transfer_PK` = " + id);
			;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void modifyPlayer(String imie, String nazwisko, String wartosc, String pozycja, String kraj, String klub, String id) {

		try {
			myStatement.executeUpdate("UPDATE `pilkarze` SET `imie_pilkarza` = '" + imie + "', `nazwisko_pilkarza` = '" + nazwisko + "', `wartosc_pilkarza` = '"
					+ wartosc + "', `ID_pozycja_FK` = '" + pozycja + "', `ID_kraj_FK` = '" + kraj + "', `ID_klub_FK` = '" + klub
					+ "' WHERE `pilkarze`.`ID_pilkarz_PK` = " + id);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
}
