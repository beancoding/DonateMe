package com.dmcliver.donateme.services;

import java.io.IOException;

public interface FileService {

	void write(String fileName, byte[] content) throws IOException;

}