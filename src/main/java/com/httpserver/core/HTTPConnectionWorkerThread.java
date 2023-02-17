package com.httpserver.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTTPConnectionWorkerThread extends Thread {

	private final static Logger Logger = LoggerFactory.getLogger(HTTPConnectionWorkerThread.class);
	private Socket socket;

	public HTTPConnectionWorkerThread(Socket socket) {
		super();
		this.socket = socket;
	}

	@Override
	public void run() {
		InputStream input = null;
		OutputStream output = null;
		try {
			input = socket.getInputStream();
			output = socket.getOutputStream();

			String html = "<html><head><title>Simple Java HTTP Server</title></head><body><h1>This is a HTTP Server using java</h1></body></html>";

			final String CRLF = "\n\r";
			String response = "HTTP/1.1 200 OK" + CRLF + "Content-Length: " + html.getBytes().length + CRLF + CRLF
					+ html + CRLF + CRLF;

			output.write(response.getBytes());

			Logger.info("Connection Processing finished...");
		} catch (IOException e) {
			Logger.error("Problem with communication", e);
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}
