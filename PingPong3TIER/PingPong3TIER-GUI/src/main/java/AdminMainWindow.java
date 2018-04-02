import javafx.stage.Stage;
import javafx.scene.Scene;

import java.awt.Color;
import java.nio.file.attribute.PosixFilePermission;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javafx.scene.control.SelectionMode;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.*;

public class AdminMainWindow {
	
	public static Stage window;
	public static TableView<PlayerObj> playerTable;
	static Scene scene;
	
	static LinkedHashMap<TournamentObj,LinkedHashMap<MatchObj,ArrayList<SetObj>>> map;
	static TableView<TournamentObj> tournamentTable;
	static TableView<MatchObj> matchTable=new TableView<>();
	static TableView<SetObj> setTable=new TableView<>();
	static Scene tournamentScene,matchScene,setScene;
	static TournamentObj selectedTournament;
	static MatchObj selectedMatch;
	
	
	public static void openAdminWindow() throws SQLException {
		window=new Stage();
		window.setTitle("Administrator Window");
		GridPane grid=new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(8);
		grid.setHgap(10);
		
		HBox adminTitle=new HBox();
		adminTitle.setAlignment(Pos.CENTER);
		
		Label adminLabel=new Label("Administrator");
		adminLabel.setFont(new Font("Arial",25));
		
		adminTitle.getChildren().add(adminLabel);
		
		Button playerButton=new Button("Players");
		GridPane.setConstraints(playerButton, 3, 2);
		
		playerButton.setOnAction(e -> {
			try {
				showPlayerWindow();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		Button tournamentButton=new Button("Tournaments");
		GridPane.setConstraints(tournamentButton, 6, 2);
		
		tournamentButton.setOnAction(e->{
			try {
				showTournamentWindow();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		grid.getChildren().addAll(playerButton,tournamentButton);
		
		BorderPane border=new BorderPane();
		
		border.setTop(adminTitle);
		border.setCenter(grid);
		
		scene=new Scene(border,250,100);
		
		window.setScene(scene);
		
		window.show();
	}
	
	public static void showPlayerWindow() throws SQLException {
		window=new Stage();
		window.setTitle("Players");
		
		HBox playersTitle=new HBox();
		playersTitle.setAlignment(Pos.CENTER);
		
		Label playersLabel=new Label("Players");
		playersLabel.setFont(new Font("Arial",25));
		
		playersTitle.getChildren().add(playersLabel);
		
		VBox players=new VBox();
		
		playerTable=new TableView<>();
		playerTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		playerTable.setItems(getPlayers());
		
		TableColumn<PlayerObj,String> nameCol=new TableColumn<>("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn<PlayerObj,String> ageCol=new TableColumn<>("Age");
		ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
		
		TableColumn<PlayerObj,String> userNameCol=new TableColumn<>("User Name");
		userNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
		
		TableColumn<PlayerObj,String> passwordCol=new TableColumn<>("Password");
		passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
		
		TableColumn<PlayerObj,String> availableCol=new TableColumn<>("Availability");
		availableCol.setCellValueFactory(new PropertyValueFactory<>("available"));
		
		playerTable.getColumns().addAll(nameCol,ageCol,userNameCol,passwordCol,availableCol);
		
		players.getChildren().add(playerTable);
		
		VBox functions=new VBox();
		
		Label nameLabel=new Label("Name :");
		nameLabel.setFont(new Font("Arial",15));
		
		TextField nameField=new TextField();
		
		Label ageLabel=new Label("Age :");
		ageLabel.setFont(new Font("Arial",15));
		
		TextField ageField=new TextField();
		
		Label userNameLabel=new Label("Username :");
		userNameLabel.setFont(new Font("Arial",15));
		
		TextField userNameField=new TextField();
		
		Label passwordLabel=new Label("Password :");
		passwordLabel.setFont(new Font("Arial",15));
		
		TextField passwordField=new TextField();
		
		VBox buttons=new VBox();
		buttons.setAlignment(Pos.CENTER);
		
		Button insert=new Button("Insert");
		Button update=new Button("Update");
		Button delete=new Button("Delete");
		
		Label emptyLabel=new Label();
		Label emptyLabel1=new Label();
		Label emptyLabel2=new Label();
		
		buttons.getChildren().addAll(emptyLabel,insert,emptyLabel1,update,emptyLabel2,delete);
		
		functions.getChildren().addAll(nameLabel,nameField,ageLabel,ageField,userNameLabel,userNameField,passwordLabel,passwordField,buttons);
		
		
		insert.setOnAction(e->{
			try {
				Business.createPlayer(nameField.getText(), Integer.parseInt(ageField.getText()), userNameField.getText(), passwordField.getText());
				playerTable.setItems(getPlayers());
			} catch (NumberFormatException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		update.setOnAction(e->{
			String id=Integer.toString(playerTable.getSelectionModel().getSelectedItem().getID());
			try {
				String name,age,userName,password;
				if(nameField.getText()==null || nameField.getText().trim().isEmpty()) {
					name="";
				}
				else {
					name=nameField.getText();
				}
				if(ageField.getText()==null || ageField.getText().trim().isEmpty()) {
					age="";
				}
				else {
					age=ageField.getText();
				}
				if(userNameField.getText()==null || userNameField.getText().trim().isEmpty()) {
					userName="";
				}
				else {
					userName=userNameField.getText();
				}
				if(passwordField.getText()==null || passwordField.getText().trim().isEmpty()) {
					password="";
				}
				else {
					password=passwordField.getText();
				}
				Business.updatePlayer(id, name, age, userName, password);
				playerTable.setItems(getPlayers());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		delete.setOnAction(e->{
			String id=Integer.toString(playerTable.getSelectionModel().getSelectedItem().getID());
			try {
				Business.deletePlayer(id);
				playerTable.setItems(getPlayers());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		BorderPane border=new BorderPane();
		
		border.setTop(playersTitle);
		border.setCenter(players);
		border.setLeft(null);
		border.setRight(functions);
		border.setBottom(null);
		
		scene=new Scene(border,600,350);
		window.setScene(scene);
		window.show();
	}
	
	public static void showTournamentWindow() throws SQLException {
		window=new Stage();
		window.setTitle("Tournaments");
		
		VBox titleTournament=new VBox();
		titleTournament.setAlignment(Pos.CENTER);
		
		Label tournamentLabel=new Label("Tournamets");
		tournamentLabel.setFont(new Font("Arial",25));
		
		titleTournament.getChildren().add(tournamentLabel);
		
		VBox tournaments=new VBox();
		
		TableColumn<TournamentObj,String> nameCol=new TableColumn<>("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn<TournamentObj,String> statusCol=new TableColumn<>("Status");
		statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
		
		tournamentTable=new TableView<>();
		tournamentTable.setItems(getTournaments());
		tournamentTable.getColumns().addAll(nameCol,statusCol);
		
		//TableRow<TournamentObj> selectedRow;
		tournamentTable.setRowFactory(selectedRow ->{
			TableRow<TournamentObj> row=new TableRow<>();
			row.setOnMouseClicked(e ->{
				if(e.getClickCount()==2 && (! row.isEmpty())) {
					selectedTournament=new TournamentObj(row.getItem().getID(),row.getItem().getName(),row.getItem().getStatus());
					try {
						matchTable.setItems(getMatches(selectedTournament));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					window.setScene(matchScene);
				}
			});
			return row;
		});
		//System.out.println(selectedTournament.getID());
		tournaments.getChildren().add(tournamentTable);
		
		VBox functions=new VBox();
		
		Label name=new Label("Name :");
		name.setFont(new Font("Arial",15));
		
		TextField nameField=new TextField();
		
		VBox buttons=new VBox();
		buttons.setAlignment(Pos.CENTER);
		
		Button insert=new Button("Insert");
		Button update=new Button("Update");
		Button delete=new Button("Delete");
		Button enroll=new Button("Enroll Players");
		
		Label emptyLabel=new Label();
		Label emptyLabel1=new Label();
		Label emptyLabel2=new Label();
		Label emptyLabel3=new Label();
		
		buttons.getChildren().addAll(emptyLabel3,insert,emptyLabel,update,emptyLabel1,delete,emptyLabel2,enroll);
		
		functions.getChildren().addAll(name,nameField,buttons);
		
		insert.setOnAction(e->{
			try {
				Business.createTournament(nameField.getText());
				tournamentTable.setItems(getTournaments());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		delete.setOnAction(e->{
			String id=Integer.toString(tournamentTable.getSelectionModel().getSelectedItem().getID());
			try {
				Business.deleteTournament(id);
				tournamentTable.setItems(getTournaments());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		update.setOnAction(e->{
			String id=Integer.toString(tournamentTable.getSelectionModel().getSelectedItem().getID());
			String tournamentName;
			//System.out.println(id);
			//String newName;
			if(nameField.getText()==null || nameField.getText().trim().isEmpty()) {
				tournamentName="";
			}
			else {
				tournamentName=nameField.getText();
				System.out.println(tournamentName);
			}
			try {
				Business.updateTournament(id, tournamentName);
				tournamentTable.setItems(getTournaments());
				tournamentTable.refresh();
				//tournamentTable.getColumns().addAll(nameCol,statusCol);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		enroll.setOnAction(e->{
			ObservableList<PlayerObj> selectedPlayers=playerTable.getSelectionModel().getSelectedItems();
			boolean flag=false;
			PlayerObj player1=null,player2=null;
			for(PlayerObj p:selectedPlayers) {
				if(flag==true) {
					player2=p;
					break;
				}
				player1=p;
				flag=true;
			}
			System.out.println(player1.getName()+" "+player2.getName());
			String tournamentID=Integer.toString(tournamentTable.getSelectionModel().getSelectedItem().getID());
			System.out.println(tournamentID);
			try {
				Business.enrollPlayers(tournamentID, Integer.toString(player1.getID()), Integer.toString(player2.getID()));
				tournamentTable.setItems(getTournaments());
				playerTable.setItems(getPlayers());
				tournamentTable.refresh();
				playerTable.refresh();
			} catch (NumberFormatException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		BorderPane borderPane=new BorderPane();
		borderPane.setTop(titleTournament);
		borderPane.setCenter(tournaments);
		borderPane.setRight(functions);
		borderPane.setLeft(null);
		borderPane.setBottom(null);
		
		tournamentScene=new Scene(borderPane,600,250);
		
		window.setScene(tournamentScene);
		
		VBox titleMatch=new VBox();
		titleMatch.setAlignment(Pos.CENTER);
		
		
		Label matchLabel=new Label("Matches");
		matchLabel.setFont(new Font("Arial",25));
		
		titleMatch.getChildren().add(matchLabel);
		
		VBox matches=new VBox();
		
		TableColumn<MatchObj,String> nameCol1=new TableColumn<>("Name");
		nameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn<MatchObj,String> scoreCol=new TableColumn<>("Score");
		scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
		
		TableColumn<MatchObj,String> statusCol1=new TableColumn<>("Status");
		statusCol1.setCellValueFactory(new PropertyValueFactory<>("status"));
		
		TableColumn<MatchObj,String> player1Col=new TableColumn<>("Player 1");
		player1Col.setCellValueFactory(new PropertyValueFactory<>("player1Name"));
		
		TableColumn<MatchObj,String> player2Col=new TableColumn<>("Player 2");
		player2Col.setCellValueFactory(new PropertyValueFactory<>("player2Name"));
		
		matchTable.getColumns().addAll(nameCol1,scoreCol,statusCol1,player1Col,player2Col);
		
		matchTable.setRowFactory(t ->{
			TableRow<MatchObj> row=new TableRow<>();
			row.setOnMouseClicked(e ->{
				if(e.getClickCount()==2 && (! row.isEmpty())) {
					selectedMatch=row.getItem();
					//System.out.println(selectedMatch.getName());
					for(TournamentObj p:map.keySet()) {
						if(selectedMatch.getName().contains(p.getName())) {
							selectedTournament=p;
							//System.out.println(selectedTournament.getName());
						}
					}
					try {
						setTable.setItems(getSets(selectedTournament,selectedMatch));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					window.setScene(setScene);
				}
			});
			return row;
		});
		
		matches.getChildren().add(matchTable);
		
		Button cancelButton=new Button("Cancel Enrollment");
		
		HBox backToTournaments=new HBox();
		backToTournaments.setAlignment(Pos.CENTER);
		
		Button backTournamnets=new Button("Back");
		
		backToTournaments.getChildren().addAll(backTournamnets,cancelButton);
		
		backTournamnets.setOnAction(e -> window.setScene(tournamentScene));
		
		cancelButton.setOnAction(e->{
			try {
				Business.cancelEnrollment(Integer.toString(matchTable.getSelectionModel().getSelectedItem().getID()));
				tournamentTable.setItems(getTournaments());
				playerTable.setItems(getPlayers());
				TournamentObj tour=Business.selectTournamentByMatchID(Integer.toString(matchTable.getSelectionModel().getSelectedItem().getID()));
				matchTable.setItems(getMatches(tour));
				tournamentTable.refresh();
				matchTable.refresh();
				playerTable.refresh();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		BorderPane borderPane1=new BorderPane();
		borderPane1.setTop(titleMatch);
		borderPane1.setCenter(matches);
		borderPane1.setRight(null);
		borderPane1.setLeft(null);
		borderPane1.setBottom(backToTournaments);
		
		matchScene=new Scene(borderPane1,600,250);
		
		VBox titleSet=new VBox();
		titleSet.setAlignment(Pos.CENTER);
		
		
		Label setLabel=new Label("Sets");
		setLabel.setFont(new Font("Arial",25));
		
		titleSet.getChildren().add(setLabel);
		
		VBox sets=new VBox();
		
		TableColumn<SetObj,String> nameCol2=new TableColumn<>("Name");
		nameCol2.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn<SetObj,String> scoreCol1=new TableColumn<>("Score");
		scoreCol1.setCellValueFactory(new PropertyValueFactory<>("score"));
		
		TableColumn<SetObj,String> statusCol2=new TableColumn<>("Status");
		statusCol2.setCellValueFactory(new PropertyValueFactory<>("status"));
		
		setTable.getColumns().addAll(nameCol2,scoreCol1,statusCol2);
		
		sets.getChildren().add(setTable);
		
		VBox backToMatches=new VBox();
		backToMatches.setAlignment(Pos.CENTER);
		
		Button backMatches=new Button("Back");
		
		backToMatches.getChildren().add(backMatches);
		
		backMatches.setOnAction(e -> window.setScene(matchScene));
		
		BorderPane borderPane2=new BorderPane();
		borderPane2.setTop(titleSet);
		borderPane2.setCenter(sets);
		borderPane2.setRight(null);
		borderPane2.setLeft(null);
		borderPane2.setBottom(backToMatches);
		
		setScene=new Scene(borderPane2,600,250);
		window.show();
	}
	
	private static ObservableList<PlayerObj> getPlayers() throws SQLException {
		ObservableList<PlayerObj> list=FXCollections.observableArrayList();
		for(PlayerObj p:Business.selectPlayers()) {
			//System.out.println(p.getAvailability());
			if(!(p.getName().contains("Test") || p.getName().equals("Administrator"))) {
				list.add(p);
			}
		}
		return list;
	}
	
	public static ObservableList<TournamentObj> getTournaments() throws SQLException{
		ObservableList<TournamentObj> tournaments=FXCollections.observableArrayList();
		map=Business.selectTournaments();
		for(TournamentObj t:map.keySet()) {
			tournaments.add(t);
		}
		return tournaments;
	}
	
	public static ObservableList<MatchObj> getMatches(TournamentObj t) throws SQLException{
		ObservableList<MatchObj> matches=FXCollections.observableArrayList();
		map=Business.selectTournaments();
		for(MatchObj m:map.get(t).keySet()) {
			matches.add(m);
			//System.out.println(m.getName());
		}
		return matches;
	}
	
	public static ObservableList<SetObj> getSets(TournamentObj t,MatchObj m) throws SQLException{
		ObservableList<SetObj> sets=FXCollections.observableArrayList();
		//map=Business.selectTournaments();
		//System.out.println(map.get(t).containsKey(m));
		for(SetObj s:map.get(t).get(m)) {
			sets.add(s);
		}
		return sets;
	}


}
