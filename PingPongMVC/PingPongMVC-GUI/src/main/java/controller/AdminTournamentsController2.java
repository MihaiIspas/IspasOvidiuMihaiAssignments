package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import business.Business;
import dto.MatchDTO;
import dto.SetDTO;
import dto.TournamentDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import view.AdminWindow;

@SuppressWarnings("restriction")
public class AdminTournamentsController2 implements Initializable,Observer {
    
    @FXML
    private TableView<SetDTO> setsTable;
    
    @FXML
    private TableColumn<SetDTO, String> nameColSetsTable;

    @FXML
    private TableColumn<SetDTO, String> scoreColSetsTable;

    @FXML
    private TableColumn<SetDTO, String> statusColSetsTable;
    
    public AdminTournamentsController2() {
    	MainController.b.addObserver(this);
    }
    
    @FXML
    void backSetsAction(ActionEvent event) throws IOException {
    	AdminWindow.showMatches();
    }
    
    @FXML
    void tableAction(MouseEvent event) {

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
		nameColSetsTable.setCellValueFactory(new PropertyValueFactory<SetDTO,String>("name"));
		scoreColSetsTable.setCellValueFactory(new PropertyValueFactory<SetDTO,String>("score"));
		statusColSetsTable.setCellValueFactory(new PropertyValueFactory<SetDTO,String>("status"));
		setsTable.setItems(getSets(AdminTournamentsController.selectedTour,AdminTournamentsController1.selectedMatch));
	}

	public void update(Observable arg0, Object arg1) {
		setsTable.setItems(getSets(AdminTournamentsController.selectedTour,AdminTournamentsController1.selectedMatch));
		setsTable.refresh();
	}

}
