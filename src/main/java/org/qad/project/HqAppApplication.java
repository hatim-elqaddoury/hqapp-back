package org.qad.project;

import org.qad.project.business.AdminService;
import org.qad.project.dao.CollaborateurDao;
import org.qad.project.models.Collaborateur;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class HqAppApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(HqAppApplication.class, args);
		
		
		ctx.getBean(CollaborateurDao.class).save(new Collaborateur("helqaddoury", "Hatim", "El-Qaddoury"));
		ctx.getBean(AdminService.class).addCollaborateur(new Collaborateur("mtoader", "madalina", "toader"));
		
		
		
	}

}
