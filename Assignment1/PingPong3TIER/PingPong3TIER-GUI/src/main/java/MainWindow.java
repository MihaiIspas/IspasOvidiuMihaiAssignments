import javafx.geometry.Insets;

import java.awt.Color;
import java.nio.file.attribute.PosixFilePermission;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.*;

public class MainWindow extends Application {
	Stage window;
	Scene scene;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window=primaryStage;
		window.setTitle("Ping Pong Application");
		
		HBox title=new HBox();
		
		Label titleLabel=new Label("Ping Pong Application");
		titleLabel.setFont(new Font("Arial",35));
		//titleLabel.setTextFill(Paint);
		title.getChildren().setAll(titleLabel);
		title.setAlignment(Pos.CENTER);
		
		GridPane grid=new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(8);
		grid.setHgap(10);
		
		/*Label adminLabel=new Label("Administrator Log In");
		adminLabel.setFont(new Font("Arial",20));
		GridPane.setConstraints(adminLabel, 2, 1);
		
		Label userNameLabelAdmin=new Label("Username: ");
		GridPane.setConstraints(userNameLabelAdmin, 1, 3);
		
		TextField userNameFieldAdmin=new TextField();
		GridPane.setConstraints(userNameFieldAdmin, 2, 3);
		
		Label passwordLabelAdmin=new Label("Password: ");
		GridPane.setConstraints(passwordLabelAdmin, 1, 4);
		
		TextField passwordFieldAdmin=new TextField();
		GridPane.setConstraints(passwordFieldAdmin, 2, 4);
		
		Button adminButton=new Button("Log In");
		GridPane.setConstraints(adminButton, 2, 5);
		
		Label adminErrorLabel=new Label("Username or password incorrect!");
		GridPane.setConstraints(adminErrorLabel, 2, 7);*/
		
		Label playerLabel=new Label("Log In");
		playerLabel.setFont(new Font("Arial",20));
		GridPane.setConstraints(playerLabel, 7, 1);
		
		Label userNameLabelPlayer=new Label("Username: ");
		GridPane.setConstraints(userNameLabelPlayer, 6, 3);
		
		TextField userNameFieldPlayer=new TextField();
		GridPane.setConstraints(userNameFieldPlayer, 7, 3);
		
		Label passwordLabelPlayer=new Label("Password: ");
		GridPane.setConstraints(passwordLabelPlayer, 6, 4);
		
		TextField passwordFieldPlayer=new TextField();
		GridPane.setConstraints(passwordFieldPlayer, 7, 4);
		
		Button playerButton=new Button("Log In");
		GridPane.setConstraints(playerButton, 7, 5);
		
		Label playerErrorLabel=new Label("Username or password incorrect!");
		GridPane.setConstraints(playerErrorLabel, 7, 7);
		
		grid.getChildren().addAll(playerLabel,userNameLabelPlayer,userNameFieldPlayer,passwordLabelPlayer,passwordFieldPlayer,playerButton);
		
		BorderPane borderPane=new BorderPane();
		borderPane.setTop(title);
		borderPane.setCenter(grid);
		
		scene=new Scene(borderPane,400,230);
		
		/*adminButton.setOnAction(e ->{
			if(userNameFieldAdmin.getText().equals("admin") && passwordFieldAdmin.getText().equals("admin")) {
				try {
					AdminMainWindow.openAdminWindow();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				userNameFieldAdmin.clear();
				passwordFieldAdmin.clear();
				grid.getChildren().remove(adminErrorLabel);
				window.setScene(scene);
				window.show();
			}
			else {
				grid.getChildren().add(adminErrorLabel);
				userNameFieldAdmin.clear();
				passwordFieldAdmin.clear();
			}
		});*/
		
		playerButton.setOnAction(e->{
			try {
				boolean inList=false;
				for(String[] s:Business.selectAllPrerequisites()) {
					if(userNameFieldPlayer.getText().equals(s[0]) && passwordFieldPlayer.getText().equals(s[1])) {
						if(s[3].equals("P")) {
							PlayerWindow.playerID=s[2];
							//System.out.println(s[2]);
							PlayerWindow win=new PlayerWindow();
							win.showWindow();
							inList=true;
							userNameFieldPlayer.clear();
							passwordFieldPlayer.clear();
							grid.getChildren().remove(playerErrorLabel);
							window.setScene(scene);
							window.show();
						}
						if(s[3].equals("A")) {
							try {
								AdminMainWindow.openAdminWindow();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							inList=true;
							userNameFieldPlayer.clear();
							passwordFieldPlayer.clear();
							grid.getChildren().remove(playerErrorLabel);
							window.setScene(scene);
							window.show();
						}
					}
				}
				if(inList==false) {
					grid.getChildren().add(playerErrorLabel);
					userNameFieldPlayer.clear();
					passwordFieldPlayer.clear();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		window.setScene(scene);
		
		window.show();
	}

}
