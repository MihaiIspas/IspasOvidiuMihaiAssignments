package view;

import java.sql.SQLException;

import business.Business;
import controller.MainController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;

@SuppressWarnings("restriction")
public class MainWindow extends Application {
	
	public static Stage stage;
	public static Pane mainPane;
	public static Scene scene;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage mainStage) throws Exception {
		stage=mainStage;
		FXMLLoader mainLoader=new FXMLLoader(MainWindow.class.getResource("/MainWindow.fxml"));
		MainController controller=new MainController();
		mainLoader.setController(controller);
		mainPane=(Pane) mainLoader.load();
		scene=new Scene(mainPane);
		stage.setScene(scene);
		stage.show();
		stage.setOnHiding(event->{
			try {
				MainController.b.updateDatabase();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

}
