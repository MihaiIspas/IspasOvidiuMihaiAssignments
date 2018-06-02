package controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import view.AdminWindow;

import java.io.IOException;

import business.Business;
import javafx.event.ActionEvent;

@SuppressWarnings("restriction")
public class AdminMainController {
	
	//public static Business b;
	
	public AdminMainController() {
		//b=new Business();
	}

    @FXML
    void playersAction(ActionEvent event) throws IOException {
    	AdminWindow.showPlayersWindow();
    	AdminWindow.stage.show();
    }

    @FXML
    void tournamentsAction(ActionEvent event) throws IOException {
    	AdminWindow.showTournaments();
    	AdminWindow.stage1.show();
    }

}