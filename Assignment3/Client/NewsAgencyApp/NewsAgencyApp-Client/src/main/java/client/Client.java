package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.locks.ReentrantLock;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import entities.ArticleObj;
import entities.WriterObj;

public class Client extends Observable {
	
	Socket socket;
	Socket updateSocket;
	//public static List instanceList=new ArrayList();
	//ReentrantLock lock = new ReentrantLock();
	public static boolean lockflag = false;
	
	//public static boolean uiflag;
	
	public ArrayList<ArticleObj> articleList=new ArrayList<ArticleObj>();
	
	public Client() {
		//uiflag=false;
		//socket=new Socket("127.0.0.1",2222);
		//instanceList.add(new java.lang.ref.WeakReference<Client>(this))
	}
	
	public synchronized void createArticle(String title,String abstr,String author,String body,ArrayList<ArticleObj> relatedArticles) throws UnknownHostException, IOException {
		//lock.lock();
		//lockflag=true;
		socket=new Socket("127.0.0.1",2222);
		ObjectMapper mapper = new ObjectMapper();
		PrintWriter printWriter=new PrintWriter(socket.getOutputStream(),true);
		ArticleObj article=new ArticleObj(title,abstr,author,body,relatedArticles);
		String art="CLIENTCREATE"+mapper.writeValueAsString(article);
		printWriter.println(art);
		//System.out.println(art);
		//BufferedReader bufferReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		//this.articleList=mapper.readValue(bufferReader.readLine(), new TypeReference<ArrayList<ArticleObj>>() {});
		
		//setChanged();
		//notifyObservers();
		//lock.unlock();
		//socket.close();
		//lockflag=false;
	}
	
	public synchronized void updateArticle(ArticleObj article) throws UnknownHostException, IOException {
		//lock.lock();
		//lockflag=true;
		socket=new Socket("127.0.0.1",2222);
		ObjectMapper mapper = new ObjectMapper();
		PrintWriter printWriter=new PrintWriter(socket.getOutputStream(),true);
		//ArticleObj article=new ArticleObj(title,abstr,author,body);
		String art="CLIENTUPDATE"+mapper.writeValueAsString(article);
		printWriter.println(art);
		//System.out.println(art);
		//BufferedReader bufferReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		//System.out.println(bufferReader.readLine()+"--------------");
		//articleList=mapper.readValue(bufferReader.readLine(), new TypeReference<ArrayList<ArticleObj>>() {});
		
		//setChanged();
		//notifyObservers();
		//lock.unlock();
		//socket.close();
		//lockflag=false;
	}
	
	public synchronized void deleteArticle(ArticleObj article) throws UnknownHostException, IOException {
		//lock.lock();
		//lockflag=true;
		socket=new Socket("127.0.0.1",2222);
		ObjectMapper mapper = new ObjectMapper();
		PrintWriter printWriter=new PrintWriter(socket.getOutputStream(),true);
		//ArticleObj article=new ArticleObj(title,abstr,author,body);
		String art="CLIENTDELETE"+mapper.writeValueAsString(article);
		printWriter.println(art);
		//System.out.println(art);
		//BufferedReader bufferReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		//articleList=mapper.readValue(bufferReader.readLine(), new TypeReference<ArrayList<ArticleObj>>() {});
		
		//setChanged();
		//notifyObservers();
		//lock.unlock();
		//socket.close();
		//lockflag=false;
	}
	
	public void exit() throws UnknownHostException, IOException {
		//lockflag=true;
		socket=new Socket("127.0.0.1",2222);
		PrintWriter printWriter=new PrintWriter(socket.getOutputStream(),true);
		printWriter.println("EXIT");
		//lockflag=false;
	}
	
	public synchronized void updateArticleList() throws UnknownHostException, IOException, InterruptedException {
		//ArrayList<ArticleObj> articles=new ArrayList<ArticleObj>();
		//updateSocket=new Socket("127.0.0.1",2222);
		//uiflag=true;
		socket=new Socket("127.0.0.1",2222);
		PrintWriter printWriter=new PrintWriter(socket.getOutputStream(),true);
		printWriter.println("CLIENT");
		ObjectMapper mapper = new ObjectMapper();
		Thread.sleep(300);
		BufferedReader bufferReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		//System.out.println(bufferReader.readLine()+"---------");
		//if(bufferReader.readLine()!=null) {
			//this.articleList=new ArrayList<ArticleObj>();
		try {
			this.articleList=mapper.readValue(bufferReader.readLine(), new TypeReference<ArrayList<ArticleObj>>() {});
		} catch(NullPointerException e) {
			
		}
		//this.articleList=mapper.readValue(bufferReader.readLine(), new TypeReference<ArrayList<ArticleObj>>() {});
		//socket.close();
		setChanged();
		notifyObservers();
		//uiflag=false;
		//return articles;
	}

}
