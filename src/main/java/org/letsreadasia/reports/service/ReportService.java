package org.letsreadasia.reports.service;

import org.letsreadasia.reports.utils.CommandExecutor;
import org.letsreadasia.reports.utils.FileUploadManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 
 * @author anish
 *
 */
@Service
public class ReportService {
	
	@Autowired
	private CommandExecutor commandExecutor;
	
	@Autowired
	private FileUploadManager fileUploadManager;
	
	@Value("${latex.command}")
	private String latexCommand;
	
	@Async
	public boolean generate(){
		System.out.println(latexCommand);
		this.commandExecutor.executePdfCommand(latexCommand);
		try {
			this.fileUploadManager.upload();
		} catch (Exception e) {
			System.err.println("exception while uploading......");
			e.printStackTrace();
		}
		return true;
	}

}
