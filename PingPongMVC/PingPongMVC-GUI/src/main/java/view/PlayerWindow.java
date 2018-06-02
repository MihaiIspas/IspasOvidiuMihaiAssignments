package view;

import java.awt.Label;
import java.io.IOException;

import controller.MainController;
import controller.PlayerController;
import controller.PlayerController1;
import controller.PlayerController2;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class PlayerWindow {
	
	public static Pane pane;
	public static Stage stage=new Stage();
	public static Scene scene;
	
	static FXMLLoader matchesLoader;
	static FXMLLoader setsLoader;
	static FXMLLoader tournamentsLoader;
	
	public static void showTournaments() throws IOException {
		//pane=new Pane();
		//stage=new Stage();
		tournamentsLoader=new FXMLLoader(PlayerWindow.class.getResource("/TournamentsPlayer.fxml"));
		//pane=(Pane) FXMLLoader.load(MainWindow.class.getResource("/TournamentsPlayer.fxml"));
		PlayerController controller=new PlayerController();
		tournamentsLoader.setController(controller);
		pane=(Pane) tournamentsLoader.load();
		scene=new Scene(pane);
		stage.setScene(scene);
	}
	
	public static void showMatches() throws IOException {
		matchesLoader=new FXMLLoader(PlayerWindow.class.getResource("/MatchesPlayer.fxml"));
		PlayerController1 controller=new PlayerController1();
		matchesLoader.setController(controller);
		pane=(Pane) matchesLoader.load();
		scene=new Scene(pane);
		stage.setScene(scene);
	}
	
	public static void showSets() throws IOException {
		setsLoader=new FXMLLoader(PlayerWindow.class.getResource("/SetsPlayer.fxml"));
		PlayerController2 controller=new PlayerController2();
		setsLoader.setController(controller);
		pane=(Pane) setsLoader.load();
		scene=new Scene(pane);
		stage.setScene(scene);
	}

}
