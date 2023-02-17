package com.httpserver.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.slf4j.LoggerFactory;

public class HttpParser {
	
	private final static org.slf4j.Logger Logger=LoggerFactory.getLogger(HttpParser.class);
	
	private static final int SP= 0x20; //32
	private static final int CR= 0x0D; //13
	private static final int LF= 0x0A; // 10
	
	public HttpRequest parseHttpRequest(InputStream input) throws IOException, HttpParsingException {
		InputStreamReader reader=new InputStreamReader(input,StandardCharsets.US_ASCII);
		HttpRequest request=new HttpRequest();
		parseRequestLine(reader,request);
		parseHeaders(reader,request);
		parseBody(reader,request);
		return request;
	}
	

	public void parseRequestLine(InputStreamReader reader,HttpRequest request) throws IOException, HttpParsingException {
		
		StringBuilder processingDataBuffer =new StringBuilder();
		boolean methodParsed=false;
		boolean requestTargetParsed=false;
		
		int _byte;
		while((_byte=reader.read())>=0) {
			if(_byte==CR) {
				_byte=reader.read();
				if(_byte==LF) {
					Logger.debug("Request Line VERSION to Process: {}", processingDataBuffer.toString());
					if(!methodParsed || !requestTargetParsed) {
						throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
					}
					try {
						request.setHttpVersion(processingDataBuffer.toString());
					}
					catch(BadHttpVersionException e) {
						throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
					}
					return;
				}
				else {
					throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
				}
			}
			if(_byte==SP) {
				if(!methodParsed) {
					Logger.debug("Request Line METHOD to Process: {}", processingDataBuffer.toString());
					request.setMethod(processingDataBuffer.toString());
					methodParsed=true;
				}
				else if(!requestTargetParsed) {
					Logger.debug("Request Line REQ TARGET to Process: {}",processingDataBuffer.toString());
					request.setRequestTarget(processingDataBuffer.toString());
					requestTargetParsed=true;
				}
				else {
					throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
				}
				processingDataBuffer.delete(0,processingDataBuffer.length());
			}
			else {
				processingDataBuffer.append((char)_byte);
				if(!methodParsed){
					if(processingDataBuffer.length()>HttpMethod.MAX_LENGTH) {
						throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
					}
				}
			}
			
		}
	}
	
	private void parseBody(InputStreamReader reader, HttpRequest request) {
		// TODO Auto-generated method stub
		
	}

	private void parseHeaders(InputStreamReader reader, HttpRequest request) {
		// TODO Auto-generated method stub
		
	}

}
