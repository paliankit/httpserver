package com.httpserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.httpserver.config.Configuration;
import com.httpserver.config.ConfigurationManager;
import com.httpserver.core.ServerListenerThread;

public class HttpServer {
	
	private static final Logger Logger=LoggerFactory.getLogger(HttpServer.class);	
	
	public static void main(String[] args) {
			
		 Logger.info("Server running...");
		
		try {
			ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
			Configuration conf=ConfigurationManager.getInstance().getCurrentConfiguration();
			
			Logger.info("Port Used: " +conf.getPort());
			Logger.info("Webroot: "+ conf.getWebroot());
			
			ServerListenerThread serverListenerThread=new ServerListenerThread(conf.getPort(),conf.getWebroot());
			serverListenerThread.start();
			
				
		} catch (IOException e) { 
			e.printStackTrace();
		}
		
	}
	
	

}
