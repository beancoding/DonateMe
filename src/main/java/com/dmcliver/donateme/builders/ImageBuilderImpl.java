package com.dmcliver.donateme.builders;

import static java.util.UUID.randomUUID;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dmcliver.donateme.LoggingFactory;
import com.dmcliver.donateme.domain.Image;
import com.dmcliver.donateme.domain.Product;
import com.dmcliver.donateme.services.FileService;

@Component
public class ImageBuilderImpl implements ImageBuilder {

	private Logger logger;
	private FileService fileService;
	
	@Autowired
	public ImageBuilderImpl(LoggingFactory logFac, FileService fileService) {
		
		this.fileService = fileService;
		logger = logFac.create(ImageBuilder.class);
	}
	
	@Override
	public List<Image> buildAll(Product product, List<UploadedFile> files) throws MalformedURLException, IOException {
		
		List<Image> images = new LinkedList<Image>();

		for (UploadedFile uploadedFile : files) {
		
			String fileName = uploadedFile.getFileName();

			try {
				images.add(new Image(randomUUID(), new URL(fileName), product));
			}
			catch (MalformedURLException ex) {
				
				logger.error("The filename: " + fileName + " is malformed and hence bad and invalid.", ex);
				throw ex;
			}
			fileService.write(fileName, uploadedFile.getContents());
		}
		
		return images;
	}
}
