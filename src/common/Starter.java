package common;

import javax.swing.UIManager;

import view.BasicWindow;

public class Starter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch(Exception e) {
			  System.out.println("Error setting native LAF: " + e);
			}
	
		Connector connector = new Connector();
		BasicWindow window = new BasicWindow(connector);
	
	}

	
	/*
	 * Autor: Piotr Kostañski 32INF-SP 2018
	*/
}
