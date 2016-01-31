package com.dmcliver.donateme.builders;

import java.io.IOException;
import java.util.List;

import org.primefaces.model.UploadedFile;

import com.dmcliver.donateme.domain.Image;
import com.dmcliver.donateme.domain.Product;

public interface ImageBuilder {

	List<Image> buildAll(Product product, List<UploadedFile> files) throws IOException;
}
