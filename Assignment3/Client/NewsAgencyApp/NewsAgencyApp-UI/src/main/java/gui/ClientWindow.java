package gui;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.control.ListView;

import client.Admin;
import client.Client;
import client.ThreadUpdate;
import entities.ArticleObj;
import entities.WriterObj;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.geometry.*;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.SelectionMode;
import javafx.application.Platform;

@SuppressWarnings({ "restriction", "unused" })
public class ClientWindow implements Observer {

	Client client;
	ListView<String>articleList;
	Scene scene;
	Stage window;
	//public static ArrayList<Client> clientList=new ArrayList<Client>();
	public ThreadUpdate thread;
	
	public static boolean lock;
	
	public ClientWindow() throws UnknownHostException, IOException, InterruptedException {
		client=new Client();
		lock=false;
		//this.client=client1;
		//client.articleList=client.updateArticleList();
		//System.out.println(client.articleList.toString());
		client.addObserver(this);
		new Thread(new Runnable() {
			public void run() {
				try {
					while(true) {
						if(lock==false) {
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
		//Platform.runLater();
		//clientList.add(this.client);
		
	}
	
	public synchronized void showClientWindow() {
		window=new Stage();
		window.setTitle("Client");
		
		window.setOnHiding(e->{
			try {
				client.exit();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		HBox writersTitle=new HBox();
		writersTitle.setAlignment(Pos.CENTER);
		
		Label articlesLabel=new Label("Articles");
		articlesLabel.setFont(new Font("Arial",25));
		
		writersTitle.getChildren().add(articlesLabel);
		
		VBox writers=new VBox();
		
		articleList=new ListView<>();
		articleList.setItems(getWriters());
		
		writers.getChildren().add(articleList);
		
		HBox bottom=new HBox();
		bottom.setAlignment(Pos.CENTER);
		
		Button insert=new Button("Insert");
		Button update=new Button("Update");
		Button delete=new Button("Delete");
		
		bottom.getChildren().addAll(insert,update,delete);
		
		insert.setOnAction(e->{
			showInsertWindow();
		});
		
		update.setOnAction(e->{
			ArticleObj art=new ArticleObj();
			for(ArticleObj a:client.articleList) {
				if(articleList.getSelectionModel().getSelectedItem().contains(a.getTitle()) && articleList.getSelectionModel().getSelectedItem().contains(a.getAbstr())) {
					art=a;
				}
			}
			showUpdateWindow(art);
		});
		
		delete.setOnAction(e->{
			lock=true;
			ArticleObj art=new ArticleObj();
			for(ArticleObj a:client.articleList) {
				if(articleList.getSelectionModel().getSelectedItem().contains(a.getTitle()) && articleList.getSelectionModel().getSelectedItem().contains(a.getAbstr())) {
					art=a;
				}
			}
			try {
				client.deleteArticle(art);
				/*for(Client c:clientList) {
					c.updateArticleList();
				}*/
				//articleList.setItems(getWriters());
				//articleList.refresh();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			lock=false;
		});
		
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
		
		window.setOnHiding(e->{
			try {
				client.exit();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		BorderPane border=new BorderPane();
		
		border.setTop(writersTitle);
		border.setCenter(writers);
		border.setLeft(null);
		//border.setRight(functions);
		border.setBottom(bottom);
		
		scene=new Scene(border,600,350);
		window.setScene(scene);
		window.show();
	}
	
	public void showInsertWindow() {
		Stage insertWindow=new Stage();
		VBox insert=new VBox();
		Label titleLabel=new Label("Title : ");
		titleLabel.setFont(new Font("Arial",15));
		TextField titleField=new TextField();
		Label authorLabel=new Label("Author : ");
		authorLabel.setFont(new Font("Arial",15));
		TextField authorField=new TextField();
		Label abstractLabel=new Label("Abstract : ");
		abstractLabel.setFont(new Font("Arial",15));
		TextField abstractField=new TextField();
		Label bodyLabel=new Label("Body : ");
		bodyLabel.setFont(new Font("Arial",15));
		TextArea bodyField=new TextArea();
		VBox relatedBox=new VBox();
		//relatedBox.setAlignment(Pos.CENTER);
		Label relatedLabel=new Label("Choose related articles : ");
		relatedLabel.setFont(new Font("Arial",15));
		ListView<String> related=new ListView<>();
		related.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		related.setItems(getWriters());
		relatedBox.getChildren().addAll(relatedLabel,related);
		relatedBox.setMaxHeight(150.0);
		HBox bottom=new HBox();
		bottom.setAlignment(Pos.CENTER);
		Button insertButton=new Button("Insert");
		bottom.getChildren().add(insertButton);
		insert.getChildren().addAll(titleLabel,titleField,authorLabel,authorField,abstractLabel,abstractField,bodyLabel,bodyField,relatedBox,bottom);
		Scene insertScene=new Scene(insert,400,400);
		insertWindow.setScene(insertScene);
		insertWindow.show();
		
		insertButton.setOnAction(e->{
			lock=true;
			try {
				ArrayList<ArticleObj> relatedArticles=new ArrayList<ArticleObj>();
				ObservableList<String> selected=related.getSelectionModel().getSelectedItems();
				//System.out.println(selected);
				if(!(selected.isEmpty())) {
					System.out.println(selected);
					for(String s:selected) {
						for(ArticleObj a:client.articleList) {
							if(s.contains(a.getTitle()) && s.contains(a.getAbstr())) {
								relatedArticles.add(a);
							}
						}
					}
				}
				client.createArticle(titleField.getText(), abstractField.getText(), authorField.getText(), bodyField.getText(),relatedArticles);
				/*if(clientList!=null) {
					for(Client c:clientList) {
						c.updateArticleList();
					}
				}*/
				//Client.class
				//articleList.setItems(getWriters());
				//articleList.refresh();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			lock=false;
		});
	}
	
	public void showUpdateWindow(ArticleObj article) {
		Stage insertWindow=new Stage();
		VBox insert=new VBox();
		Label titleLabel=new Label("Title : ");
		titleLabel.setFont(new Font("Arial",15));
		TextField titleField=new TextField();
		titleField.setText(article.getTitle());
		Label authorLabel=new Label("Author : ");
		authorLabel.setFont(new Font("Arial",15));
		TextField authorField=new TextField();
		authorField.setText(article.getAuthor());
		Label abstractLabel=new Label("Abstract : ");
		abstractLabel.setFont(new Font("Arial",15));
		TextField abstractField=new TextField();
		abstractField.setText(article.getAbstr());
		Label bodyLabel=new Label("Body : ");
		bodyLabel.setFont(new Font("Arial",15));
		TextArea bodyField=new TextArea();
		bodyField.setText(article.getBody());
		VBox relatedBox=new VBox();
		//relatedBox.setAlignment(Pos.CENTER);
		Label relatedLabel=new Label("Choose related articles : ");
		relatedLabel.setFont(new Font("Arial",15));
		ListView<String> related=new ListView<>();
		related.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		related.setItems(getWriters());
		relatedBox.getChildren().addAll(relatedLabel,related);
		relatedBox.setMaxHeight(150.0);
		HBox bottom=new HBox();
		bottom.setAlignment(Pos.CENTER);
		Button updateButton=new Button("Update");
		bottom.getChildren().add(updateButton);
		insert.getChildren().addAll(titleLabel,titleField,authorLabel,authorField,abstractLabel,abstractField,bodyLabel,bodyField,relatedBox,bottom);
		Scene insertScene=new Scene(insert,400,400);
		insertWindow.setScene(insertScene);
		insertWindow.show();
		
		updateButton.setOnAction(e->{
			lock=true;
			ArrayList<ArticleObj> relatedArticles=new ArrayList<ArticleObj>();
			ObservableList<String> selected=related.getSelectionModel().getSelectedItems();
			//System.out.println(selected);
			if(!(selected.isEmpty())) {
				//System.out.println(selected);
				for(String s:selected) {
					for(ArticleObj a:client.articleList) {
						if(s.contains(a.getTitle()) && s.contains(a.getAbstr())) {
							relatedArticles.add(a);
						}
					}
				}
			}
			else {
				relatedArticles=article.getRelated();
			}
			article.setTitle(titleField.getText());
			article.setAuthor(authorField.getText());
			article.setAbstr(abstractField.getText());
			article.setBody(bodyField.getText());
			article.setRelated(relatedArticles);
			try {
				client.updateArticle(article);
				/*for(Client c:clientList) {
					c.updateArticleList();
				}*/
				//articleList.setItems(getWriters());
				//articleList.refresh();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			lock=false;
		});
	
	}
	
	public void showArticle(ArticleObj article) {
		Stage articleWindow=new Stage();
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
		window.setScene(articleScene);
		back.setOnAction(e->{
			window.setScene(scene);
		});
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
		articleList.setItems(getWriters());
		articleList.refresh();
		//}
		//this.window.setScene(scene);
		//System.out.println("Updated");
		
	}
	
	private ObservableList<String> getWriters() {
		ObservableList<String> list=FXCollections.observableArrayList();
		for(ArticleObj p:client.articleList) {
			//System.out.println(p.getAvailability());
			//list.add(p);
			String s=p.getTitle()+"\n"+"Abstract : "+p.getAbstr();
			//Platform.runLater(()-> list.add(s));
			list.add(s);
		}
		return list;
	}

}
