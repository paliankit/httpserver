package com.httpserver.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.httpserver.HttpServer;

public class ServerListenerThread extends Thread {

	private static final Logger Logger = LoggerFactory.getLogger(ServerListenerThread.class);

	private int port;
	private String webroot;
	private ServerSocket serverSocket;

	public ServerListenerThread(int port, String webroot) throws IOException {
		this.port = port;
		this.webroot = webroot;
		this.serverSocket = new ServerSocket(this.port);
	}

	@Override
	public void run() {
		try {

			while (serverSocket.isBound() && !serverSocket.isClosed()) {

				Socket socket = serverSocket.accept();

				Logger.info("Connection accepted: " + socket.getInetAddress());

				HTTPConnectionWorkerThread workerThread = new HTTPConnectionWorkerThread(socket);
				workerThread.start();
			}

		} catch (IOException e) {
			Logger.error("Problem with setting socket",e);
			e.printStackTrace();
		} finally {
			if (serverSocket != null) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}
