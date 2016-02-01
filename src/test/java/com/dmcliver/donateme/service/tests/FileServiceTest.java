package com.dmcliver.donateme.service.tests;

import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;

import com.dmcliver.donateme.FileOutputStreamProxy;
import com.dmcliver.donateme.LoggingFactory;
import com.dmcliver.donateme.services.FileServiceImpl;

public class FileServiceTest {

	@Test(expected = IOException.class)
	public void write_WithIOException_LogsError() throws IOException {
		
		String fileName = "C:\\Users\\Public\\FakeFile.txt";

		Logger logger = mock(Logger.class);
		LoggingFactory logFac = mock(LoggingFactory.class);
		when(logFac.create(any(Class.class))).thenReturn(logger);
		
		FileOutputStreamProxy fileStreamProxy = mock(FileOutputStreamProxy.class);
		doThrow(IOException.class).when(fileStreamProxy).write(eq(fileName), any(byte[].class));
		
		FileServiceImpl service = new FileServiceImpl(logFac, fileStreamProxy );
		service.write(fileName, new byte[] {0, 1, -2, 3});
		
		verify(logger).error(eq("Problem writing to file: " + fileName), any(IOException.class));
	}
}
