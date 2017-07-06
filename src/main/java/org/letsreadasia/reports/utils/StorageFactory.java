package org.letsreadasia.reports.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gcloud.storage.Storage;

/**
 * 
 * @author anish
 *
 */
@Component
public class StorageFactory {

	@Value("${file.path}")
	public String filePath;
	
	public Storage get() throws Exception{
		return StorageUtils.create("letsreadasia", filePath).service();
	}
}
