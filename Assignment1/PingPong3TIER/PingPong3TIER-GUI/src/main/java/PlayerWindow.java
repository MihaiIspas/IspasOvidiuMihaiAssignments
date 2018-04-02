

import java.awt.Color;
import java.nio.file.attribute.PosixFilePermission;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javafx.scene.control.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.*;
import javafx.collections.*;

public class PlayerWindow {
	public static String playerID;
	static LinkedHashMap<TournamentObj,LinkedHashMap<MatchObj,ArrayList<SetObj>>> map;
	static TableView<TournamentObj> tournamentTable;
	static TableView<MatchObj> matchTable;
	static TableView<SetObj> setTable;
	private static Scene tournamentScene,matchScene,setScene;
	static Stage window;
	static BorderPane borderPane;
	static BorderPane borderPane1;
	static BorderPane borderPane2;
	
	public PlayerWindow() throws SQLException {
		window=new Stage();
		borderPane=new BorderPane();
		borderPane1=new BorderPane();
		borderPane2=new BorderPane();
		tournamentScene=new Scene(borderPane,600,250);
		matchScene=new Scene(borderPane1,600,250);
		setScene=new Scene(borderPane2,600,250);
		//tournamentScene.
	}
	
	public static void showWindow() throws SQLException {
		//map=new LinkedHashMap<TournamentObj,LinkedHashMap<MatchObj,ArrayList<SetObj>>>();
		//map=Business.selectTournaments();
		//Scene tournamentScene=null,matchScene=null,setScene=null;
		//tournamentScene=new Scene();
		TournamentObj selectedTournament=null;
		MatchObj selectedMatch=null;
		window.setTitle("Player Window");
		
		VBox titleTournament=new VBox();
		titleTournament.setAlignment(Pos.CENTER);
		
		Label currentMatch=new Label();
		currentMatch.setFont(new Font("Arial",17));
		
		Label tournamentLabel=new Label("Tournaments");
		tournamentLabel.setFont(new Font("Arial",20));
		
		//System.out.println(playerID);
		
		HBox score=new HBox();
		score.setAlignment(Pos.CENTER);
		
		Button updateScore=new Button("Increase Score");
		updateScore.setAlignment(Pos.CENTER_RIGHT);
		Button updateOpScore=new Button("Increase Opponent Score");
		updateOpScore.setAlignment(Pos.CENTER_LEFT);
		
		score.getChildren().addAll(updateScore,updateOpScore);
		
		if(Business.findByPlayerID(playerID)!=null) {
			
			currentMatch.setText("Current match: "+Business.selectMatchNameByID(Business.findByPlayerID(playerID)));
		}
		else {
			currentMatch.setText("No match enrolled in");
		}
		
		titleTournament.getChildren().addAll(tournamentLabel,currentMatch,score);
		
		VBox tournaments=new VBox();
		
		TableColumn<TournamentObj,String> nameCol=new TableColumn<>("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn<TournamentObj,String> statusCol=new TableColumn<>("Status");
		statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
		
		tournamentTable=new TableView<>();
		tournamentTable.setItems(getTournaments());
		tournamentTable.getColumns().addAll(nameCol,statusCol);
		
		//TableRow<TournamentObj> selectedRow;
		//System.out.println(selectedTournament.getID());
		tournaments.getChildren().add(tournamentTable);
		
		//borderPane=new BorderPane();
		borderPane.setTop(titleTournament);
		borderPane.setCenter(tournaments);
		borderPane.setRight(null);
		borderPane.setLeft(null);
		borderPane.setBottom(null);
		
		//mtournamentScene=new Scene(borderPane,600,250);
		
		window.setScene(tournamentScene);
		
		VBox titleMatch=new VBox();
		titleMatch.setAlignment(Pos.CENTER);
		
		
		Label matchLabel=new Label("Matches");
		matchLabel.setFont(new Font("Arial",20));
		
		Label currentMatch1=new Label();
		currentMatch1.setFont(new Font("Arial",17));
		
		HBox score1=new HBox();
		score1.setAlignment(Pos.CENTER);
		
		Button updateScore1=new Button("Increase Score");
		updateScore.setAlignment(Pos.CENTER_RIGHT);
		Button updateOpScore1=new Button("Increase Opponent Score");
		updateOpScore.setAlignment(Pos.CENTER_LEFT);
		
		score1.getChildren().addAll(updateScore1,updateOpScore1);
		
		if(Business.findByPlayerID(playerID)!=null) {
			//System.out.println(playerID);
			currentMatch1.setText("Current match: "+Business.selectMatchNameByID(Business.findByPlayerID(playerID)));
		}
		else {
			currentMatch1.setText("No match enrolled in");
		}

		
		titleMatch.getChildren().addAll(matchLabel,currentMatch1,score1);
		
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
		
		matchTable=new TableView<>();
		matchTable.getColumns().addAll(nameCol1,scoreCol,statusCol1,player1Col,player2Col);
		
		matches.getChildren().add(matchTable);
		
		VBox backToTournaments=new VBox();
		backToTournaments.setAlignment(Pos.CENTER);
		
		Button backTournamnets=new Button("Back");
		
		backToTournaments.getChildren().add(backTournamnets);
		
		backTournamnets.setOnAction(e -> window.setScene(tournamentScene));
		
		//borderPane1=new BorderPane();
		borderPane1.setTop(titleMatch);
		borderPane1.setCenter(matches);
		borderPane1.setRight(null);
		borderPane1.setLeft(null);
		borderPane1.setBottom(backToTournaments);
		
		//matchScene=new Scene(borderPane1,600,250);
		
		VBox titleSet=new VBox();
		titleSet.setAlignment(Pos.CENTER);
		
		
		Label setLabel=new Label("Sets");
		setLabel.setFont(new Font("Arial",20));
		
		Label currentMatch2=new Label();
		currentMatch2.setFont(new Font("Arial",17));

		HBox score2=new HBox();
		score2.setAlignment(Pos.CENTER);
		
		Button updateScore2=new Button("Increase Score");
		updateScore.setAlignment(Pos.CENTER_RIGHT);
		Button updateOpScore2=new Button("Increase Opponent Score");
		updateOpScore2.setAlignment(Pos.CENTER_LEFT);
		
		score2.getChildren().addAll(updateScore2,updateOpScore2);
		
		if(Business.findByPlayerID(playerID)!=null) {
			//System.out.println(playerID);
			currentMatch2.setText("Current match: "+Business.selectMatchNameByID(Business.findByPlayerID(playerID)));
		}
		else {
			currentMatch2.setText("No match enrolled in");
		}
		
		titleSet.getChildren().addAll(setLabel,currentMatch2,score2);
		
		VBox sets=new VBox();
		
		TableColumn<SetObj,String> nameCol2=new TableColumn<>("Name");
		nameCol2.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn<SetObj,String> scoreCol1=new TableColumn<>("Score");
		scoreCol1.setCellValueFactory(new PropertyValueFactory<>("score"));
		
		TableColumn<SetObj,String> statusCol2=new TableColumn<>("Status");
		statusCol2.setCellValueFactory(new PropertyValueFactory<>("status"));
		
		setTable=new TableView<>();
		setTable.getColumns().addAll(nameCol2,scoreCol1,statusCol2);
		
		sets.getChildren().add(setTable);
		
		VBox backToMatches=new VBox();
		backToMatches.setAlignment(Pos.CENTER);
		
		Button backMatches=new Button("Back");
		
		backToMatches.getChildren().add(backMatches);
		
		backMatches.setOnAction(e -> window.setScene(matchScene));
		
		//borderPane2=new BorderPane();
		borderPane2.setTop(titleSet);
		borderPane2.setCenter(sets);
		borderPane2.setRight(null);
		borderPane2.setLeft(null);
		borderPane2.setBottom(backToMatches);
		
		//setScene=new Scene(borderPane2,600,250);
		
		tournamentTable.setRowFactory(selectedRow ->{
			TableRow<TournamentObj> row=new TableRow<>();
			row.setOnMouseClicked(e ->{
				if(e.getClickCount()==2 && (! row.isEmpty())) {
					//selectedTournament=new TournamentObj(row.getItem().getID(),row.getItem().getName(),row.getItem().getStatus());
					try {
						matchTable.setItems(getMatches(new TournamentObj(row.getItem().getID(),row.getItem().getName(),row.getItem().getStatus())));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					window.setScene(matchScene);
				}
			});
			return row;
		});
		
		matchTable.setRowFactory(t ->{
			TableRow<MatchObj> row=new TableRow<>();
			row.setOnMouseClicked(e ->{
				if(e.getClickCount()==2 && (! row.isEmpty())) {
					//selectedMatch=row.getItem();
					//System.out.println(selectedMatch.getName());
					/*for(TournamentObj p:map.keySet()) {
						if(selectedMatch.getName().contains(p.getName())) {
							selectedTournament=p;
							//System.out.println(selectedTournament.getName());
						}
					}*/
					try {
						//System.out.println(selectedMatch.getName());
						setTable.setItems(getSets2(Integer.toString(row.getItem().getID())));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					window.setScene(setScene);
				}
			});
			return row;
		});
		
		updateScore.setOnAction(e->{
			try {
				map=new LinkedHashMap<TournamentObj,LinkedHashMap<MatchObj,ArrayList<SetObj>>>();
				if(Business.notPlayersAvailable==false) {
					Business.updateScore(playerID);
					map=Business.selectTournaments();
					tournamentTable.setItems(getTournaments());
					//AdminMainWindow.tournamentTable.setItems(getTournaments());
					for(TournamentObj t:Business.selectTournaments().keySet()) {
						if(Business.selectMatchNameByID(Business.findByPlayerID(playerID)).contains(t.getName())) {
							matchTable.setItems(getMatches(t));
							//AdminMainWindow.matchTable.setItems(getMatches(t));
							//System.out.println(Business.selectMatchNameByID(Business.findByPlayerID(playerID)));
							for(MatchObj m:Business.selectTournaments().get(t).keySet()) {
								//System.out.println(m.getName());
								if(m.getID()==Integer.parseInt(Business.findByPlayerID(playerID))) {
									//System.out.println(m.getID()+" - "+Integer.parseInt(Business.findByPlayerID(playerID)));
									setTable.setItems(getSets2(Integer.toString(m.getID())));
									//AdminMainWindow.setTable.setItems(getSets2(Integer.toString(m.getID())));
								}
							}
						}
					}
					tournamentTable.refresh();
					matchTable.refresh();
					setTable.refresh();
					if(Business.findByPlayerID(playerID)!=null) {
						currentMatch.setText("Current match: "+Business.selectMatchNameByID(Business.findByPlayerID(playerID)));
					}
					else {
						currentMatch.setText("No match enrolled in");
					}
					//AdminMainWindow.tournamentTable.refresh();
					//AdminMainWindow.matchTable.refresh();
					//AdminMainWindow.setTable.refresh();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		updateOpScore.setOnAction(e->{
			try {
				if(Business.getOpponentID(playerID)!=null) {
					map=new LinkedHashMap<TournamentObj,LinkedHashMap<MatchObj,ArrayList<SetObj>>>();
					Business.updateScore(Business.getOpponentID(playerID));
					if(Business.notPlayersAvailable==false) {
						map=Business.selectTournaments();
						tournamentTable.setItems(getTournaments());
						//AdminMainWindow.tournamentTable.setItems(getTournaments());
						for(TournamentObj t:Business.selectTournaments().keySet()) {
							if(Business.selectMatchNameByID(Business.findByPlayerID(playerID)).contains(t.getName())) {
								matchTable.setItems(getMatches(t));
								//AdminMainWindow.matchTable.setItems(getMatches(t));
								//System.out.println(Business.selectMatchNameByID(Business.findByPlayerID(playerID)));
								for(MatchObj m:Business.selectTournaments().get(t).keySet()) {
									//System.out.println(m.getName());
									if(m.getID()==Integer.parseInt(Business.findByPlayerID(playerID))) {
										//System.out.println(m.getID()+" - "+Integer.parseInt(Business.findByPlayerID(playerID)));
										setTable.setItems(getSets2(Integer.toString(m.getID())));
										//AdminMainWindow.setTable.setItems(getSets2(Integer.toString(m.getID())));
									}
								}
							}
						}
						tournamentTable.refresh();
						matchTable.refresh();
						setTable.refresh();
						//AdminMainWindow.tournamentTable.refresh();
						//AdminMainWindow.matchTable.refresh();
						//AdminMainWindow.setTable.refresh();
					}
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		updateScore1.setOnAction(e->{
			try {
				map=new LinkedHashMap<TournamentObj,LinkedHashMap<MatchObj,ArrayList<SetObj>>>();
				Business.updateScore(playerID);
				map=Business.selectTournaments();
				if(Business.notPlayersAvailable==false) {
					tournamentTable.setItems(getTournaments());
					//AdminMainWindow.tournamentTable.setItems(getTournaments());
					for(TournamentObj t:Business.selectTournaments().keySet()) {
						//Business.findByPlayerID(playerID);
						if(Business.selectMatchNameByID(Business.findByPlayerID(playerID)).contains(t.getName())) {
							matchTable.setItems(getMatches(t));
							//AdminMainWindow.matchTable.setItems(getMatches(t));
							//System.out.println(Business.selectMatchNameByID(Business.findByPlayerID(playerID)));
							for(MatchObj m:Business.selectTournaments().get(t).keySet()) {
								//System.out.println(m.getName());
								if(m.getID()==Integer.parseInt(Business.findByPlayerID(playerID))) {
									//System.out.println(m.getID()+" - "+Integer.parseInt(Business.findByPlayerID(playerID)));
									setTable.setItems(getSets2(Integer.toString(m.getID())));
									//AdminMainWindow.setTable.setItems(getSets2(Integer.toString(m.getID())));
								}
							}
						}
					}
					tournamentTable.refresh();
					matchTable.refresh();
					setTable.refresh();
					if(Business.findByPlayerID(playerID)!=null) {
						currentMatch1.setText("Current match: "+Business.selectMatchNameByID(Business.findByPlayerID(playerID)));
					}
					else {
						currentMatch1.setText("No match enrolled in");
					}
					//AdminMainWindow.tournamentTable.refresh();
					//AdminMainWindow.matchTable.refresh();
					//AdminMainWindow.setTable.refresh();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		updateOpScore1.setOnAction(e->{
			try {
				if(Business.getOpponentID(playerID)!=null) {
					map=new LinkedHashMap<TournamentObj,LinkedHashMap<MatchObj,ArrayList<SetObj>>>();
					Business.updateScore(Business.getOpponentID(playerID));
					map=Business.selectTournaments();
					if(Business.notPlayersAvailable==false) {
						tournamentTable.setItems(getTournaments());
						//AdminMainWindow.tournamentTable.setItems(getTournaments());
						for(TournamentObj t:Business.selectTournaments().keySet()) {
							//Business.findByPlayerID(playerID);
							if(Business.selectMatchNameByID(Business.findByPlayerID(playerID)).contains(t.getName())) {
								matchTable.setItems(getMatches(t));
								//AdminMainWindow.matchTable.setItems(getMatches(t));
								//System.out.println(Business.selectMatchNameByID(Business.findByPlayerID(playerID)));
								for(MatchObj m:Business.selectTournaments().get(t).keySet()) {
									//System.out.println(m.getName());
									if(m.getID()==Integer.parseInt(Business.findByPlayerID(playerID))) {
										//System.out.println(m.getID()+" - "+Integer.parseInt(Business.findByPlayerID(playerID)));
										setTable.setItems(getSets2(Integer.toString(m.getID())));
										//AdminMainWindow.setTable.setItems(getSets2(Integer.toString(m.getID())));
									}
								}
							}
						}
						tournamentTable.refresh();
						matchTable.refresh();
						setTable.refresh();
						if(Business.findByPlayerID(playerID)!=null) {
							currentMatch1.setText("Current match: "+Business.selectMatchNameByID(Business.findByPlayerID(playerID)));
						}
						else {
							currentMatch1.setText("No match enrolled in");
						}
						//AdminMainWindow.tournamentTable.refresh();
						//AdminMainWindow.matchTable.refresh();
						//AdminMainWindow.setTable.refresh();
					}
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		updateScore2.setOnAction(e->{
			try {
				//map=new LinkedHashMap<TournamentObj,LinkedHashMap<MatchObj,ArrayList<SetObj>>>();
				map=Business.selectTournaments();
				Business.updateScore(playerID);
				if(Business.notPlayersAvailable==false) {
					tournamentTable.setItems(getTournaments());
					//AdminMainWindow.tournamentTable.setItems(getTournaments());
					for(TournamentObj t:Business.selectTournaments().keySet()) {
						//System.out.println(Business.findByPlayerID(playerID));
						if(Business.selectMatchNameByID(Business.findByPlayerID(playerID)).contains(t.getName())) {
							matchTable.setItems(getMatches(t));
							//AdminMainWindow.matchTable.setItems(getMatches(t));
							//System.out.println(Business.selectMatchNameByID(Business.findByPlayerID(playerID)));
							for(MatchObj m:Business.selectTournaments().get(t).keySet()) {
								//System.out.println(m.getName());
								if(m.getID()==Integer.parseInt(Business.findByPlayerID(playerID))) {
									//System.out.println(m.getID()+" - "+Integer.parseInt(Business.findByPlayerID(playerID)));
									setTable.setItems(getSets2(Integer.toString(m.getID())));
									//AdminMainWindow.setTable.setItems(getSets2(Integer.toString(m.getID())));
								}
							}
						}
					}
					tournamentTable.refresh();
					matchTable.refresh();
					setTable.refresh();
					if(Business.findByPlayerID(playerID)!=null) {
						currentMatch2.setText("Current match: "+Business.selectMatchNameByID(Business.findByPlayerID(playerID)));
					}
					else {
						currentMatch2.setText("No match enrolled in");
					}
					//AdminMainWindow.tournamentTable.refresh();
					//AdminMainWindow.matchTable.refresh();
					//AdminMainWindow.setTable.refresh();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		updateOpScore2.setOnAction(e->{
			try {
				if(Business.getOpponentID(playerID)!=null) {
					//System.out.println(".....................");
					map=new LinkedHashMap<TournamentObj,LinkedHashMap<MatchObj,ArrayList<SetObj>>>();
					Business.updateScore(Business.getOpponentID(playerID));
					map=Business.selectTournaments();
					if(Business.notPlayersAvailable==false) {
						tournamentTable.setItems(getTournaments());
						//AdminMainWindow.tournamentTable.setItems(getTournaments());
						for(TournamentObj t:Business.selectTournaments().keySet()) {
							//Business.findByPlayerID(playerID);
							if(Business.selectMatchNameByID(Business.findByPlayerID(playerID)).contains(t.getName())) {
								matchTable.setItems(getMatches(t));
								//AdminMainWindow.matchTable.setItems(getMatches(t));
								//System.out.println(Business.selectMatchNameByID(Business.findByPlayerID(playerID)));
								for(MatchObj m:Business.selectTournaments().get(t).keySet()) {
									//System.out.println(m.getName());
									if(m.getID()==Integer.parseInt(Business.findByPlayerID(playerID))) {
										//System.out.println(m.getID()+" - "+Integer.parseInt(Business.findByPlayerID(playerID)));
										setTable.setItems(getSets2(Integer.toString(m.getID())));
										//AdminMainWindow.setTable.setItems(getSets2(Integer.toString(m.getID())));
									}
								}
							}
						}
						tournamentTable.refresh();
						matchTable.refresh();
						setTable.refresh();
						if(Business.findByPlayerID(playerID)!=null) {
							currentMatch1.setText("Current match: "+Business.selectMatchNameByID(Business.findByPlayerID(playerID)));
						}
						else {
							currentMatch1.setText("No match enrolled in");
						}
						//AdminMainWindow.tournamentTable.refresh();
						//AdminMainWindow.matchTable.refresh();
						//AdminMainWindow.setTable.refresh();
					}
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		window.show();
	}
	
	public static ObservableList<TournamentObj> getTournaments() throws SQLException{
		ObservableList<TournamentObj> tournaments=FXCollections.observableArrayList();
		map=new LinkedHashMap<TournamentObj,LinkedHashMap<MatchObj,ArrayList<SetObj>>>();
		map=Business.selectTournaments();
		for(TournamentObj t:map.keySet()) {
			tournaments.add(t);
		}
		return tournaments;
	}
	
	public static ObservableList<MatchObj> getMatches(TournamentObj t) throws SQLException{
		ObservableList<MatchObj> matches=FXCollections.observableArrayList();
		map=new LinkedHashMap<TournamentObj,LinkedHashMap<MatchObj,ArrayList<SetObj>>>();
		map=Business.selectTournaments();
		System.out.println(map.containsKey(t));
		for(MatchObj m:map.get(t).keySet()) {
			matches.add(m);
			//System.out.println(m.getName());
		}
		return matches;
	}
	
	public static ObservableList<SetObj> getSets(TournamentObj t,MatchObj m) throws SQLException{
		ObservableList<SetObj> sets=FXCollections.observableArrayList();
		//map=Business.selectTournaments();
		System.out.println(t.getName());
		//System.out.println(map.get(t).containsKey(m)+"()()()()");
		for(SetObj s:map.get(t).get(m)) {
			sets.add(s);
		}
		return sets;
	}
	
	public static ObservableList<SetObj> getSets2(String matchID) throws SQLException{
		ObservableList<SetObj> sets=FXCollections.observableArrayList();
		for(String m:Business.selectByMatchID(matchID).keySet()) {
			String[] fields=Business.selectByMatchID(matchID).get(m);
			SetObj set=new SetObj(Integer.parseInt(m),fields[3],fields[0],fields[1],Integer.parseInt(fields[2]));
			sets.add(set);
		}
		return sets;
	}

}
