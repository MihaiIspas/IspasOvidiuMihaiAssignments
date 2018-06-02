package view;

import java.io.IOException;

import controller.AdminPlayerController;
import controller.AdminTournamentsController;
import controller.AdminTournamentsController1;
import controller.AdminTournamentsController2;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class AdminWindow {
	
	public static Pane pane;
	public static Pane pane1;
	public static Stage stage;
	public static Stage stage1=new Stage();
	public static Scene scene;
	public static Scene scene1;
	
	static FXMLLoader playersLoader;
	static FXMLLoader tournamentsLoader;
	static FXMLLoader matchesLoader;
	static FXMLLoader setsLoader;
	
	public static AdminPlayerController playerController;
	
	public static void showAdminWindow() throws IOException {
		pane=new Pane();
		stage=new Stage();
		pane=(Pane) FXMLLoader.load(MainWindow.class.getResource("/AdminMainWindow.fxml"));
		stage.setScene(new Scene(pane));
		stage.show();
	}
	
	public static void showPlayersWindow() throws IOException {
		stage=new Stage();
		playersLoader=new FXMLLoader(AdminWindow.class.getResource("/PlayersAdmin.fxml"));
		playerController=new AdminPlayerController();
		playersLoader.setController(playerController);
		pane=(Pane) playersLoader.load();
		scene=new Scene(pane);
		stage.setScene(scene);
	}
	
	public static void showTournaments() throws IOException {
		tournamentsLoader=new FXMLLoader(AdminWindow.class.getResource("/TournamentsAdmin.fxml"));
		AdminTournamentsController controller=new AdminTournamentsController();
		tournamentsLoader.setController(controller);
		pane1=(Pane) tournamentsLoader.load();
		scene1=new Scene(pane1);
		stage1.setScene(scene1);
	}

	public static void showMatches() throws IOException {
		matchesLoader=new FXMLLoader(AdminWindow.class.getResource("/MatchesAdmin.fxml"));
		AdminTournamentsController1 controller=new AdminTournamentsController1();
		matchesLoader.setController(controller);
		pane1=(Pane) matchesLoader.load();
		scene1=new Scene(pane1);
		stage1.setScene(scene1);
	}

	public static void showSets() throws IOException {
		setsLoader=new FXMLLoader(AdminWindow.class.getResource("/SetsAdmin.fxml"));
		AdminTournamentsController2 controller=new AdminTournamentsController2();
		setsLoader.setController(controller);
		pane1=(Pane) setsLoader.load();
		scene1=new Scene(pane1);
		stage1.setScene(scene1);
	}

}
