package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;

import threads.ThreadAdmin;
import threads.ThreadClient;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import entities.ArticleObj;
import entities.WriterObj;


public class Server {
	
	public static final int PORTUPDATE=3333;
	public static final int PORTCLIENT=2222;
	
	public static ArrayList<ArticleObj> articleList=new ArrayList<ArticleObj>();
	public static ArrayList<WriterObj> writerList=new ArrayList<WriterObj>();
	
	public static String msg;
	
	/*public static void main(String[] args) throws IOException {
		new Server().runServer();
	}*/
	
	public void runServerClient() throws IOException, InterruptedException {
		ServerSocket serverSocketClient=new ServerSocket(PORTCLIENT);
		//ServerSocket updateServerSocket=new ServerSocket(PORTUPDATE);
		//System.out.println(PORTCLIENT);
		boolean flag=true;
		while(flag==true) {
			Socket socket=serverSocketClient.accept();
			//System.out.println("Here");
			BufferedReader bufferReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//System.out.println(bufferReader.readLine());
			msg=bufferReader.readLine();
			//System.out.println(msg);
			//System.out.println(msg+"-----------");
			if(msg.contains("ADMIN")) {
				ThreadAdmin thread=new ThreadAdmin(socket);
				thread.start();
				//System.out.println("Here");
			}
			if(msg.contains("CLIENT")) {
				//System.out.println("Here");
				ThreadClient thread=new ThreadClient(socket);
				thread.start();
			}
			if(msg.equals("EXIT")) {
				Serialize();
			}
			/*for(ArticleObj a:Server.articleList) {
				System.out.println(a.toString());
			}*/
			//System.out.println("Here");
			
			//thread.join();
		}
	}
	
	public void runUpdate() throws IOException {
		ServerSocket updateServerSocket=new ServerSocket(PORTUPDATE);
		while(true) {
			//Socket newSocket=new Socket("127.0.0.1",2222);
			//ServerSocket updateServerSocket=new ServerSocket(PORTUPDATE);
			Socket updateSocket=new Socket("127.0.0.1",3333);
			BufferedReader bufferReader=new BufferedReader(new InputStreamReader(updateSocket.getInputStream()));
			//System.out.println(bufferReader.readLine());
			String msg1=bufferReader.readLine();
			ObjectMapper mapper = new ObjectMapper();
			if(msg1.equals("ARTICLESUPDATE")) {
				PrintWriter printWriter=new PrintWriter(updateSocket.getOutputStream(),true);
				String articles=mapper.writeValueAsString(articleList);
				printWriter.println(articles);
			}
		}
	}
	
	/*public void runServerAdmin() throws IOException {
		//ServerSocket serverSocketAdmin=new ServerSocket(PORTADMIN);
		boolean flag=true;
		while(flag==true) {
			//System.out.println("Here");
			//System.out.println("Here");
			Socket adminSocket=serverSocketAdmin.accept();
			
		}
	}*/
	
	public void Serialize() throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(new File("C:\\Users\\Hp\\eclipse-workspace\\NewsAgencyApp\\Articles.json"), articleList);
		mapper.writeValue(new File("C:\\Users\\Hp\\eclipse-workspace\\NewsAgencyApp\\Writers.json"), writerList);
	}
	
	public void Deserialize() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		articleList=new ArrayList<ArticleObj>();
		writerList=new ArrayList<WriterObj>();
		articleList=mapper.readValue(new File("C:\\Users\\Hp\\eclipse-workspace\\NewsAgencyApp\\Articles.json"), new TypeReference<ArrayList<ArticleObj>>() {});
		writerList=mapper.readValue(new File("C:\\Users\\Hp\\eclipse-workspace\\NewsAgencyApp\\Writers.json"), new TypeReference<ArrayList<WriterObj>>() {});
	}

}
