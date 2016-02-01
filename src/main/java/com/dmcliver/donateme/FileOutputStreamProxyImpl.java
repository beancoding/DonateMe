package com.dmcliver.donateme;

import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class FileOutputStreamProxyImpl implements FileOutputStreamProxy {

	private FileOutputStream stream;
	
	@Override
	public void write(String fileName, byte[] content) throws IOException {
		
		stream = new FileOutputStream(fileName, false);;
		stream.write(content);
	}
	
	@Override
	public void close() throws IOException {
		stream.close();
	}
}
