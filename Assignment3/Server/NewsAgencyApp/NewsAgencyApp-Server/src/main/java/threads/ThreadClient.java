package threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;

import entities.ArticleObj;
import entities.WriterObj;
import server.Server;

public class ThreadClient extends Thread {
	
	Socket socket;
	Socket socketUpdate;
	
	//static boolean changed=true;
	
	public ThreadClient(Socket socket) {
		this.socket=socket;
		//this.socketUpdate=socketUpdate;
	}
	
	public void run() {
		try {
			//changed=false;
			String msg=null;
			//System.out.println("Here");
			//System.out.println(bufferReader.readLine());
			ObjectMapper mapper = new ObjectMapper();
			msg=Server.msg;
				if (msg.contains("CREATE")) {
					msg=msg.substring(12);
					System.out.println(msg+"-----------------");
					//BufferedReader bufferReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
					ArticleObj article=mapper.readValue(msg, ArticleObj.class);
					Server.articleList.add(article);
					//System.out.println(article.toString());
					//System.out.println("Here");
					/*for(ArticleObj a:Server.articleList) {
						System.out.println(a.toString());
					}*/
					//changed=true;
				}
				if(msg.contains("UPDATE")) {
					msg=msg.substring(12);
					ArticleObj article=mapper.readValue(msg, ArticleObj.class);
					//BufferedReader bufferReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
					for(ArticleObj a:Server.articleList) {
						if(a.getID().equals(article.getID())) {
							a.setTitle(article.getTitle());
							a.setAuthor(article.getAuthor());
							a.setAbstr(article.getAbstr());
							a.setBody(a.getBody());
							a.setRelated(article.getRelated());
						}
					}
					//changed=true;
				}
				if(msg.contains("DELETE")) {
					msg=msg.substring(12);
					ArticleObj article=mapper.readValue(msg, ArticleObj.class);
					//Server.articleList.remove(article);
					for(ArticleObj a:Server.articleList) {
						if(a.getID().equals(article.getID())) {
							article=a;
						}
					}
					Server.articleList.remove(article);
					
					//changed=true;
				}
				if(msg.equals("CLIENT")) {
					//System.out.println(msg);
					PrintWriter printWriter=new PrintWriter(socket.getOutputStream(),true);
					String articles=mapper.writeValueAsString(Server.articleList);
					//System.out.println(articles+"------------------------");
					printWriter.println(articles);
				}
				/*if(changed==true) {
					PrintWriter printWriter=new PrintWriter(socket.getOutputStream(),true);
					String articles=mapper.writeValueAsString(Server.articleList);
					//System.out.println(articles+"------------------------");
					printWriter.println(articles);
					Thread.sleep(1000);
					changed=false;
				}*/
				
			//socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
