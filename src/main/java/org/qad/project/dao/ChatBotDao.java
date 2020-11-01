package org.qad.project.dao;

import org.qad.project.models.ChatBot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatBotDao extends JpaRepository<ChatBot, String> {

}
