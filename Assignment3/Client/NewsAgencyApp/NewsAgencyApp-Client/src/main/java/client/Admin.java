package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import entities.WriterObj;

public class Admin extends Observable {
	
	Socket socket;
	
	public ArrayList<WriterObj> writerList=new ArrayList<WriterObj>();
	
	public Admin() {
		//socket=new Socket("127.0.0.1",1111);
		//PrintWriter printWriter=new PrintWriter(socket.getOutputStream(),true);
		//printWriter.println("CONNECT");
		//this.addObserver();
	}
	
	public void createWriter(String name,Integer age,String userName,String password) throws UnknownHostException, IOException, InterruptedException {
		socket=new Socket("127.0.0.1",2222);
		ObjectMapper mapper = new ObjectMapper();
		PrintWriter printWriter=new PrintWriter(socket.getOutputStream(),true);
		WriterObj writer=new WriterObj(name,age,userName,password);
		String wr="ADMINCREATE"+mapper.writeValueAsString(writer);
		//System.out.println(wr);
		printWriter.println(wr);
		//System.out.println(art);
		
		//Thread.sleep(1000);
		BufferedReader bufferReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		//System.out.println(bufferReader.readLine());
		//System.out.println(bufferReader.readLine());
		writerList=mapper.readValue(bufferReader.readLine(), new TypeReference<ArrayList<WriterObj>>() {});
		//System.out.println(writerList.toString());
		
		setChanged();
		notifyObservers();
	}
	
	public void updateWriter(WriterObj writer) throws UnknownHostException, IOException, InterruptedException {
		socket=new Socket("127.0.0.1",2222);
		ObjectMapper mapper = new ObjectMapper();
		PrintWriter printWriter=new PrintWriter(socket.getOutputStream(),true);
		//WriterObj writer=new WriterObj(name,age,userName,password);
		String wr="ADMINUPDATE"+mapper.writeValueAsString(writer);
		printWriter.println(wr);
		//System.out.println(art);
		
		//Thread.sleep(1000);
		BufferedReader bufferReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		//System.out.println(bufferReader.readLine());
		writerList=mapper.readValue(bufferReader.readLine(), new TypeReference<ArrayList<WriterObj>>() {});
		
		setChanged();
		notifyObservers();
	}
	
	public void deleteWriter(WriterObj writer) throws UnknownHostException, IOException, InterruptedException {
		socket=new Socket("127.0.0.1",2222);
		ObjectMapper mapper = new ObjectMapper();
		PrintWriter printWriter=new PrintWriter(socket.getOutputStream(),true);
		//ArticleObj article=new ArticleObj(title,abstr,author,body);
		//WriterObj writer=new WriterObj(name,age,userName,password);
		String wr="ADMINDELETE"+mapper.writeValueAsString(writer);
		printWriter.println(wr);
		//System.out.println(art);
		//Thread.sleep(1000);
		BufferedReader bufferReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		//System.out.println(bufferReader.readLine());
		writerList=mapper.readValue(bufferReader.readLine(), new TypeReference<ArrayList<WriterObj>>() {});
		setChanged();
		notifyObservers();
	}
	
	public void exit() throws UnknownHostException, IOException {
		socket=new Socket("127.0.0.1",2222);
		PrintWriter printWriter=new PrintWriter(socket.getOutputStream(),true);
		printWriter.println("EXIT");
	}
	
	public ArrayList<WriterObj> updateWriterList() throws UnknownHostException, IOException, InterruptedException {
		ArrayList<WriterObj> writers;
		socket=new Socket("127.0.0.1",2222);
		PrintWriter printWriter=new PrintWriter(socket.getOutputStream(),true);
		printWriter.println("ADMIN");
		ObjectMapper mapper = new ObjectMapper();
		//Thread.sleep(1000);
		BufferedReader bufferReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		//System.out.println(bufferReader.readLine());
		writers=mapper.readValue(bufferReader.readLine(), new TypeReference<ArrayList<WriterObj>>() {});
		//System.out.println("here");
		//System.out.println(writerList.size());
		return writers;
	}


}
