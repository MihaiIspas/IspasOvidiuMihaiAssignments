package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import entities.ArticleObj;
import javafx.application.Platform;

@SuppressWarnings("unused")
public class ThreadUpdate extends Thread {
	
	public Client client;
	
	public ThreadUpdate(Client client) {
		this.client=client;
	}
	
	public void run() {
		while (true) {
		}
	}
}


