package gui;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.attribute.PosixFilePermission;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import client.Admin;
import client.Client;
import entities.ArticleObj;
import entities.WriterObj;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.*;
import javafx.application.Platform;

@SuppressWarnings({ "unused", "restriction" })
public class LogInWindow extends Application implements Observer {

	Stage window;
	Scene scene;
	Stage window1;
	Scene scene1;
	Client client;
	public ListView<String> articleList=new ListView<>();
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window=primaryStage;
		window.setTitle("News Agency Application");
		
		HBox title=new HBox();
		
		Label titleLabel=new Label("News Agency Application");
		titleLabel.setFont(new Font("Arial",35));
		title.getChildren().setAll(titleLabel);
		title.setAlignment(Pos.CENTER);
		
		GridPane grid=new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(8);
		grid.setHgap(10);
		
		Label clientLabel=new Label("Log In");
		clientLabel.setFont(new Font("Arial",20));
		GridPane.setConstraints(clientLabel, 5, 1);
		
		Label userNameLabelClient=new Label("Username: ");
		GridPane.setConstraints(userNameLabelClient, 4, 3);
		
		TextField userNameFieldClient=new TextField();
		GridPane.setConstraints(userNameFieldClient, 5, 3);
		
		Label passwordLabelClient=new Label("Password: ");
		GridPane.setConstraints(passwordLabelClient, 4, 4);
		
		TextField passwordFieldClient=new TextField();
		GridPane.setConstraints(passwordFieldClient, 5, 4);
		
		Button logInButton=new Button("Log In");
		GridPane.setConstraints(logInButton, 5, 5);
		
		Button readerButton=new Button("Go to articles");
		GridPane.setConstraints(readerButton, 7, 6);
		
		Label playerErrorLabel=new Label("Username or password incorrect!");
		GridPane.setConstraints(playerErrorLabel, 5, 5);
		
		grid.getChildren().addAll(clientLabel,userNameLabelClient,userNameFieldClient,passwordLabelClient,passwordFieldClient,logInButton,readerButton);
		
		BorderPane borderPane=new BorderPane();
		borderPane.setTop(title);
		borderPane.setCenter(grid);
		
		scene=new Scene(borderPane,400,230);
		
		//ArrayList<WriterObj> writers=Admin.writerList;
		Admin admin=new Admin();
		admin.writerList=admin.updateWriterList();
		//System.out.println("here");
		//System.out.println(client.articleList.toString());
		
		
		logInButton.setOnAction(e->{
			if(userNameFieldClient.getText().equals("admin") && passwordFieldClient.getText().equals("admin")) {
				try {
					new AdminWindow(admin).showAdministratorWindow();
				} catch (IOException | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else {
				System.out.println(admin.writerList.toString());
				for(WriterObj w:admin.writerList) {
					//System.out.println(w.toString());
					if(userNameFieldClient.getText().equals(w.getUserName()) && passwordFieldClient.getText().equals(w.getPassword())) {
						try {
							new ClientWindow().showClientWindow();
						} catch (IOException | InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
		
		readerButton.setOnAction(e->{
			showClientWindow();
		});
		
		window.setScene(scene);
		
		window.show();
	}
	
	
	public synchronized void showClientWindow() {
		window1=new Stage();
		window1.setTitle("Client");
		
		client=new Client();
		//client.updateArticleList();
		client.addObserver(this);
		
		new Thread(new Runnable() {
			public void run() {
				try {
					while(true) {
						if(ClientWindow.lock==false) {
							client.updateArticleList();
						}
						else {
							Thread.sleep(1000);
						}
					}
				} catch (IOException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		
		HBox writersTitle=new HBox();
		writersTitle.setAlignment(Pos.CENTER);
		
		Label articlesLabel=new Label("Articles");
		articlesLabel.setFont(new Font("Arial",25));
		
		writersTitle.getChildren().add(articlesLabel);
		
		VBox writers=new VBox();
		
		articleList=new ListView<>();
		articleList.setItems(getWriters());
		
		writers.getChildren().add(articleList);
		
		articleList.setOnMouseClicked(e-> {
			//public void handle(MouseEvent arg0) {
			String selected=articleList.getSelectionModel().getSelectedItem();
			ArticleObj art=new ArticleObj();
			if(e.getClickCount()==2) {
				for(ArticleObj a:client.articleList) {
					if(selected.contains(a.getTitle()) && selected.contains(a.getAbstr())) {
						art=a;
					}
				}
				showArticle(art);
			}
		});
		
		BorderPane border=new BorderPane();
		
		border.setTop(writersTitle);
		border.setCenter(writers);
		border.setLeft(null);
		//border.setRight(functions);
		border.setBottom(null);
		
		scene1=new Scene(border,600,350);
		window1.setScene(scene1);
		window1.show();
	}
	
	public void showArticle(ArticleObj article) {
		//Stage articleWindow=new Stage();
		VBox articles=new VBox();
		HBox title=new HBox();
		title.setAlignment(Pos.CENTER);
		Label titleLabel=new Label("Title : "+article.getTitle());
		titleLabel.setFont(new Font("Arial",18));
		Label empty=new Label("             ");
		Label authorLabel=new Label("Author : "+article.getAuthor());
		authorLabel.setFont(new Font("Arial",18));
		title.getChildren().addAll(titleLabel,empty,authorLabel);
		TextArea body=new TextArea();
		String text="Abstract : "+article.getAbstr()+"\n"+"		"+article.getBody();
		body.setText(text);
		VBox relatedBox=new VBox();
		//relatedBox.setAlignment(Pos.CENTER);
		Label relatedLabel=new Label("Related articles : ");
		relatedLabel.setFont(new Font("Arial",15));
		ListView<String> related=new ListView<>();
		related.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		related.setItems(getArticles(article));
		relatedBox.getChildren().addAll(relatedLabel,related);
		relatedBox.setMaxHeight(150.0);
		HBox bottom=new HBox();
		bottom.setAlignment(Pos.CENTER);
		Button back=new Button("Back");
		bottom.getChildren().add(back);
		articles.getChildren().addAll(title,body,relatedBox,bottom);
		Scene articleScene=new Scene(articles,600,350);
		window1.setScene(articleScene);
		related.setOnMouseClicked(e-> {
			//public void handle(MouseEvent arg0) {
			String selected=related.getSelectionModel().getSelectedItem();
			ArticleObj art=new ArticleObj();
			if(e.getClickCount()==2) {
				for(ArticleObj a:client.articleList) {
					if(selected.contains(a.getTitle()) && selected.contains(a.getAbstr())) {
						art=a;
					}
				}
				showArticle(art);
			}
		});
		back.setOnAction(e->{
			window1.setScene(scene1);
		});
	}
	
	private ObservableList<String> getWriters() {
		ObservableList<String> list=FXCollections.observableArrayList();
		//System.out.println(client.articleList);
		for(ArticleObj p:client.articleList) {
			String s=p.getTitle()+"\n"+"Abstract : "+p.getAbstr();
			//Platform.runLater(()-> list.add(s));
			list.add(s);
		}
		return list;
	}
	
	public ObservableList<String> getArticles(ArticleObj article){
		ObservableList<String> articles=FXCollections.observableArrayList();
		if(article.getRelated()!=null) {
			for(ArticleObj a:article.getRelated()) {
				String art=a.getTitle()+"\n"+"Abstract : "+a.getAbstr();
				articles.add(art);
			}
		}
		return articles;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		//if(Client.uiflag==false) {
			articleList.setItems(getWriters());
			articleList.refresh();
		//}
		//window1.setScene(scene1);
	}
}
