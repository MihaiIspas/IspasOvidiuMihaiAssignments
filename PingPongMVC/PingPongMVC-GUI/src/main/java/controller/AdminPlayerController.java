package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;

import java.net.URL;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.scene.control.cell.PropertyValueFactory;
import view.AdminWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import business.Business;
import dto.PlayerDTO;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;

@SuppressWarnings("restriction")
public class AdminPlayerController implements Initializable,Observer {

    @FXML
    private TableView<PlayerDTO> playersTable;
    
    @FXML
    private TableColumn<PlayerDTO, String> playerNameCol;

    @FXML
    private TableColumn<PlayerDTO, Integer> playerAgeCol;

    @FXML
    private TableColumn<PlayerDTO, String> playerAvailableCol;

    @FXML
    private TableColumn<PlayerDTO, String> playerUserNameCol;

    @FXML
    private TableColumn<PlayerDTO, String> playerPasswordCol;

    @FXML
    private TableColumn<PlayerDTO, Float> playerAccountCol;


    @FXML
    private TextField playerNameTf;

    @FXML
    private TextField amountTf;

    @FXML
    private TextField playerAgeTf;

    @FXML
    private TextField playerUserNameTf;

    @FXML
    private TextField playerPasswordTf;
    
    @FXML
    private Label errorLabel;
    
    public TableView<PlayerDTO> getPlayersTable(){
    	return playersTable;
    }
    
    public AdminPlayerController(){
    	MainController.b.addObserver(this);
    }
    
    public boolean validateInputs() {
    	int age=1;
    	float amount=1;
    	if(!(playerAgeTf.getText()==null || playerAgeTf.getText().trim().isEmpty())) {
    		age=Integer.parseInt(playerAgeTf.getText());
    	}
    	if(!(amountTf.getText()==null || amountTf.getText().trim().isEmpty())) {
    		amount=Float.parseFloat(amountTf.getText());
    	}
    	if(age<=0 || amount<0) {
    		return false;
    	}
    	return true;
    }

    @FXML
    void createPlayerAction(ActionEvent event) throws NumberFormatException, SQLException {
    	String text="Incorrect data!";
    	if(validateInputs()==true) {
    		MainController.b.createPlayer(playerNameTf.getText(), Integer.parseInt(playerAgeTf.getText()), playerUserNameTf.getText(), playerPasswordTf.getText(), 0);
	    	playerNameTf.clear();
	    	playerAgeTf.clear();
	    	playerUserNameTf.clear();
	    	playerPasswordTf.clear();
	    	AdminWindow.pane.getChildren().remove(errorLabel);
	    	AdminWindow.stage.setScene(AdminWindow.scene);
    	}
    	else {
    		errorLabel.setText(text);
    		if(AdminWindow.pane.getChildren().contains(errorLabel)) {
    			AdminWindow.pane.getChildren().remove(errorLabel);
    		}
    		AdminWindow.pane.getChildren().add(errorLabel);
    	}
    }

    @FXML
    void deletePlayerAction(ActionEvent event) throws SQLException {
    	PlayerDTO p=playersTable.getSelectionModel().getSelectedItem();
    	MainController.b.deletePlayer(p.getID());
    }

    @FXML
    void depositAction(ActionEvent event) throws SQLException {
    	String text="Incorrect data!";
    	if(validateInputs()==true) {
    		PlayerDTO player=playersTable.getSelectionModel().getSelectedItem();
    		MainController.b.depositMoney(player, Float.parseFloat(amountTf.getText()));
    		for(PlayerDTO p:MainController.b.playerList) {
    			if(p.getID()==player.getID()) {
    				System.out.println(p.getAccount());
    			}
    		}
    		amountTf.clear();
    		AdminWindow.pane.getChildren().remove(errorLabel);
	    	AdminWindow.stage.setScene(AdminWindow.scene);
    	}
    	else {
    		errorLabel.setText(text);
    		if(AdminWindow.pane.getChildren().contains(errorLabel)) {
    			AdminWindow.pane.getChildren().remove(errorLabel);
    		}
    		AdminWindow.pane.getChildren().add(errorLabel);
    	}
    }

