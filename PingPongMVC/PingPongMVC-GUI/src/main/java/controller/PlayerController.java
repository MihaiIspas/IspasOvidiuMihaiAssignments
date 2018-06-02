package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import business.Business;
import dto.TournamentDTO;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import view.AdminWindow;
import view.PlayerWindow;
import javafx.fxml.*;
import javafx.scene.control.cell.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@SuppressWarnings("restriction")
public class PlayerController implements Initializable,Observer {
	
	static TournamentDTO selectedTour;
	
	//Tournaments window

    @FXML
    private TableView<TournamentDTO> tournamentsTable;

    @FXML
    private Label currentTournamentsLabel;

    @FXML
    private TextField searchTf;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private Label noTournamentsLabel;
    
    @FXML
    private TableColumn<TournamentDTO, String> nameColTournamentsTable;

    @FXML
    private TableColumn<TournamentDTO, String> statusColTournamentsTable;

    @FXML
    private TableColumn<TournamentDTO, Float> priceColTournamentsTable;
    
    @FXML
    private TableColumn<TournamentDTO, LocalDate> dateColTournaments;
    
    @FXML
    private TableColumn<TournamentDTO, Integer> availablePlacesCol;
    
    @FXML
    private Label accountLabel;
    
    @FXML
    private Label nameLabel;

    @FXML
    private Label userNameLabel;
    
    //public static Business b;
    
    public PlayerController() {
    	//b=new Business();
    	MainController.b.addObserver(this);
    }
    
    @FXML
    void searchAction(ActionEvent event) {
    	String name=searchTf.getText();
    	String text="No tournaments with this name!";
    	boolean found=false;
    	for(TournamentDTO t:MainController.b.map.keySet()) {
    		if(t.getName().contains(name)) {
    			found=true;
    			tournamentsTable.setItems(getSearchedTournaments(name));
    			tournamentsTable.refresh();
    			PlayerWindow.pane.getChildren().remove(noTournamentsLabel);
    	    	PlayerWindow.stage.setScene(PlayerWindow.scene);
    		}
    	}
    	if(found==false) {
	    	noTournamentsLabel.setText(text);
			if(PlayerWindow.pane.getChildren().contains(noTournamentsLabel)) {
				PlayerWindow.pane.getChildren().remove(noTournamentsLabel);
			}
			PlayerWindow.pane.getChildren().add(noTournamentsLabel);
    	}
    	if(name==null) {
    		tournamentsTable.setItems(tournamentsTable.getItems());
			tournamentsTable.refresh();
    	}
    }

    @FXML
    void updateScoreTournamentsAction(ActionEvent event) throws SQLException {
    	if(MainController.b.getOpponentID(MainController.type)!=0) {
    		MainController.b.updateScore(MainController.type);
        	MainController.updateMatch();
    		currentTournamentsLabel.setText(MainController.currentMatch);
        	accountLabel.setText("Account : "+MainController.b.getPlayerByID(MainController.type).getAccount());
    	}
    }
    
    @FXML
    void updateOpScoreTournamentsAction(ActionEvent event) throws SQLException {
    	if(MainController.b.getOpponentID(MainController.type)!=0) {
    		MainController.b.updateScore(MainController.b.getOpponentID(MainController.type));
    		MainController.updateMatch();
    		currentTournamentsLabel.setText(MainController.currentMatch);
    	}
    }
    
    @FXML
    void currentTournamentsAction(ActionEvent event) {
    	MainController.updateMatch();
    	currentTournamentsLabel.setText(MainController.currentMatch);
    }
    
    @FXML
    void comboBoxAction(ActionEvent event) {
    	if(comboBox.getValue().equals("Free")) {
    		tournamentsTable.setItems(getFreeTournaments());
    		tournamentsTable.refresh();
    	}
    	if(comboBox.getValue().equals("Paid")) {
    		tournamentsTable.setItems(getPaidTournaments());
    		tournamentsTable.refresh();
    	}
    	if(comboBox.getValue().equals("Upcoming")) {
    		tournamentsTable.setItems(getUpcomingTournaments());
    		tournamentsTable.refresh();
    	}
    	if(comboBox.getValue().equals("Enrolled")) {
    		tournamentsTable.setItems(getEnrolledTournaments());
    		tournamentsTable.refresh();
    	}
    	if(comboBox.getValue().equals("Ongoing")) {
    		tournamentsTable.setItems(getOngoingTournaments());
    		tournamentsTable.refresh();
    	}
    	if(comboBox.getValue().equals("Finished")) {
    		tournamentsTable.setItems(getFinishedTournaments());
    		tournamentsTable.refresh();
    	}
    	if(comboBox.getValue().equals("All")) {
    		tournamentsTable.setItems(getAllTournaments());
    		tournamentsTable.refresh();
    	}
    }
    
    public ObservableList<TournamentDTO> getAllTournaments(){
    	ObservableList<TournamentDTO> tourList=FXCollections.observableArrayList();
    	for(TournamentDTO t:MainController.b.map.keySet()) {
    		TournamentDTO tour=new TournamentDTO();
    		Business.populateTournamentDTO(tour, t.getID(), t.getName(), t.getStatus(), t.getPrice(), 8-t.getNumberPlayers(),t.getDate());
    		tourList.add(tour);
    	}
    	return tourList;
    }
    
