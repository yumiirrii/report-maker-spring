package com.example.reportmaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReportMakerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportMakerApplication.class, args);
//				.getBean(ReportMakerApplication.class).execute();
	}
//	@Autowired
//	ProjectService service;
//
//	private void execute() {
//		List<Project> project = service.selectAll();
//		System.out.println(project);
//	}

}