    @FXML
    void updatePlayerAction(ActionEvent event) {
    	String text="Incorrect data!";
    	if(validateInputs()==true) {
	    	String name,userName,password;
	    	int age;
	    	PlayerDTO player=playersTable.getSelectionModel().getSelectedItem();
	    	if(playerNameTf.getText()==null || playerNameTf.getText().trim().isEmpty()) {
	    		name="";
	    	}
	    	else {
	    		name=playerNameTf.getText();
	    	}
	    	if(playerUserNameTf.getText()==null || playerUserNameTf.getText().trim().isEmpty()) {
	    		userName="";
	    	}
	    	else {
	    		userName=playerUserNameTf.getText();
	    	}
	    	if(playerPasswordTf.getText()==null || playerPasswordTf.getText().trim().isEmpty()) {
	    		password="";
	    	}
	    	else {
	    		password=playerPasswordTf.getText();
	    	}
	    	if(playerAgeTf.getText()==null || playerAgeTf.getText().trim().isEmpty()) {
	    		age=-1;
	    	}
	    	else {
	    		age=Integer.parseInt(playerAgeTf.getText());
	    	}
	    	MainController.b.updatePlayer(player.getID(), name, age, userName, password);
	    	playerNameTf.clear();
	    	playerAgeTf.clear();
	    	playerUserNameTf.clear();
	    	playerPasswordTf.clear();
	    	AdminWindow.pane.getChildren().remove(errorLabel);
	    	AdminWindow.stage.setScene(AdminWindow.scene);
    	}
    	else {
    		errorLabel.setText(text);
    		if(AdminWindow.pane.getChildren().contains(errorLabel)) {
    			AdminWindow.pane.getChildren().remove(errorLabel);
    		}
    		AdminWindow.pane.getChildren().add(errorLabel);
    	}
    }

    @FXML
    void withdrawAction(ActionEvent event) {
    	String text="Incorrect data!";
    	PlayerDTO player=playersTable.getSelectionModel().getSelectedItem();
    	if(validateInputs()==true && Float.parseFloat(amountTf.getText())<=player.getAccount()) {
    		MainController.b.withdrawMoney(player, Float.parseFloat(amountTf.getText()));
    		amountTf.clear();
    		AdminWindow.pane.getChildren().remove(errorLabel);
	    	AdminWindow.stage.setScene(AdminWindow.scene);
    	}
    	else {
    		errorLabel.setText(text);
    		if(AdminWindow.pane.getChildren().contains(errorLabel)) {
    			AdminWindow.pane.getChildren().remove(errorLabel);
    		}
    		AdminWindow.pane.getChildren().add(errorLabel);
    	}
    }
    
    public ObservableList<PlayerDTO> getPlayers() {
    	ObservableList<PlayerDTO> playerList=FXCollections.observableArrayList();
    	for(PlayerDTO p:MainController.b.playerList) {
    		if(!(p.getName().equals("Administrator"))) {
	    		PlayerDTO player=new PlayerDTO();
	    		int available;
	    		if(p.getAvailable().equals("Available")) {
	    			available=0;
	    		}
	    		else {
	    			available=1;
	    		}
	    		Business.populatePlayerDTO(player, p.getID(), p.getName(), p.getAge(), available, p.getUserName(), p.getPassword(), p.getAccount());
	    		playerList.add(player);
    		}
    	}
    	return playerList;
    }

	public void initialize(URL arg0, ResourceBundle arg1) {
		playerNameCol.setCellValueFactory(new PropertyValueFactory<PlayerDTO,String>("name"));
		playerAgeCol.setCellValueFactory(new PropertyValueFactory<PlayerDTO,Integer>("age"));
		playerAvailableCol.setCellValueFactory(new PropertyValueFactory<PlayerDTO,String>("available"));
		playerUserNameCol.setCellValueFactory(new PropertyValueFactory<PlayerDTO,String>("userName"));
		playerPasswordCol.setCellValueFactory(new PropertyValueFactory<PlayerDTO,String>("password"));
		playerAccountCol.setCellValueFactory(new PropertyValueFactory<PlayerDTO,Float>("account"));
		playersTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		playersTable.setItems(getPlayers());
	}

	public void update(Observable arg0, Object arg1) {
		playersTable.setItems(getPlayers());
		playersTable.refresh();
	}

}

