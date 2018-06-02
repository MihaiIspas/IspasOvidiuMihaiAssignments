package gui;

import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

import client.Admin;
import entities.WriterObj;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.geometry.*;

@SuppressWarnings("restriction")
public class AdminWindow implements Observer {
	
	Admin admin;
	TableView<WriterObj>writerTable;
	Scene scene;
	Stage window;
	
	public AdminWindow() {
		
	}
	
	public AdminWindow(Admin admin1) throws UnknownHostException, IOException, InterruptedException {
		admin=admin1;
		//admin.writerList=admin.updateWriterList();
		admin1.addObserver(this);
	}
	
	public void showAdministratorWindow() {
		window=new Stage();
		window.setTitle("Administrator");
		
		window.setOnHiding(e->{
			try {
				admin.exit();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		HBox writersTitle=new HBox();
		writersTitle.setAlignment(Pos.CENTER);
		
		Label writersLabel=new Label("Writers");
		writersLabel.setFont(new Font("Arial",25));
		
		writersTitle.getChildren().add(writersLabel);
		
		VBox writers=new VBox();
		
		writerTable=new TableView<>();
		writerTable.setItems(getWriters());
		
		TableColumn<WriterObj,String> nameCol=new TableColumn<>("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn<WriterObj,Integer> ageCol=new TableColumn<>("Age");
		ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
		
		TableColumn<WriterObj,String> userNameCol=new TableColumn<>("User Name");
		userNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
		
		TableColumn<WriterObj,String> passwordCol=new TableColumn<>("Password");
		passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
		
		writerTable.getColumns().addAll(nameCol,ageCol,userNameCol,passwordCol);
		
		writers.getChildren().add(writerTable);
		
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
				admin.createWriter(nameField.getText(), Integer.parseInt(ageField.getText()), userNameField.getText(), passwordField.getText());
				//writerTable.setItems(getWriters());
				//writerTable.refresh();
				
			} catch (NumberFormatException | IOException | InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		update.setOnAction(e->{
			WriterObj writer=writerTable.getSelectionModel().getSelectedItem();
			String name,userName,password;
			Integer age;
			if(nameField.getText()==null || nameField.getText().trim().isEmpty()) {
				name=writer.getName();
			}
			else {
				name=nameField.getText();
			}
			if(ageField.getText()==null || ageField.getText().trim().isEmpty()) {
				age=writer.getAge();
			}
			else {
				age=Integer.parseInt(ageField.getText());
			}
			if(userNameField.getText()==null || userNameField.getText().trim().isEmpty()) {
				userName=writer.getUserName();
			}
			else {
				userName=userNameField.getText();
			}
			if(passwordField.getText()==null || passwordField.getText().trim().isEmpty()) {
				password=writer.getPassword();
			}
			else {
				password=passwordField.getText();
			}
			writer.setName(name);
			writer.setAge(age);
			writer.setUserName(userName);
			writer.setPassword(password);
			try {
				
				admin.updateWriter(writer);
				//writerTable.setItems(getWriters());
				//writerTable.refresh();
			} catch (IOException | InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		delete.setOnAction(e->{
			WriterObj writer=writerTable.getSelectionModel().getSelectedItem();
			try {
				admin.deleteWriter(writer);
				//writerTable.setItems(getWriters());
				//writerTable.refresh();
			} catch (IOException | InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		window.setOnHiding(e->{
			try {
				admin.exit();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		BorderPane border=new BorderPane();
		
		border.setTop(writersTitle);
		border.setCenter(writers);
		border.setLeft(null);
		border.setRight(functions);
		border.setBottom(null);
		
		scene=new Scene(border,600,350);
		window.setScene(scene);
		window.show();
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		writerTable.setItems(getWriters());
		writerTable.refresh();
		window.setScene(scene);
		//System.out.println("Updated");
		
	}
	
	private ObservableList<WriterObj> getWriters() {
		ObservableList<WriterObj> list=FXCollections.observableArrayList();
		for(WriterObj p:admin.writerList) {
			//System.out.println(p.getAvailability());
			list.add(p);
		}
		return list;
	}

}
