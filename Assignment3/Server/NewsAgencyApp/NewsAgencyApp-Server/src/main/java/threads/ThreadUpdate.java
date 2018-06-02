package threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;

import server.Server;

public class ThreadUpdate extends Thread {
	
	public void run() {
		try {
			ServerSocket updateServerSocket=new ServerSocket(3333);
			Socket updateSocket=updateServerSocket.accept();
			//Socket newSocket=new Socket("127.0.0.1",2222);
			//ServerSocket updateServerSocket=new ServerSocket(PORTUPDATE);
			BufferedReader bufferReader=new BufferedReader(new InputStreamReader(updateSocket.getInputStream()));
			//System.out.println(bufferReader.readLine());
			String msg1=bufferReader.readLine();
			ObjectMapper mapper = new ObjectMapper();
			//System.out.println(msg1);
			if(msg1.equals("ARTICLESUPDATE")) {
				PrintWriter printWriter=new PrintWriter(updateSocket.getOutputStream(),true);
				String articles=mapper.writeValueAsString(Server.articleList);
				printWriter.println(articles);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
