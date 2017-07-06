package org.letsreadasia.reports.utils;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gcloud.spi.StorageRpc.Tuple;
import com.google.gcloud.storage.BlobInfo;

@Component
public class FileUploadManager {
	
	@Value("${file.path}")
	public String filePath;
	
	@Value("${project.name}")
	public String projectName;
	
	@Value("${upload.location}")
	public String uploadLocation;
	
	@Autowired
	private StorageFactory storageFactory;
	
	
	public boolean upload() throws Exception{
		StorageAction<Tuple<Path, BlobInfo>> storageAction = new UploadAction();
		String[] args = new String[]{"main.pdf", "", uploadLocation, projectName};
        Tuple<Path, BlobInfo> request = storageAction.parse(args);
        storageAction.run(storageFactory.get(), request);	
		return true;
	}
	
	

}
