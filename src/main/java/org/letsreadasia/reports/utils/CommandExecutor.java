package org.letsreadasia.reports.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.stereotype.Component;

/**
 * 
 * @author anish
 *
 */
@Component
public class CommandExecutor {
	
	public void executePdfCommand(String command){
		System.out.println(command);
		System.out.println("COMMAND EXECUTION STARTED.........");
		String s = null;
		try {
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			// read the output from the command
			while ((s = stdInput.readLine()) != null) {
				System.out.println("OUTPUT : \n");
				System.out.println(s);
			}
			// read any errors from the attempted command
			while ((s = stdError.readLine()) != null) {
				System.out.println("ERROR : \n");
				System.out.println(s);
			}
			System.out.println("COMMAND EXECUTED..................");
			//System.exit(0);
		} catch (IOException e) {
			System.out.println("exception occured :  ");
			e.printStackTrace();
			System.exit(-1);
		}finally {
			//System.exit(0);
		}
	}

}
