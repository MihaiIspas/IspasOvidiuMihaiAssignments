package server;

import java.io.IOException;

import threads.ThreadUpdate;

public class TestMain {

	public static void main(String[] args) throws IOException, InterruptedException {
		Server s=new Server();
		s.Deserialize();
		//new ThreadUpdate().start();
		s.runServerClient();
		//s.runServerAdmin();
	}

}