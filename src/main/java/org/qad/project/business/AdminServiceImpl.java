package org.qad.project.business;

import javax.transaction.Transactional;

import org.qad.project.dao.CollaborateurDao;
import org.qad.project.models.Collaborateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
	

	@Autowired
	private CollaborateurDao collabDao;

	@Override
	public Collaborateur addCollaborateur(Collaborateur collaborateur) {
		return collabDao.save(collaborateur);
	}
	
	
}
