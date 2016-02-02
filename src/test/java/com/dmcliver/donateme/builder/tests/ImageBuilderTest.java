package com.dmcliver.donateme.builder.tests;

import static com.dmcliver.donateme.WebConstants.Messages.MalformedURLError;
import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;

import com.dmcliver.donateme.LoggingFactory;
import com.dmcliver.donateme.builders.ImageBuilderImpl;
import com.dmcliver.donateme.domain.Product;
import com.dmcliver.donateme.services.FileService;

@RunWith(MockitoJUnitRunner.class)
public class ImageBuilderTest {

	@Mock private LoggingFactory logFac;
	@Mock private FileService fileService;
	@Mock private Logger logger;

	@Before
	public void init() {
		when(logFac.create(any(Class.class))).thenReturn(logger);
	}
	
	@Test(expected = MalformedURLException.class)
	public void buildAll_WithMalformedURLException_LogsErrorAndRethrows() throws MalformedURLException, IOException {
		
		final String badFileName = "/\\:@";

		UploadedFile uploadedFile = mock(UploadedFile.class);
		when(uploadedFile.getFileName()).thenReturn(badFileName);
		
		ImageBuilderImpl imageBuilder = new ImageBuilderImpl(logFac, fileService);
		imageBuilder.buildAll(new Product(), asList(uploadedFile));

		verify(logger).error(String.format(MalformedURLError, badFileName), any(MalformedURLException.class));
	}
	
	@Test(expected = IOException.class)
	public void buildAll_WithIOException_LogsErrorAndRethrows() throws MalformedURLException, IOException {
		
		final String goodFileName = "C:\\Users\\Public\\Images\\image1.txt";

		UploadedFile uploadedFile = mock(UploadedFile.class);
		when(uploadedFile.getFileName()).thenReturn(goodFileName);
		
		doThrow(IOException.class).when(fileService).write(eq(goodFileName), any(byte[].class));
		
		ImageBuilderImpl imageBuilder = new ImageBuilderImpl(logFac, fileService);
		imageBuilder.buildAll(new Product(), asList(uploadedFile));
	}
}
