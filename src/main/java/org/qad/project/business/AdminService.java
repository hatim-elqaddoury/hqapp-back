package org.qad.project.business;

import org.qad.project.models.Collaborateur;
import org.springframework.data.domain.Page;

@SuppressWarnings("unused")
public interface AdminService {

	public Collaborateur addCollaborateur(Collaborateur collaborateur);
	
	
}
