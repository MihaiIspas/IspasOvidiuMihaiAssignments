package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import business.Business;
import dto.MatchDTO;
import dto.PlayerDTO;
import dto.TournamentDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import view.AdminWindow;
import view.PlayerWindow;
import javafx.fxml.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

@SuppressWarnings("restriction")
public class PlayerController1 implements Initializable,Observer {
    
    @FXML
    private TableView<MatchDTO> matchesTable;
    
    @FXML
    private TableColumn<MatchDTO, String> nameColMatchesTable;

    @FXML
    private TableColumn<MatchDTO, String> scoreColMatchesTable;

    @FXML
    private TableColumn<MatchDTO, String> statusColMatchesTable;

    @FXML
    private TableColumn<MatchDTO, String> player1ColMatchesTable;

    @FXML
    private TableColumn<MatchDTO, String> player2ColMatchesTable;

    @FXML
    private TableColumn<MatchDTO, String> winnerColMatchesTable;


    @FXML
    private Label currentMatchesLabel;
    
    @FXML
    private Label cantEnrollLabel;
    
    @FXML
    private Label accountLabel;
    
    @FXML
    private Label nameLabel;

    @FXML
    private Label userNameLabel;
    
	static MatchDTO selectedMatch;
    
    //Business b;
    
    ObservableList<MatchDTO> matchList=null;
    
    public PlayerController1() {
    	//PlayerController.b=new Business();
    	MainController.b.addObserver(this);
    }

    @FXML
    void enrollAction(ActionEvent event) {
    	MatchDTO match=matchesTable.getSelectionModel().getSelectedItem();
    	TournamentDTO tour=MainController.b.getTournamentByMatch(match);
    	String text="Not enough money to enroll!";
    	boolean enoughMoney=false;
    	PlayerDTO player=MainController.b.getPlayerByID(MainController.type);
    	if(player.getAccount()>=tour.getPrice()) {
    		enoughMoney=true;
    	}
    	if(MainController.b.isPlaying(MainController.type)==false && enoughMoney==true) {
    		//b.playerEnroll(matchID, Integer.toString(MainController.type));
    		MainController.b.playerEnroll(match.getID(), Integer.toString(MainController.type));
    		accountLabel.setText("Account : "+MainController.b.getPlayerByID(MainController.type).getAccount());
    		MainController.updateMatch();
    		currentMatchesLabel.setText(MainController.currentMatch);
    		PlayerWindow.pane.getChildren().remove(cantEnrollLabel);
    		PlayerWindow.stage.setScene(PlayerWindow.scene);
    	}
    	else {
    		cantEnrollLabel.setText(text);
    		if(PlayerWindow.pane.getChildren().contains(cantEnrollLabel)) {
    			PlayerWindow.pane.getChildren().remove(cantEnrollLabel);
    		}
    		PlayerWindow.pane.getChildren().add(cantEnrollLabel);
    	}
    }

    @FXML
    void updateScoreMatchesAction(ActionEvent event) throws SQLException {
    	if(MainController.b.getOpponentID(MainController.type)!=0) {
    		MainController.b.updateScore(MainController.type);
        	MainController.updateMatch();
    		currentMatchesLabel.setText(MainController.currentMatch);
        	accountLabel.setText("Account : "+MainController.b.getPlayerByID(MainController.type).getAccount());
    	}
    }
    
    @FXML
    void updateOpScoreMatchesAction(ActionEvent event) throws SQLException {
    	if(MainController.b.getOpponentID(MainController.type)!=0) {
    		MainController.b.updateScore(MainController.b.getOpponentID(MainController.type));
    		MainController.updateMatch();
    		currentMatchesLabel.setText(MainController.currentMatch);
    	}
    }
    
    @FXML
    void backMatchesAction(ActionEvent event) throws IOException {
    	PlayerWindow.showTournaments();
    }
    
    @FXML
    void currentMatchesAction(ActionEvent event) {
    	MainController.updateMatch();
    	currentMatchesLabel.setText(MainController.currentMatch);
    }
    
    @FXML
    void matchesTableAction(MouseEvent event) throws IOException {
    	selectedMatch=matchesTable.getSelectionModel().getSelectedItem();
    	if(event.getClickCount()==2) {
    		PlayerWindow.showSets();
    	}
    }
    
    public ObservableList<MatchDTO> getMatches(TournamentDTO tour){
    	ObservableList<MatchDTO> matchList=FXCollections.observableArrayList();
    	for(MatchDTO m:MainController.b.map.get(tour).keySet()) {
    		MatchDTO match=new MatchDTO();
    		String player1Name=null;
    		String player2Name=null;
    		String winnerName=null;
    		//System.out.println(m.getName());
    		if(m.getPlayer1ID()!=null) {
    			player1Name=MainController.b.getPlayerByID(Integer.parseInt(m.getPlayer1ID())).getName();
    		}
    		if(m.getPlayer2ID()!=null) {
    			player2Name=MainController.b.getPlayerByID(Integer.parseInt(m.getPlayer2ID())).getName();
    		}
    		if(m.getWinnerID()!=null) {
    			winnerName=MainController.b.getPlayerByID(Integer.parseInt(m.getWinnerID())).getName();
    		}
    		Business.populateMatchDTO(match, m.getID(), m.getName(), m.getScore(), m.getStatus(), m.getTournamentID(), player1Name, player2Name, winnerName);
    		matchList.add(match);
    	}
    	return matchList;
    }

	public void initialize(URL arg0, ResourceBundle arg1) {
		currentMatchesLabel.setText(MainController.currentMatch);
		accountLabel.setText("Account : "+MainController.b.getPlayerByID(MainController.type).getAccount());
		
		nameLabel.setText(MainController.name);
    	userNameLabel.setText(MainController.userName);
		
		nameColMatchesTable.setCellValueFactory(new PropertyValueFactory<MatchDTO,String>("name"));
		scoreColMatchesTable.setCellValueFactory(new PropertyValueFactory<MatchDTO,String>("score"));
		statusColMatchesTable.setCellValueFactory(new PropertyValueFactory<MatchDTO,String>("status"));
		player1ColMatchesTable.setCellValueFactory(new PropertyValueFactory<MatchDTO,String>("player1ID"));
		player2ColMatchesTable.setCellValueFactory(new PropertyValueFactory<MatchDTO,String>("player2ID"));
		winnerColMatchesTable.setCellValueFactory(new PropertyValueFactory<MatchDTO,String>("winnerID"));
		matchesTable.setItems(getMatches(PlayerController.selectedTour));
		
	}

	public void update(Observable o, Object arg) {
		matchesTable.setItems(getMatches(PlayerController.selectedTour));
		matchesTable.refresh();
	}

}
