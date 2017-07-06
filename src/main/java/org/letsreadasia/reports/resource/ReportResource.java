package org.letsreadasia.reports.resource;

import org.letsreadasia.reports.AppConfig;
import org.letsreadasia.reports.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author anish
 *
 */
@RestController
@Import(AppConfig.class)
public class ReportResource {
	
	@Autowired
	private ReportService reportService;
	
	@ResponseBody
	@RequestMapping("/reports/generate")
	public ResponseEntity<String> generate(){
		this.reportService.generate();
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}

}
