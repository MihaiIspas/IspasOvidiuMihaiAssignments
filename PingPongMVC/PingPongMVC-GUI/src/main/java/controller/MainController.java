package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import business.Business;
import dto.MatchDTO;
import dto.PlayerDTO;
import javafx.event.ActionEvent;
import view.AdminWindow;
import view.MainWindow;
import view.PlayerWindow;

@SuppressWarnings("restriction")
public class MainController {
	
	@FXML
    private TextField userNameTf;

    @FXML
    private PasswordField passwordTf;

    @FXML
    private Label errorLabel;
    
    public static String currentMatch;
    
    static int type;
    
    static int matchID;
    
    public static Business b;
    
    static String name;
    
    static String userName;
    
    public MainController() throws SQLException, IOException {
    	b=new Business();
    	b.getTournamentsFromDB();
    	b.getPlayersFromDB();
    }

    @FXML
    void signInAction(ActionEvent event) throws IOException, SQLException {
    	errorLabel.setText("User name or password incorrect!");
    	type=b.signIn(userNameTf.getText(), passwordTf.getText());
    	if(type==0) {
    		if(MainWindow.mainPane.getChildren().contains(errorLabel)) {
    			MainWindow.mainPane.getChildren().remove(errorLabel);
    		}
    		MainWindow.mainPane.getChildren().add(errorLabel);
    	}
    	else if(type==1) {
        	userNameTf.clear();
        	passwordTf.clear();
    		AdminWindow.showAdminWindow();
    		MainWindow.mainPane.getChildren().remove(errorLabel);
    		MainWindow.stage.setScene(MainWindow.scene);
    	}
    	else {
        	userNameTf.clear();
        	passwordTf.clear();
    		//PlayerController player=new PlayerController();
    		updateMatch();
    		//PlayerWindow.stage=new Stage();
    		PlayerWindow.showTournaments();
    		PlayerWindow.stage.show();
    		MainWindow.mainPane.getChildren().remove(errorLabel);
    		MainWindow.stage.setScene(MainWindow.scene);
    	}
    	
    }
    
    public static void updateMatch() {
    	String matchName = null;
    	for(PlayerDTO p:b.playerList) {
			if(p.getID()==type) {
				name="Your name : "+p.getName();
				userName="Your user name : "+p.getUserName();
			}
		}
		if(b.isPlaying(type)) {
			matchName=b.findMatchByPlayerID(type).getName();
			matchID=b.getMatchByPlayerID(type).getID();
		}
		if(matchName!=null) {
			//System.out.println(matchName);
			//PlayerController.currentTournamentsLabel=new Label();
			currentMatch="Current match : "+matchName;
			//PlayerController.currentTournamentsLabel.setText(currentMatch);
			//player.currentTournamentsLabel.setText(currentMatch);
		}
		else {
			//PlayerController.currentTournamentsLabel=new Label();
			currentMatch="No match enrolled in";
			//PlayerController.currentTournamentsLabel.setText("No match enrolled in");
			//PlayerController.currentTournamentsLabel.setText(currentMatch);
		}
    }


}
