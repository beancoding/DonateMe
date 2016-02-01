package com.dmcliver.donateme;

import java.io.IOException;

public interface FileOutputStreamProxy {

	void write(String fileName, byte[] content) throws IOException;

	void close() throws IOException;

}