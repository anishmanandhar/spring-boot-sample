package org.letsreadasia.reports.service;

import org.letsreadasia.reports.utils.CommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${latex.command}")
	private String latexCommand;
	
	public boolean generate(){
		System.out.println(latexCommand);
		this.commandExecutor.executePdfCommand(latexCommand);
		return true;
	}

}
