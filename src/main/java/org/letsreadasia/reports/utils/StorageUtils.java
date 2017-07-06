package org.letsreadasia.reports.utils;

import java.io.FileInputStream;
import java.io.InputStream;

import com.google.gcloud.AuthCredentials;
import com.google.gcloud.RetryParams;
import com.google.gcloud.storage.StorageOptions;

/**
 * 
 * @author anish
 *
 */
public class StorageUtils {

	public static StorageOptions create(String projectId, InputStream keyStream) throws Exception {
		StorageOptions storageOptions = StorageOptions.builder()
				.authCredentials(AuthCredentials.createForJson(keyStream)).projectId(projectId)
				.retryParams(RetryParams.builder().retryMaxAttempts(10).retryMinAttempts(6).maxRetryDelayMillis(30000)
						.totalRetryPeriodMillis(120000).initialRetryDelayMillis(250).build())
				.connectTimeout(60000).readTimeout(60000).build();
		return storageOptions;

	}

	public static StorageOptions create(String projectId, String keyPath) throws Exception {
		InputStream keyFileStream = new FileInputStream(keyPath);
		return create(projectId, keyFileStream);
	}

	public static String getPublicURL(String bucket, String storagePath) {
		StringBuilder url = new StringBuilder("https://storage.googleapis.com/");
		url.append(bucket);
		url.append("/");
		url.append(storagePath);
		return url.toString();
	}
}