    public ObservableList<TournamentDTO> getFreeTournaments(){
    	ObservableList<TournamentDTO> tourList=FXCollections.observableArrayList();
    	for(TournamentDTO t:MainController.b.map.keySet()) {
    		if(t.getPrice()==0) {
    			TournamentDTO tour=new TournamentDTO();
        		Business.populateTournamentDTO(tour, t.getID(), t.getName(), t.getStatus(), t.getPrice(), 8-t.getNumberPlayers(),t.getDate());
        		tourList.add(tour);
    		}
    	}
    	return tourList;
    }
    
    public ObservableList<TournamentDTO> getPaidTournaments(){
    	ObservableList<TournamentDTO> tourList=FXCollections.observableArrayList();
    	for(TournamentDTO t:MainController.b.map.keySet()) {
    		if(t.getPrice()>0) {
    			TournamentDTO tour=new TournamentDTO();
        		Business.populateTournamentDTO(tour, t.getID(), t.getName(), t.getStatus(), t.getPrice(), 8-t.getNumberPlayers(),t.getDate());
        		tourList.add(tour);
    		}
    	}
    	return tourList;
    }
    
    public ObservableList<TournamentDTO> getUpcomingTournaments(){
    	ObservableList<TournamentDTO> tourList=FXCollections.observableArrayList();
    	for(TournamentDTO t:MainController.b.map.keySet()) {
    		if(t.getStatus().equals("Upcoming")) {
    			TournamentDTO tour=new TournamentDTO();
        		Business.populateTournamentDTO(tour, t.getID(), t.getName(), t.getStatus(), t.getPrice(), 8-t.getNumberPlayers(),t.getDate());
        		tourList.add(tour);
    		}
    	}
    	return tourList;
    }
    
    public ObservableList<TournamentDTO> getEnrolledTournaments(){
    	ObservableList<TournamentDTO> tourList=FXCollections.observableArrayList();
    	for(TournamentDTO t:MainController.b.map.keySet()) {
    		if(t.getStatus().equals("Enrolled")) {
    			TournamentDTO tour=new TournamentDTO();
        		Business.populateTournamentDTO(tour, t.getID(), t.getName(), t.getStatus(), t.getPrice(), 8-t.getNumberPlayers(),t.getDate());
        		tourList.add(tour);
    		}
    	}
    	return tourList;
    }
    
    public ObservableList<TournamentDTO> getOngoingTournaments(){
    	ObservableList<TournamentDTO> tourList=FXCollections.observableArrayList();
    	for(TournamentDTO t:MainController.b.map.keySet()) {
    		if(t.getStatus().equals("Ongoing")) {
    			TournamentDTO tour=new TournamentDTO();
        		Business.populateTournamentDTO(tour, t.getID(), t.getName(), t.getStatus(), t.getPrice(), 8-t.getNumberPlayers(),t.getDate());
        		tourList.add(tour);
    		}
    	}
    	return tourList;
    }
    
    public ObservableList<TournamentDTO> getFinishedTournaments(){
    	ObservableList<TournamentDTO> tourList=FXCollections.observableArrayList();
    	for(TournamentDTO t:MainController.b.map.keySet()) {
    		if(t.getStatus().equals("Finished")) {
    			TournamentDTO tour=new TournamentDTO();
        		Business.populateTournamentDTO(tour, t.getID(), t.getName(), t.getStatus(), t.getPrice(), 8-t.getNumberPlayers(),t.getDate());
        		tourList.add(tour);
    		}
    	}
    	return tourList;
    }
    
    public ObservableList<TournamentDTO> getSearchedTournaments(String name){
    	ObservableList<TournamentDTO> tourList=FXCollections.observableArrayList();
    	for(TournamentDTO t:tournamentsTable.getItems()) {
    		if(t.getName().contains(name)) {
    			TournamentDTO tour=new TournamentDTO();
        		Business.populateTournamentDTO(tour, t.getID(), t.getName(), t.getStatus(), t.getPrice(), 8-t.getNumberPlayers(),t.getDate());
        		tourList.add(tour);
    		}
    	}
    	return tourList;
    }
    
    @FXML
    void tournamentsTableAction(MouseEvent event) throws IOException {
    	selectedTour=tournamentsTable.getSelectionModel().getSelectedItem();
    	if(event.getClickCount()==2) {
    		PlayerWindow.showMatches();
    	}
    	
    }

    public void initialize(URL arg0, ResourceBundle arg1) {
    	currentTournamentsLabel.setText(MainController.currentMatch);
    	accountLabel.setText("Account : "+MainController.b.getPlayerByID(MainController.type).getAccount());
    	
    	nameLabel.setText(MainController.name);
    	userNameLabel.setText(MainController.userName);
    	
		nameColTournamentsTable.setCellValueFactory(new PropertyValueFactory<TournamentDTO,String>("name"));
		statusColTournamentsTable.setCellValueFactory(new PropertyValueFactory<TournamentDTO,String>("status"));
		priceColTournamentsTable.setCellValueFactory(new PropertyValueFactory<TournamentDTO,Float>("price"));
		availablePlacesCol.setCellValueFactory(new PropertyValueFactory<TournamentDTO,Integer>("numberPlayers"));
		dateColTournaments.setCellValueFactory(new PropertyValueFactory<TournamentDTO,LocalDate>("date"));
		
		tournamentsTable.setItems(getAllTournaments());
		
		comboBox.getItems().addAll("All","Free","Paid","Upcoming","Enrolled","Ongoing","Finished");
		comboBox.getSelectionModel().select("All");
		
	}

	public void update(Observable o, Object arg) {
		tournamentsTable.setItems(getAllTournaments());
		tournamentsTable.refresh();
	}

}
