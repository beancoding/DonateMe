package com.dmcliver.donateme.services;

import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dmcliver.donateme.LoggingFactory;

@Service
public class FileServiceImpl implements FileService {

	private Logger logger;
	
	@Autowired
	public FileServiceImpl(LoggingFactory logFac){
		this.logger = logFac.create(FileServiceImpl.class);
	}
	
	@Override
	public  void write(String fileName, byte[] content) throws IOException {
		
		FileOutputStream fileStream = new FileOutputStream(fileName, false);
		try {
			fileStream.write(content);
		}
		catch(IOException ex) {
			
			logger.error("Problem writing to file: " + fileName, ex);
			throw ex;
		}
		finally {
			fileStream.close();
		}
	}
}
