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
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import view.AdminWindow;
import view.PlayerWindow;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Label;

@SuppressWarnings("restriction")
public class AdminTournamentsController1 implements Initializable,Observer {
    

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
    private Label cantEnrollLabel;
    
    public static MatchDTO selectedMatch;
    
    public AdminTournamentsController1() {
    	MainController.b.addObserver(this);
    }


    @FXML
    void enrollAction(ActionEvent event) throws SQLException {
    	ObservableList<PlayerDTO> selectedPlayers=AdminWindow.playerController.getPlayersTable().getSelectionModel().getSelectedItems();
		boolean flag=false;
		PlayerDTO player1=null,player2=null;
		for(PlayerDTO p:selectedPlayers) {
			if(flag==true) {
				player2=p;
				break;
			}
			player1=p;
			flag=true;
		}
		MatchDTO match=matchesTable.getSelectionModel().getSelectedItem();
		TournamentDTO tour=MainController.b.getTournamentByMatch(match);
		String text1="Players don't have enough money!";
		String text2=player1.getName()+" don't have enough money!";
		String text3=player2.getName()+" don't have enough money!";
		System.out.println(tour.getPrice()+" "+player1.getAccount());
		if(player1.getAccount()<tour.getPrice() && player2.getAccount()>=tour.getPrice()) {
			//AdminWindow.pane.getChildren().add(cantEnrollLabel);
			cantEnrollLabel.setText(text2);
    		if(AdminWindow.pane1.getChildren().contains(cantEnrollLabel)) {
    			AdminWindow.pane1.getChildren().remove(cantEnrollLabel);
    		}
    		AdminWindow.pane1.getChildren().add(cantEnrollLabel);
		}
		if(player2.getAccount()<tour.getPrice() && player1.getAccount()>=tour.getPrice()) {
			cantEnrollLabel.setText(text3);
    		if(AdminWindow.pane1.getChildren().contains(cantEnrollLabel)) {
    			AdminWindow.pane1.getChildren().remove(cantEnrollLabel);
    		}
    		AdminWindow.pane1.getChildren().add(cantEnrollLabel);
		}
		if(player2.getAccount()<tour.getPrice() && player1.getAccount()<tour.getPrice()) {
			cantEnrollLabel.setText(text1);
			System.out.println(text1);
    		if(AdminWindow.pane1.getChildren().contains(cantEnrollLabel)) {
    			AdminWindow.pane1.getChildren().remove(cantEnrollLabel);
    		}
    		AdminWindow.pane1.getChildren().add(cantEnrollLabel);
		}
		if(player1.getAccount()>=tour.getPrice() && player2.getAccount()>=tour.getPrice()) {
			MainController.b.enrollPlayers(match.getID(), player1.getID(), player2.getID());
			AdminWindow.pane1.getChildren().remove(cantEnrollLabel);
    		AdminWindow.stage1.setScene(AdminWindow.scene1);
    		PlayerWindow.stage.setScene(PlayerWindow.scene);
		}
    }
    
    @FXML
    void backMatchesAction(ActionEvent event) throws IOException {
    	AdminWindow.showTournaments();
    }
    
    @FXML
    void tableAction(MouseEvent event) throws IOException {
    	selectedMatch=matchesTable.getSelectionModel().getSelectedItem();
    	if(event.getClickCount()==2) {
    		AdminWindow.showSets();
    	}
    }

	public void update(Observable arg0, Object arg1) {
		matchesTable.setItems(getMatches(AdminTournamentsController.selectedTour));
		matchesTable.refresh();
	}
	
	public ObservableList<MatchDTO> getMatches(TournamentDTO tour){
    	ObservableList<MatchDTO> matchList=FXCollections.observableArrayList();
    	for(MatchDTO m:MainController.b.map.get(tour).keySet()) {
    		MatchDTO match=new MatchDTO();
    		String player1Name=null;
    		String player2Name=null;
    		String winnerName=null;
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
		nameColMatchesTable.setCellValueFactory(new PropertyValueFactory<MatchDTO,String>("name"));
		scoreColMatchesTable.setCellValueFactory(new PropertyValueFactory<MatchDTO,String>("score"));
		statusColMatchesTable.setCellValueFactory(new PropertyValueFactory<MatchDTO,String>("status"));
		player1ColMatchesTable.setCellValueFactory(new PropertyValueFactory<MatchDTO,String>("player1ID"));
		player2ColMatchesTable.setCellValueFactory(new PropertyValueFactory<MatchDTO,String>("player2ID"));
		matchesTable.setItems(getMatches(AdminTournamentsController.selectedTour));
	}

}
