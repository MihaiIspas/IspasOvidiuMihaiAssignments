package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

import dto.PlayerDTO;
import dto.TournamentDTO;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Label;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.scene.input.MouseEvent;

import business.Business;
import javafx.fxml.*;
import javafx.scene.control.cell.*;
import view.AdminWindow;
import view.PlayerWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


@SuppressWarnings("restriction")
public class AdminTournamentsController implements Initializable,Observer {
	
	//Tournaments window

    @FXML
    private TableView<TournamentDTO> tournamentsTable;
    
    @FXML
    private TableColumn<TournamentDTO, String> tournamentsNameCol;

    @FXML
    private TableColumn<TournamentDTO, Float> tournamentsPriceCol;

    @FXML
    private TableColumn<TournamentDTO, String> tournamentsStatusCol;

    @FXML
    private TableColumn<TournamentDTO, LocalDate> tournamentsDateCol;
    
    @FXML
    private TableColumn<TournamentDTO, Integer> availablePlacesCol;
    
    @FXML
    private Label errorLabel;

    @FXML
    private TextField tournamentNametf;

    @FXML
    private CheckBox ifPaid;

    @FXML
    private DatePicker startDate;

    @FXML
    private TextField priceTf;
    
    @FXML
    private Label priceLabel;
    
    public static TournamentDTO selectedTour;
    
    public AdminTournamentsController() {
    	MainController.b.addObserver(this);
    }
    
    boolean validateInputs() {
    	float price=1;
    	if(!(priceTf.getText()==null || priceTf.getText().trim().isEmpty())) {
    		price=Float.parseFloat(priceTf.getText());
    	}
    	System.out.println(price);
    	if(price>=0) {
    		return true;
    	}
    	return false;
    }
    
    boolean validateDate() {
    	LocalDate date=LocalDate.now();
    	if(startDate.getValue()!=null) {
    		date=startDate.getValue();
    	}
    	if(date.getYear()==LocalDate.now().getYear()) {
    		if(date.getMonthValue()==LocalDate.now().getMonthValue()+1) {
    			if(date.getDayOfMonth()>=LocalDate.now().getDayOfMonth()) {
    				return true;
    			}
    		}
    		if(date.getMonthValue()>LocalDate.now().getMonthValue()+1) {
    			return true;
    		}
    	}
    	if(date.getYear()>LocalDate.now().getYear()) {
    		if(date.getMonthValue()!=LocalDate.now().getMonthValue()) {
    			if(date.getMonthValue()==12 && LocalDate.now().getMonthValue()==01) {
    				if(date.getDayOfMonth()>=LocalDate.now().getDayOfMonth()) {
    					return true;
    				}
    			}
    			else {
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    @FXML
    void tableAction(MouseEvent event) throws IOException {
    	selectedTour=tournamentsTable.getSelectionModel().getSelectedItem();
    	if(event.getClickCount()==2) {
    		AdminWindow.showMatches();
    	}
    }
    
    @FXML
    void ifPaidAction(ActionEvent event) {
    	if(ifPaid.isSelected()) {
    		AdminWindow.pane1.getChildren().addAll(priceLabel,priceTf);
    	}
    	else {
    		AdminWindow.pane1.getChildren().remove(priceLabel);
    		AdminWindow.pane1.getChildren().remove(priceTf);
    		AdminWindow.stage1.setScene(AdminWindow.scene1);
    	}
    }

    @FXML
    void createTournamentAction(ActionEvent event) throws NumberFormatException, SQLException {
    	LocalDate date=startDate.getValue();
    	String name=tournamentNametf.getText();
    	String text="Incorrect data!";
    	//System.out.println(validateDate());
    	if(validateInputs()==true && validateDate()==true) {
    		if(ifPaid.isSelected()==true) {
    			MainController.b.createPaidTournament(name, Float.parseFloat(priceTf.getText()), date);
    			AdminWindow.pane1.getChildren().remove(errorLabel);
    	    	AdminWindow.stage1.setScene(AdminWindow.scene1);
    	    	tournamentNametf.clear();
    	    	startDate.setValue(null);
    	    	priceTf.clear();
    		}
    		else {
    			MainController.b.createFreeTournament(name, date);
    			AdminWindow.pane1.getChildren().remove(errorLabel);
    	    	AdminWindow.stage1.setScene(AdminWindow.scene1);
    	    	tournamentNametf.clear();
    	    	startDate.setValue(null);
    		}
    	}
    	else {
    		errorLabel.setText(text);
    		if(AdminWindow.pane1.getChildren().contains(errorLabel)) {
    			AdminWindow.pane1.getChildren().remove(errorLabel);
    		}
    		AdminWindow.pane1.getChildren().add(errorLabel);
    	}
    }

    @FXML
    void deleteTournamentAction(ActionEvent event) throws SQLException {
    	TournamentDTO tour=tournamentsTable.getSelectionModel().getSelectedItem();
    	MainController.b.deleteTournament(tour.getID());
    }

    @FXML
    void updateTournamentAction(ActionEvent event) {
    	String name=tournamentNametf.getText();
    	String text="Incorrect data!";
    	TournamentDTO tour=tournamentsTable.getSelectionModel().getSelectedItem();
    	if(validateInputs()==true) {
    		if(ifPaid.isSelected()==true) {
    			//float price=Float.parseFloat(priceTf.getText());
    			float price=-1;
    			if(!(priceTf.getText()==null || priceTf.getText().trim().isEmpty())) {
    				price=Float.parseFloat(priceTf.getText());
    			}
    			MainController.b.updateTournament(tour.getID(), name, price);
    			AdminWindow.pane1.getChildren().remove(errorLabel);
    	    	AdminWindow.stage1.setScene(AdminWindow.scene1);
    	    	tournamentNametf.clear();
    	    	priceTf.clear();
    		}
    		else {
    			MainController.b.updateTournament(tour.getID(), name, -1);
    			AdminWindow.pane1.getChildren().remove(errorLabel);
    	    	AdminWindow.stage1.setScene(AdminWindow.scene1);
    	    	tournamentNametf.clear();
    		}
    	}
    	else {
    		errorLabel.setText(text);
    		if(AdminWindow.pane1.getChildren().contains(errorLabel)) {
    			AdminWindow.pane1.getChildren().remove(errorLabel);
    		}
    		AdminWindow.pane1.getChildren().add(errorLabel);
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

	public void update(Observable arg0, Object arg1) {
		tournamentsTable.setItems(getAllTournaments());
		tournamentsTable.refresh();
	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		ifPaid.setSelected(true);
		
		tournamentsNameCol.setCellValueFactory(new PropertyValueFactory<TournamentDTO,String>("name"));
		tournamentsPriceCol.setCellValueFactory(new PropertyValueFactory<TournamentDTO,Float>("price"));
		tournamentsStatusCol.setCellValueFactory(new PropertyValueFactory<TournamentDTO,String>("status"));
		tournamentsDateCol.setCellValueFactory(new PropertyValueFactory<TournamentDTO,LocalDate>("date"));
		availablePlacesCol.setCellValueFactory(new PropertyValueFactory<TournamentDTO,Integer>("numberPlayers"));
		
		tournamentsTable.setItems(getAllTournaments());
	}
    
}

