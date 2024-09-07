package Controller;

import Model.*;
import View.RegisterView;
import View.StartPageView;

import javax.swing.*;

import java.util.List;

public class Application {
	public static Integer userID = null;
	public static void main(String[] args) {
		DBTables dbTables = new DBTables();
		dbTables.createDBTables();
		SwingUtilities.invokeLater(() -> {
			StartPageView startPageView = new StartPageView();
		});
	}
}
