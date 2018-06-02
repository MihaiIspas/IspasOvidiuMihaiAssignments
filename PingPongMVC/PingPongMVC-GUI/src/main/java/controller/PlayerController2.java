package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import business.Business;
import dto.MatchDTO;
import dto.SetDTO;
import dto.TournamentDTO;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import view.PlayerWindow;
import javafx.fxml.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@SuppressWarnings("restriction")
public class PlayerController2 implements Initializable,Observer {
    
    @FXML
    private TableView<SetDTO> setsTable;

    @FXML
    private Label currentSetsLabel;
    
    @FXML
    private TableColumn<SetDTO, String> nameColSetsTable;

    @FXML
    private TableColumn<SetDTO, String> scoreColSetsTable;

    @FXML
    private TableColumn<SetDTO, String> statusColSetsTable;
    
    @FXML
    private Label accountLabel;
    
    @FXML
    private Label nameLabel;

    @FXML
    private Label userNameLabel;
    
    //Business b;
    
    public PlayerController2() {
    	//PlayerController.b=new Business();
    	MainController.b.addObserver(this);
    }

    @FXML
    void updateScoreSetsAction(ActionEvent event) throws SQLException {
    	if(MainController.b.getOpponentID(MainController.type)!=0) {
    		MainController.b.updateScore(MainController.type);
    		MainController.updateMatch();
    		currentSetsLabel.setText(MainController.currentMatch);
    		accountLabel.setText("Account : "+MainController.b.getPlayerByID(MainController.type).getAccount());
    	}
    }
    
    @FXML
    void backSetsAction(ActionEvent event) throws IOException {
    	PlayerWindow.showMatches();
    }
    
    @FXML
    void currentSetsAction(ActionEvent event) {
    	MainController.updateMatch();
    	currentSetsLabel.setText(MainController.currentMatch);
    }

    @FXML
    void updateOpScoreSetsAction(ActionEvent event) throws SQLException {
    	if(MainController.b.getOpponentID(MainController.type)!=0) {
    		MainController.b.updateScore(MainController.b.getOpponentID(MainController.type));
    		MainController.updateMatch();
    		currentSetsLabel.setText(MainController.currentMatch);
    	}
    }
    
    @FXML
    void setsTableAction(MouseEvent event) {

    }
    
    public ObservableList<SetDTO> getSets(TournamentDTO tour,MatchDTO match){
    	ObservableList<SetDTO> setList=FXCollections.observableArrayList();
    	for(SetDTO s:MainController.b.map.get(tour).get(match)) {
    		SetDTO set=new SetDTO();
    		Business.populateSetDTO(set, s.getID(), s.getName(), s.getScore(), s.getStatus(), s.getMatchID(), s.getScoreLimit());
    		setList.add(set);
    	}
    	return setList;
    }

	public void initialize(URL arg0, ResourceBundle arg1) {
		currentSetsLabel.setText(MainController.currentMatch);
		accountLabel.setText("Account : "+MainController.b.getPlayerByID(MainController.type).getAccount());
		
		nameLabel.setText(MainController.name);
    	userNameLabel.setText(MainController.userName);
		
		nameColSetsTable.setCellValueFactory(new PropertyValueFactory<SetDTO,String>("name"));
		scoreColSetsTable.setCellValueFactory(new PropertyValueFactory<SetDTO,String>("score"));
		statusColSetsTable.setCellValueFactory(new PropertyValueFactory<SetDTO,String>("status"));
		setsTable.setItems(getSets(PlayerController.selectedTour,PlayerController1.selectedMatch));
		
	}

	public void update(Observable o, Object arg) {
		setsTable.setItems(getSets(PlayerController.selectedTour,PlayerController1.selectedMatch));
		setsTable.refresh();
	}

}
