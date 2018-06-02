package threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;

import entities.WriterObj;
import server.Server;

public class ThreadAdmin extends Thread {
	
	Socket socket;
	
	public ThreadAdmin(Socket socket) {
		this.socket=socket;
	}
	
	public void run() {
		try {
			String msg=null;
			PrintWriter printWriter=new PrintWriter(socket.getOutputStream(),true);
			BufferedReader bufferReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			ObjectMapper mapper = new ObjectMapper();
			//System.out.println("Here");
			msg=Server.msg;
			//System.out.println(msg);
				if (msg.contains("CREATE")) {
					System.out.println(msg);
					msg=msg.substring(11);
					//System.out.println(msg);
					WriterObj writer=mapper.readValue(msg, WriterObj.class);
					//System.out.println(writer.toString());
					Server.writerList.add(writer);
				}
				if(msg.contains("UPDATE")) {
					msg=msg.substring(11);
					WriterObj writer=mapper.readValue(msg, WriterObj.class);
					//System.out.println("------"+writer.toString());
					for(WriterObj w:Server.writerList) {
						if(w.getID().equals(writer.getID())) {
							w.setName(writer.getName());
							w.setAge(writer.getAge());
							w.setUserName(writer.getUserName());
							w.setPassword(w.getPassword());
							//System.out.println("UPDATED "+w.toString());
						}
					}
				}
				if(msg.contains("DELETE")) {
					msg=msg.substring(11);
					WriterObj writer=mapper.readValue(msg, WriterObj.class);
					for(WriterObj w:Server.writerList) {
						if(w.getID().equals(writer.getID())) {
							//Server.writerList.remove(writer);
							writer=w;
						}
					}
					Server.writerList.remove(writer);
				}
				String writers=mapper.writeValueAsString(Server.writerList);
				System.out.println(writers);
				//System.out.println(writers);
				printWriter.println(writers);
			//socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
