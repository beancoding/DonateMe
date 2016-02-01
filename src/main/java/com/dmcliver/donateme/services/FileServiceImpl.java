package com.dmcliver.donateme.services;

import java.io.IOException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dmcliver.donateme.FileOutputStreamProxy;
import com.dmcliver.donateme.LoggingFactory;

@Service
public class FileServiceImpl implements FileService {

	private Logger logger;
	private FileOutputStreamProxy fileStream;
	
	@Autowired
	public FileServiceImpl(LoggingFactory logFac, FileOutputStreamProxy fileStream){
		
		this.fileStream = fileStream;
		this.logger = logFac.create(FileServiceImpl.class);
	}
	
	@Override
	public  void write(String fileName, byte[] content) throws IOException {
		
		try {
			fileStream.write(fileName, content);
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
